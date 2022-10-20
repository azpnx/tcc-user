package com.tcc.psiuser.controller.response;

import com.tcc.psiuser.enums.AppUserRoleEnum;
import lombok.*;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse {
    private String email;
    private String nome;
    private AppUserRoleEnum role;
}
