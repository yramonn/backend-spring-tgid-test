package com.ramon.backend_tgid.dtos;

import com.ramon.backend_tgid.models.Usuario;
import lombok.Builder;
import lombok.Data;


@Data
@Builder

public class UsuarioDTO {

    private Long id;
    private String nome;
    private String cnpj_cpf;

    public UsuarioDTO() {

    }


    public UsuarioDTO(Long id, String nome, String cnpj_cpf) {
        this.id = id;
        this.nome = nome;
        this.cnpj_cpf = cnpj_cpf;
    }

    public static EmpresaDTO fromEntityUsuario(Usuario usuario) {
        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setCnpj(usuario.getCnpj_cpf());
        return dto;
    }
}
