package com.tcc.psiuser.controller;

import com.tcc.psiuser.dto.PasswordDTO;
import com.tcc.psiuser.facade.UsuarioFacade;
import com.tcc.psiuser.entity.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioFacade usuarioFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registrarUsuario(@RequestBody Usuario usuario){
        usuarioFacade.save(usuario);
    }

    @GetMapping
    public ResponseEntity<Usuario> findByEmail(@RequestParam String email){
        Usuario usuario = usuarioFacade.findByEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestParam("email") String userEmail) {
        usuarioFacade.resetPassword(userEmail);
        return ResponseEntity.ok("SENHA RESETADA");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/changePassword")
    public ResponseEntity<?> showChangePasswordPage(@RequestParam("token") String token) {
        if (usuarioFacade.validatePasswordResetToken(token)) {
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://google.com/")).build();
        }else {
            return ResponseEntity.badRequest().body("TOKEN EXPIRADO OU INEXISTENTE");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestParam("token") String token, @RequestBody PasswordDTO passwordDTO) {
       usuarioFacade.updatePassword(token, passwordDTO);
       return ResponseEntity.noContent().build();
    }

    @GetMapping("/allProfessionals")
    public ResponseEntity<?> getProfessionals(){
        return ResponseEntity.ok(usuarioFacade.getProfessionals());
    }

    @GetMapping("/allPatients")
    public ResponseEntity<?> getPatients(){
        return ResponseEntity.ok(usuarioFacade.getPatients());
    }

    @GetMapping("/allTeachers")
    public ResponseEntity<?> getTeachers(){
        return ResponseEntity.ok(usuarioFacade.getTeachers());
    }

}
