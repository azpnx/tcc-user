package com.tcc.psiuser.entity;

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
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRoleEnum role;
    private Boolean blocked = false;
    private Boolean enabled = false;
}
