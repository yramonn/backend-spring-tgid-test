package com.ramon.backend_tgid.services;

import com.ramon.backend_tgid.models.Transacao;
import com.ramon.backend_tgid.models.Usuario;

public interface UsuarioService {

    Usuario criarUsuario(Usuario usuario);

    Usuario procurarUsuario(Long id);

    void validarUsuario(Usuario usuario);

    void atualizarUsuario(Usuario usuarioExistente);

    void excluirUsuario(Long id);
}
