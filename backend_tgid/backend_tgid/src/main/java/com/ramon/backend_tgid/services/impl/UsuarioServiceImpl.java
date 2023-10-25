package com.ramon.backend_tgid.services.impl;

import com.ramon.backend_tgid.exceptions.RegraNegocioException;
import com.ramon.backend_tgid.models.Usuario;
import com.ramon.backend_tgid.repositories.UsuarioRepository;
import com.ramon.backend_tgid.services.UsuarioService;
import com.ramon.backend_tgid.services.NotificacaoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final NotificacaoService notificacaoService;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, NotificacaoService notificacaoService) {
        this.usuarioRepository = usuarioRepository;
        this.notificacaoService = notificacaoService;
    }


    @Override
    public Usuario criarUsuario(Usuario cliente) {

        return usuarioRepository.save(cliente);
    }


    @Override
    public Usuario procurarUsuario(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            throw new RegraNegocioException("Essee usuário não existe!");
        }
        return usuarioOptional.get();
    }

    @Override
    public void validarUsuario(Usuario usuario) {

        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("Informe seu Nome");
        }



        if (usuario.getCnpj_cpf() == null || usuario.getCnpj_cpf().trim().isEmpty()) {
            throw new RegraNegocioException("Informe seu CPF");
        }

    }

    @Override
    public void atualizarUsuario(Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(usuarioAtualizado.getId()).orElse(null);
        if (usuarioExistente == null) {
            throw new RegraNegocioException("Usuario não encontrada para o ID: " + usuarioExistente.getId());
        }

        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setCnpj_cpf(usuarioAtualizado.getCnpj_cpf());
        validarUsuario(usuarioAtualizado);

        usuarioRepository.save(usuarioExistente);
    }

    @Override
    public void excluirUsuario(Long id) throws RegraNegocioException {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() ->
                new RegraNegocioException("Usuario não encontrada para o ID: " + id));

        usuarioRepository.delete(usuario);
    }

}
