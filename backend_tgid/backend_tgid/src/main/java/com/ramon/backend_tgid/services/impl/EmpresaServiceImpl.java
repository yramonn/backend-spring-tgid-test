package com.ramon.backend_tgid.services.impl;

import com.ramon.backend_tgid.exceptions.RegraNegocioException;
import com.ramon.backend_tgid.models.Empresa;
import com.ramon.backend_tgid.models.Transacao;
import com.ramon.backend_tgid.repositories.EmpresaRepository;
import com.ramon.backend_tgid.services.EmpresaService;
import com.ramon.backend_tgid.services.NotificacaoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final NotificacaoService notificacaoService;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository, NotificacaoService notificacaoService) {
        this.empresaRepository = empresaRepository;
        this.notificacaoService = notificacaoService;
    }

    @Override
    public Empresa criarEmpresa(Empresa empresa) {
        validarEmpresa(empresa);
        return  empresaRepository.save(empresa);

    }

    @Override
    public Empresa procuraEmpresa(Long id) {
        Optional<Empresa> empresaOptional = empresaRepository.findById(id);
        if (empresaOptional.isEmpty()) {
            throw new RegraNegocioException("Essa empresa não existe!");
        }
        return empresaOptional.get();
    }


    @Override
    public void processarTransacao(Transacao transacao) {

        notificacaoService.enviarCallbackEmpresa(transacao.getEmpresa(), transacao);
    }



    @Override
    public void validarEmpresa(Empresa empresa) {

        if (empresa.getNome() == null || empresa.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("Informe o Nome da Empresa");
        }

        if (empresa.getCnpj() == null || empresa.getCnpj().trim().isEmpty()) {
            throw new RegraNegocioException("Informe o CNPJ da Empresa");
        }

    }

    @Override
    public void atualizarEmpresa(Empresa empresaAtualizada) throws RegraNegocioException {
        Empresa empresaExistente = empresaRepository.findById(empresaAtualizada.getId()).orElse(null);
        if (empresaExistente == null) {
            throw new RegraNegocioException("Empresa não encontrada para o ID: " + empresaAtualizada.getId());
        }

        empresaExistente.setNome(empresaAtualizada.getNome());
        empresaExistente.setCnpj(empresaAtualizada.getCnpj());

        empresaRepository.save(empresaExistente);
    }

    @Override
    public void deletarEmpresa(Long id) throws RegraNegocioException {
        Empresa empresa = empresaRepository.findById(id).orElseThrow(() ->
                new RegraNegocioException("Empresa não encontrada para o ID: " + id));

        empresaRepository.delete(empresa);
    }

}
