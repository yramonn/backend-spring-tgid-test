package com.ramon.backend_tgid.repositories;

import com.ramon.backend_tgid.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Override
    Optional<Usuario> findById(Long aLong);
}
