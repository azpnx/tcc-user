package com.tcc.psiuser.mapper;

import com.tcc.psiuser.controller.response.UsuarioResponse;
import com.tcc.psiuser.entity.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioResponse usuarioToUsuarioResponse(Usuario usuario);

    List<UsuarioResponse> usuariosToUsuarioResponse(List<Usuario> usuarios);

}
