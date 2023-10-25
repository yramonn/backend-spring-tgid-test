package com.ramon.backend_tgid.services.impl;

import com.ramon.backend_tgid.models.Empresa;
import com.ramon.backend_tgid.models.Transacao;
import com.ramon.backend_tgid.services.NotificacaoService;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoServiceImpl implements NotificacaoService {

    @Override
    public void enviarCallbackEmpresa(Empresa empresa, Transacao transacao) {

    }
}
