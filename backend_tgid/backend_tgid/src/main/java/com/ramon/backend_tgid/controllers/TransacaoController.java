package com.ramon.backend_tgid.controllers;

import com.ramon.backend_tgid.dtos.TransacaoDTO;
import com.ramon.backend_tgid.exceptions.RegraNegocioException;
import com.ramon.backend_tgid.models.Empresa;
import com.ramon.backend_tgid.models.Transacao;
import com.ramon.backend_tgid.models.Usuario;
import com.ramon.backend_tgid.services.EmpresaService;
import com.ramon.backend_tgid.services.TransacaoService;
import com.ramon.backend_tgid.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/usuarios/{usuarioId}/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;
    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;

    public TransacaoController(TransacaoService transacaoService, UsuarioService usuarioService, EmpresaService empresaService) {
        this.transacaoService = transacaoService;
        this.usuarioService = usuarioService;
        this.empresaService = empresaService;
    }

    @PostMapping
    public ResponseEntity criarTransacaoParaUsuario(@PathVariable Long usuarioId , @RequestBody TransacaoDTO transacaoDTO) {

        Usuario usuario = usuarioService.procurarUsuario(usuarioId);
        Empresa empresa = empresaService.procuraEmpresa(transacaoDTO.getIdEmpresa());

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        Transacao transacao = new Transacao();
        transacao.setValor(transacaoDTO.getValor());
        transacao.setData(new Date());
        transacao.setUsuario(usuario);
        transacao.setEmpresa(empresa);

        boolean isSaque = false;

        try {
            Transacao novaTransacao = transacaoService.realizarTransacao(transacaoDTO,usuarioId, isSaque);
            return new ResponseEntity(novaTransacao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

