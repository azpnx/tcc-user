package com.tcc.psiuser.service;

import com.tcc.psiuser.dto.PasswordDTO;
import com.tcc.psiuser.email.EmailService;
import com.tcc.psiuser.email.token.ConfirmationToken;
import com.tcc.psiuser.email.token.ConfirmationTokenService;
import com.tcc.psiuser.entity.Usuario;
import com.tcc.psiuser.repository.UsuarioRepository;
import com.tcc.psiuser.utils.BuildEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService{

    private final UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(Usuario usuario){

        boolean userExistis = repository
                .findByEmail(usuario.getEmail())
                .isPresent();
        if (userExistis) throw new IllegalStateException("Esse e-mail já está em uso");
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));

        repository.save(usuario);

    }

    public void update(Usuario usuario){
        repository.save(usuario);
    }

    public Usuario findByEmail(String email){
        return repository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("Usuário não existe"));
    }

    public int ativarUsuario(String email) {
        return repository.ativarUsuario(email);
    }

    public void updatePassword(PasswordDTO passwordDTO){
        Usuario usuario = findByEmail(passwordDTO.getEmail());
        usuario.setPassword(bCryptPasswordEncoder.encode(passwordDTO.getPassword()));
        update(usuario);
    }
}
