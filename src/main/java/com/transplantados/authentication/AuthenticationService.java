package com.transplantados.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.transplantados.authentication.dto.LoginRequest;
import com.transplantados.authentication.dto.LoginResponse;
import com.transplantados.authentication.dto.RefreshTokenRequest;
import com.transplantados.authentication.exception.AuthenticationException;
import com.transplantados.patient.Patient;
import com.transplantados.patient.PatientRepository;
import com.transplantados.professional.Professional;
import com.transplantados.professional.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${application.jwt-secret}")
    private String jwtSecretKey;

    @Value("${application.issuer}")
    private String issuer;

    private final ProfessionalRepository professionalRepository;

    private final PatientRepository patientRepository;

    public @Nullable UserDetails getAuthenticatedUser(@NotNull String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC384(jwtSecretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();

            val verifiedToken = verifier.verify(token);

            val id = UUID.fromString(verifiedToken.getSubject());

            if (verifiedToken.getClaim("patient").asBoolean()) {
                return patientRepository.findById(id).orElse(null);
            }

            return professionalRepository.findById(id).orElse(null);
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public LoginResponse login(@NotNull @Validated LoginRequest request) {
        Patient patient = patientRepository.findByEmail(request.email()).orElse(null);
        Professional professional = professionalRepository.findByEmail(request.email()).orElse(null);

        if (patient == null && professional == null) {
            throw new AuthenticationException();
        }

        boolean authenticated = false;
        if (patient != null && getPasswordEncoder().matches(request.password(), patient.getPassword())) {
            authenticated = true;
        } else if (professional != null && getPasswordEncoder().matches(request.password(), professional.getPassword())) {
            authenticated = true;
        }

        if (!authenticated) {
            throw new AuthenticationException();
        }

        UserDetails user = patient != null ? patient : professional;

        return new LoginResponse(
                generateAccessToken(user),
                generateRefreshToken(user)
        );
    }

    public LoginResponse refreshToken(@NotNull @Validated RefreshTokenRequest request) {
        try {
            String refreshToken = request.refreshToken();

            Algorithm algorithm = Algorithm.HMAC384(jwtSecretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();

            val verifiedToken = verifier.verify(refreshToken);

            val id = UUID.fromString(verifiedToken.getSubject());

            UserDetails user = patientRepository.findById(id).orElse(null);
            if (user == null) {
                user = professionalRepository.findById(id).orElse(null);
            }

            if (user == null) {
                throw new AuthenticationException();
            }

            String accessToken = generateAccessToken(user);
            String newRefreshToken = generateRefreshToken(user);

            return new LoginResponse(
                    accessToken,
                    newRefreshToken
            );
        } catch (JWTVerificationException exception) {
            throw new AuthenticationException();
        }
    }

    private String generateAccessToken(@NotNull UserDetails user) {
        Algorithm algorithm = Algorithm.HMAC384(jwtSecretKey);

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getUsername())
                .withClaim("patient", user instanceof Patient)
                .withExpiresAt(Instant.now().plusSeconds(604800))
                .sign(algorithm);
    }

    private String generateRefreshToken(@NotNull UserDetails user) {
        Algorithm algorithm = Algorithm.HMAC384(jwtSecretKey);

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getUsername())
                .withClaim("patient", user instanceof Patient)
                .withExpiresAt(Instant.now().plusSeconds(604800))
                .sign(algorithm);
    }

    private PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
