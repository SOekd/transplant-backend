package com.transplantados.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.transplantados.authentication.dto.LoginRequest;
import com.transplantados.authentication.dto.LoginResponse;
import com.transplantados.authentication.exception.AuthenticationException;
import com.transplantados.patient.Patient;
import com.transplantados.patient.PatientRepository;
import com.transplantados.professional.Professional;
import com.transplantados.professional.ProfessionalRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    public static final String ACCESS_TOKEN_COOKIE = "access_token";

    private static final String REFRESH_TOKEN_COOKIE = "refresh_token";

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

    public LoginResponse login(@NotNull @Validated LoginRequest request, HttpServletResponse response) {
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

        Cookie accessTokenCookie = new Cookie(ACCESS_TOKEN_COOKIE, generateAccessToken(user));
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(3600);

        Cookie refreshTokenCookie = new Cookie(REFRESH_TOKEN_COOKIE, generateRefreshToken(user));
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(604800);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return new LoginResponse(true);
    }

    public void refreshToken(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        try {
            Cookie[] cookies = request.getCookies();
            String refreshToken = null;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (REFRESH_TOKEN_COOKIE.equals(cookie.getName())) {
                        refreshToken = cookie.getValue();
                        break;
                    }
                }
            }

            if (refreshToken == null) {
                throw new AuthenticationException();
            }

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

            Cookie accessTokenCookie = new Cookie(ACCESS_TOKEN_COOKIE, generateAccessToken(user));
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setSecure(true);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(3600);

            Cookie refreshTokenCookie = new Cookie(REFRESH_TOKEN_COOKIE, generateRefreshToken(user));
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(604800);

            response.addCookie(refreshTokenCookie);
            response.addCookie(accessTokenCookie);
        } catch (JWTVerificationException exception) {
            throw new AuthenticationException();
        }
    }

    public void logout(HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie(ACCESS_TOKEN_COOKIE, null);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0);

        Cookie refreshTokenCookie = new Cookie(REFRESH_TOKEN_COOKIE, null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);

        response.addCookie(refreshTokenCookie);
        response.addCookie(accessTokenCookie);
    }

    private String generateAccessToken(@NotNull UserDetails user) {
        Algorithm algorithm = Algorithm.HMAC384(jwtSecretKey);

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getUsername())
                .withClaim("patient", user instanceof Patient)
                .withExpiresAt(Instant.now().plusSeconds(3600))
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
