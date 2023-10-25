package com.ramon.backend_tgid.repositories;

import com.ramon.backend_tgid.models.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
