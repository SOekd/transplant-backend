package com.transplantados.authentication;

import com.transplantados.authentication.dto.LoginRequest;
import com.transplantados.authentication.dto.LoginResponse;
import com.transplantados.authentication.dto.RefreshTokenRequest;
import com.transplantados.shared.Routes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(Routes.Authentication.BASE_ROUTE)
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(Routes.Authentication.LOGIN)
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping(Routes.Authentication.REFRESH)
    public ResponseEntity<LoginResponse> refresh(@RequestBody @Validated RefreshTokenRequest request) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }

}
