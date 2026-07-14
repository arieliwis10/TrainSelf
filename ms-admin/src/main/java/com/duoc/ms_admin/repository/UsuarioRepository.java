package com.duoc.ms_admin.repository;

import com.duoc.ms_admin.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}