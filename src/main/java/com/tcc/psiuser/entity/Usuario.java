package com.tcc.psiuser.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcc.psiuser.enums.AppUserRoleEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 60)
    private String email;
    @JsonIgnore
    private String password;
    private String nome;
    @Enumerated(EnumType.STRING)
    private AppUserRoleEnum role;
    @JsonIgnore
    private Boolean blocked = false;
    @JsonIgnore
    private Boolean enabled = false;
}
