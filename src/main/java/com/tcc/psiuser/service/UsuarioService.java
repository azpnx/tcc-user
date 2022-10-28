package com.tcc.psiuser.service;

import com.tcc.psiuser.controller.response.UsuarioResponse;
import com.tcc.psiuser.dto.PasswordDTO;
import com.tcc.psiuser.entity.Usuario;
import com.tcc.psiuser.enums.AppUserRoleEnum;
import com.tcc.psiuser.mapper.UsuarioMapper;
import com.tcc.psiuser.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UsuarioService{

    private final UsuarioRepository repository;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(Usuario usuario){

        if (AppUserRoleEnum.ALUNO.equals(usuario.getRole())){
            if (Objects.isNull(usuario.getProfessor())){
                throw new IllegalStateException("Você precisa adicionar um professor!");
            }
        }

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

    public List<UsuarioResponse> getProfessionals() {
        return usuarioMapper.usuariosToUsuarioResponse(repository.getProfessionals());
    }

    public List<UsuarioResponse> getPatients() {
        return usuarioMapper.usuariosToUsuarioResponse(repository.getPatients());
    }

    public List<UsuarioResponse> getTeachers() {
        return usuarioMapper.usuariosToUsuarioResponse(repository.getTeachers());
    }

    public List<UsuarioResponse> findyByProfessor(String professor){
        return usuarioMapper.usuariosToUsuarioResponse(repository.findByProfessor(professor));
    }
}
