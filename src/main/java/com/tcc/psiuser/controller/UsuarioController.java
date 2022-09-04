package com.tcc.psiuser.controller;

import com.tcc.psiuser.service.UsuarioService;
import com.tcc.psiuser.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UsuarioController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registrarUsuario(@RequestBody Usuario usuario){
        usuarioService.save(usuario);
    }

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Usuario> findByEmail(@RequestParam String email){
        Usuario usuario = usuarioService.findByEmail(email);
        return ResponseEntity.ok(usuario);
    }
}
