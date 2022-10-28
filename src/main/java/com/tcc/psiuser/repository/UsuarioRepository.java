package com.tcc.psiuser.repository;

import com.tcc.psiuser.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Usuario a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int ativarUsuario(String email);

    @Query("SELECT u FROM Usuario u WHERE u.role = 'ALUNO'")
    List<Usuario> getProfessionals();

    @Query("SELECT u FROM Usuario u WHERE u.role = 'PACIENTE'")
    List<Usuario> getPatients();

    @Query("SELECT u FROM Usuario u WHERE u.role = 'PROFESSOR'")
    List<Usuario> getTeachers();

    @Query("SELECT u FROM Usuario u WHERE u.role = 'ALUNO' and u.professor = :professor")
    List<Usuario> findByProfessor(@Param("professor") String professor);
}
