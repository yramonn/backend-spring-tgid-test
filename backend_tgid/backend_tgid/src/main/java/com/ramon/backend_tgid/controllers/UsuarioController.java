package com.ramon.backend_tgid.controllers;

import com.ramon.backend_tgid.dtos.UsuarioDTO;
import com.ramon.backend_tgid.exceptions.RegraNegocioException;
import com.ramon.backend_tgid.models.Usuario;
import com.ramon.backend_tgid.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity criarUsuario(@RequestBody UsuarioDTO dto) {

        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .cnpj_cpf(dto.getCnpj_cpf())
                .build();

        try {
            Usuario novoUsuario = usuarioService.criarUsuario(usuario);
            return new ResponseEntity(novoUsuario, HttpStatus.CREATED);

        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterUsuarioPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.procurarUsuario(id);

            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuario não encontrada para o ID: " + id);
            }

            return ResponseEntity.ok(UsuarioDTO.fromEntityUsuario(usuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Usuario não encontrada para o ID");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        try {
            Usuario usuarioExistente = usuarioService.procurarUsuario(id);
            if (usuarioExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuário não encontrado para o ID: " + id);
            }

            usuarioExistente.setNome(dto.getNome());
            usuarioExistente.setCnpj_cpf(dto.getCnpj_cpf());
            usuarioService.atualizarUsuario(usuarioExistente);

            return ResponseEntity.ok("Usuário atualizado com sucesso");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar usuário para o ID: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirUsuario(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.procurarUsuario(id);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuário não encontrado para o ID: " + id);
            }

            usuarioService.excluirUsuario(id);
            return ResponseEntity.ok("Usuário excluído com sucesso");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir usuário para o ID: " + id);
        }
    }
}
