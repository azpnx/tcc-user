package com.tcc.psiuser.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageEnum {

    REGISTER("Obrigado por se registrar conosco!. Por favor clique no link abaixo e confirme sua conta:","Confirme seu e-mail", "Confirmar e-mail"),
    RESET("Você esqueceu sua senha? Clique no link abaixo e cadastre uma nova senha:", "Redefina sua conta!", "Redefinir senha");

    private String descricao;
    private String titulo;
    private String botao;
}
