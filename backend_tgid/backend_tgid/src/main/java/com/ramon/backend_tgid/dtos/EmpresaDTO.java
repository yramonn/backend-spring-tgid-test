package com.ramon.backend_tgid.dtos;

import com.ramon.backend_tgid.models.Empresa;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class EmpresaDTO {

    private Long id;
    private String nome;
    private String cnpj;
    private double saldo;
    private double taxa;

    public EmpresaDTO() {

    }

    public EmpresaDTO(Long id, String nome, String cnpj, double saldo, double taxa) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.saldo = saldo;
        this.taxa = taxa;
    }

    public static EmpresaDTO fromEntityEmpresa(Empresa empresa) {
        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(empresa.getId());
        dto.setNome(empresa.getNome());
        dto.setCnpj(empresa.getCnpj());
        dto.setSaldo(empresa.getSaldo());
        dto.setTaxa(empresa.getTaxa());
        return dto;
    }

}
