package com.tcc.psiuser.service;

import com.tcc.psiuser.email.EmailService;
import com.tcc.psiuser.email.token.ConfirmationToken;
import com.tcc.psiuser.email.token.ConfirmationTokenService;
import com.tcc.psiuser.entity.Usuario;
import com.tcc.psiuser.repository.UsuarioRepository;
import com.tcc.psiuser.utils.BuildEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService{

    private final UsuarioRepository repository;
    private final ConfirmationTokenService tokenService;
    private final EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(Usuario usuario){

        boolean userExistis = repository
                .findByEmail(usuario.getEmail())
                .isPresent();
        if (userExistis) throw new IllegalStateException("Esse e-mail já está em uso");
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));

        repository.save(usuario);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                usuario
        );

        tokenService.saveConfirmationToken(confirmationToken);
        String link = "http://localhost:8125/api/usuarios/confirmar?token=" + token;
        emailService.send(usuario.getEmail(), BuildEmail.buildEmail(usuario.getEmail(), link));
    }

    public Usuario findByEmail(String email){
        return repository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("Usuário não existe"));
    }

    public int ativarUsuario(String email) {
        return repository.ativarUsuario(email);
    }

}
