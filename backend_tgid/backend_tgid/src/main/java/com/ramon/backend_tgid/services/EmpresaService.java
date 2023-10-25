package com.ramon.backend_tgid.services;

import com.ramon.backend_tgid.models.Empresa;
import com.ramon.backend_tgid.models.Transacao;

public interface EmpresaService {

    Empresa criarEmpresa(Empresa empresa);

    Empresa procuraEmpresa(Long id);

    void processarTransacao(Transacao transacao);

    void validarEmpresa(Empresa empresa);


    void atualizarEmpresa(Empresa empresaAtualizada);

    void deletarEmpresa(Long id);
}
