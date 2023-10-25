package com.ramon.backend_tgid.services;

import com.ramon.backend_tgid.models.Empresa;
import com.ramon.backend_tgid.models.Transacao;
import com.ramon.backend_tgid.models.Usuario;

public interface NotificacaoService {

    void enviarCallbackEmpresa(Empresa empresa, Transacao transacao);
}
