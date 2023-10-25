package com.ramon.backend_tgid.repositories;

import com.ramon.backend_tgid.models.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
