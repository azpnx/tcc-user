package com.tcc.psiuser.facade;

import com.tcc.psiuser.dto.PasswordDTO;
import com.tcc.psiuser.email.EmailService;
import com.tcc.psiuser.email.token.ConfirmationToken;
import com.tcc.psiuser.email.token.ConfirmationTokenService;
import com.tcc.psiuser.entity.PasswordResetToken;
import com.tcc.psiuser.entity.Usuario;
import com.tcc.psiuser.enums.MessageEnum;
import com.tcc.psiuser.service.PasswordService;
import com.tcc.psiuser.service.UsuarioService;
import com.tcc.psiuser.utils.BuildEmail;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Component
@NoArgsConstructor
public class UsuarioFacade {


    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ConfirmationTokenService tokenService;

    private MessageEnum messageEnum;

    @Value("${endpoint.token-confirm}")
    private String ENDPOINT_EMAIL;

    public void save(Usuario usuario){
        usuarioService.save(usuario);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                usuario
        );

        tokenService.saveConfirmationToken(confirmationToken);
        String link = MessageFormat.format("http://{0}/api/usuarios/confirmar?token=" + token, ENDPOINT_EMAIL);
        emailService.sendConfirmEmail(usuario.getEmail(), BuildEmail.buildEmail(MessageEnum.REGISTER.getTitulo(), usuario.getEmail(), MessageEnum.REGISTER.getDescricao(), link, messageEnum.REGISTER.getBotao()));
    }

    public Usuario findByEmail(String email){
        return usuarioService.findByEmail(email);
    }

    public void resetPassword(String email){
        Usuario usuario = usuarioService.findByEmail(email);
        String token = UUID.randomUUID().toString();
        passwordService.createPasswordResetTokenForUser(usuario, token);

        String link = MessageFormat.format("http://{0}/api/usuarios/reset?token=" + token, ENDPOINT_EMAIL);
        emailService.sendResetEmail(email,BuildEmail.buildEmail(MessageEnum.REGISTER.getTitulo(), usuario.getEmail(), MessageEnum.RESET.getDescricao(), link, MessageEnum.RESET.getBotao()));
    }

    public Boolean validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordService.findByToken(token);
        return isTokenExpired(passToken);
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        return !passToken.getExpiryDate().isBefore(LocalDateTime.now());
    }

    public void updatePassword(String token, PasswordDTO passwordDTO){
        if (validatePasswordResetToken(token)){
            usuarioService.updatePassword(passwordDTO);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token expirado!");
        }
    }

    public List<Usuario> getProfessionals(){
        return usuarioService.getProfessionals();
    }

    public List<Usuario> getPatients(){
        return usuarioService.getPatients();
    }

    public List<Usuario> getTeachers(){
        return usuarioService.getTeachers();
    }
}
