package com.ramon.backend_tgid.services;

import com.ramon.backend_tgid.dtos.TransacaoDTO;
import com.ramon.backend_tgid.dtos.UsuarioDTO;
import com.ramon.backend_tgid.models.Transacao;

public interface TransacaoService {
    Transacao realizarTransacao(TransacaoDTO transacaoDTO, Long usuarioDTO, boolean isSaque);

    Transacao obterTransacaoPorId(Long id);
}
