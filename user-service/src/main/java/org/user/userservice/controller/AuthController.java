package org.user.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.user.userservice.model.LoginUserRequest;
import org.user.userservice.model.TokenResponse;
import org.user.userservice.model.WebResponse;
import org.user.userservice.service.AuthService;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(path = "/api/user/login",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TokenResponse> login(@RequestBody LoginUserRequest loginUserRequest) {
        TokenResponse tokenResponse = authService.login(loginUserRequest);
        return WebResponse.<TokenResponse>builder().data(tokenResponse).message("Login Berhasil").build();
    }
}
