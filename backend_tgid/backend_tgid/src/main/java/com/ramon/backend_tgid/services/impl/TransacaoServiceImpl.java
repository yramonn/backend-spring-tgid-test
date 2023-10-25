package com.ramon.backend_tgid.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ramon.backend_tgid.models.Transacao;
import com.ramon.backend_tgid.models.Usuario;
import com.ramon.backend_tgid.producer.UsuarioProducer;
import com.ramon.backend_tgid.repositories.TransacaoRepository;
import com.ramon.backend_tgid.repositories.UsuarioRepository;
import com.ramon.backend_tgid.services.TransacaoService;
import org.springframework.stereotype.Service;

import com.ramon.backend_tgid.dtos.TransacaoDTO;
import com.ramon.backend_tgid.models.Empresa;
import com.ramon.backend_tgid.repositories.EmpresaRepository;
import com.ramon.backend_tgid.exceptions.RegraNegocioException;

import java.util.Date;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioProducer usuarioProducer;

    public TransacaoServiceImpl(TransacaoRepository transacaoRepository, EmpresaRepository empresaRepository, UsuarioRepository usuarioRepository, UsuarioProducer usuarioProducer) {
        this.transacaoRepository = transacaoRepository;
        this.empresaRepository = empresaRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioProducer = usuarioProducer;
    }

    public Transacao realizarTransacao(TransacaoDTO transacaoDTO, Long idUsuario, boolean isSaque) {

        Empresa empresa = empresaRepository.findById(transacaoDTO.getIdEmpresa())
                .orElseThrow(() -> new RegraNegocioException("Empresa não encontrada"));
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraNegocioException("Usuario não encontrada"));

        Transacao transacao = new Transacao();
        transacao.setValor(transacaoDTO.getValor());
        transacao.setData(new Date());
        transacao.setEmpresa(empresa);
        transacao.setUsuario(usuario);

        if (transacaoDTO.getValor() <= 0) {
            throw new RegraNegocioException("Valor da transação deve ser maior que zero");
        }

        if (isSaque) {
            if (transacaoDTO.getValor() > empresa.getSaldo()) {
                throw new RegraNegocioException("Saldo Insuficiente");
            }

            if (transacaoDTO.getValor() <= empresa.getSaldo()) {
                double novoSaldo = empresa.getSaldo() - transacao.getValor() - empresa.getTaxa();
                empresa.setSaldo(novoSaldo);
            }
        } else {
            double novoSaldo = empresa.getSaldo() + transacao.getValor() - empresa.getTaxa();
            empresa.setSaldo(novoSaldo);
        }

        transacaoRepository.save(transacao);
        empresaRepository.save(empresa);

        if (usuario.getEmail()!= null) {
            try {
                usuarioProducer.publishMessageEmail(usuario);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return transacao;
    }


    @Override
    public Transacao obterTransacaoPorId(Long id) {
        return null;
    }
}
