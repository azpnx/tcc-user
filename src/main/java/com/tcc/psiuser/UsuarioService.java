package com.tcc.psiuser;

import com.tcc.psiuser.entity.Usuario;
import com.tcc.psiuser.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService{

    private final UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(Usuario usuario){
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        repository.save(usuario);
    }

    public Usuario findByEmail(String email){
        return repository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("Usuário não existe"));
    }

}
