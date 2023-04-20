package com.tcc.psiuser.controller;

import com.tcc.psiuser.dto.PasswordDTO;
import com.tcc.psiuser.facade.UsuarioFacade;
import com.tcc.psiuser.entity.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "User Controller")
@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioFacade usuarioFacade;

    @Operation(summary = "Faz o registro de um novo usúario")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registrarUsuario(@RequestBody Usuario usuario){
        usuarioFacade.save(usuario);
    }

    @Operation(summary = "Acha o usúario pelo e-mail")
    @GetMapping("/findByEmail")
    public ResponseEntity<Usuario> findByEmail(@RequestParam String email){
        Usuario usuario = usuarioFacade.findByEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Gera e-mail de reset de senha")
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestParam("email") String userEmail) {
        usuarioFacade.resetPassword(userEmail);
        return ResponseEntity.ok("SENHA RESETADA");
    }

    @Operation(summary = "Verifica o token do reset de senha e envia para a página de alteração")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/changePassword")
    public ResponseEntity<?> showChangePasswordPage(@RequestParam("token") String token) {
        if (Boolean.TRUE.equals(usuarioFacade.validatePasswordResetToken(token))) {
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:3000/RedefinitionReturn?token=" + token)).build();
        }else {
            return ResponseEntity.badRequest().body("TOKEN EXPIRADO OU INEXISTENTE");
        }
    }

    @Operation(summary = "Atualiza a senha do usúario")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestParam("token") String token, @RequestBody PasswordDTO passwordDTO) {
       usuarioFacade.updatePassword(token, passwordDTO);
       return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lista todos os profissionais")
    @GetMapping("/allProfessionals")
    public ResponseEntity<?> getProfessionals(){
        return ResponseEntity.ok(usuarioFacade.getProfessionals());
    }

    @Operation(summary = "Lista todos os pacientes")
    @GetMapping("/allPatients")
    public ResponseEntity<?> getPatients(){
        return ResponseEntity.ok(usuarioFacade.getPatients());
    }


    @Operation(summary = "Lista todos os usuarios")
    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(usuarioFacade.getAll());
    }

}
