package com.transplantados.authentication;

import com.transplantados.authentication.dto.LoginRequest;
import com.transplantados.authentication.dto.LoginResponse;
import com.transplantados.shared.Routes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest loginRequest, HttpServletResponse response) {
        return ResponseEntity.ok(authenticationService.login(loginRequest, response));
    }

    @PostMapping(Routes.Authentication.REFRESH)
    public void refresh(@RequestBody HttpServletRequest request, HttpServletResponse response) {
        authenticationService.refreshToken(request, response);
    }

    @PostMapping(Routes.Authentication.LOGOUT)
    public void logout(HttpServletResponse response) {
        authenticationService.logout(response);
    }

}
