package com.ramon.backend_tgid.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramon.backend_tgid.controllers.UsuarioController;
import com.ramon.backend_tgid.dtos.UsuarioDTO;
import com.ramon.backend_tgid.models.Usuario;
import com.ramon.backend_tgid.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(UsuarioController.class)

public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @Test
    public void testCriarUsuario() throws Exception {

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Exemplo");
        usuarioDTO.setCnpj_cpf("123456789");

        String usuarioJson = objectMapper.writeValueAsString(usuarioDTO);

        Usuario usuarioCriado = new Usuario();
        usuarioCriado.setId(1L);
        usuarioCriado.setNome("Exemplo");
        usuarioCriado.setCnpj_cpf("123456789");
        when(usuarioService.criarUsuario(Mockito.any(Usuario.class))).thenReturn(usuarioCriado);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists()) // Verifica se o ID foi gerado
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Exemplo")) // Verifica o nome
                .andExpect(MockMvcResultMatchers.jsonPath("$.cnpj_cpf").value("123456789")); // Verifica o CNPJ/CPF
    }

    @Test
    public void testObterUsuarioPorId() throws Exception {

        Long id = 1L;

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome("Exemplo");
        usuario.setCnpj_cpf("123456789");

        when(usuarioService.procurarUsuario(id)).thenReturn(usuario);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/usuarios/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Exemplo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cnpj").value("123456789")); // Ajuste aqui para "cnpj"
    }

    @Test
    public void testAtualizarUsuario() throws Exception {

        Long id = 1L;

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Novo Nome");
        usuarioDTO.setCnpj_cpf("Novo CNPJ/CPF");

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(id);
        usuarioExistente.setNome("Nome Antigo");
        usuarioExistente.setCnpj_cpf("CNPJ Antigo");
        when(usuarioService.procurarUsuario(id)).thenReturn(usuarioExistente);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/usuarios/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Novo Nome\",\"cnpj_cpf\":\"Novo CNPJ/CPF\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Usuário atualizado com sucesso"));

        verify(usuarioService).atualizarUsuario(usuarioExistente);
        assertEquals("Novo Nome", usuarioExistente.getNome());
        assertEquals("Novo CNPJ/CPF", usuarioExistente.getCnpj_cpf());
    }

    @Test
    public void testExcluirUsuario() throws Exception {
        Long id = 1L;

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(id);
        when(usuarioService.procurarUsuario(id)).thenReturn(usuarioExistente);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/usuarios/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Usuário excluído com sucesso"));
        verify(usuarioService).excluirUsuario(id);
    }
}

