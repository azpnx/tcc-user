package com.tcc.psiuser.service;

import com.tcc.psiuser.entity.PasswordResetToken;
import com.tcc.psiuser.entity.Usuario;
import com.tcc.psiuser.repository.PasswordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PasswordService {


    PasswordRepository passwordRepository;

    public void createPasswordResetTokenForUser(Usuario usuario, String token) {
        PasswordResetToken pwdToken = new PasswordResetToken(token, usuario);
        pwdToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        passwordRepository.save(pwdToken);
    }

    public PasswordResetToken findByToken(String token) {
        return passwordRepository.findByToken(token).orElseThrow(
                () -> new IllegalArgumentException("Token não existe!"));
    }
}
