package com.tcc.psiuser.controller;

import com.tcc.psiuser.email.token.ConfirmationTokenService;
import com.tcc.psiuser.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class ConfirmationController {

    private ConfirmationTokenService confirmationTokenService;

    @GetMapping(path = "confirmar")
    public String confirm(@RequestParam("token") String token) {
        return confirmationTokenService.confirmToken(token);
    }
}
