package com.ramon.backend_tgid.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class TransacaoDTO {

    private Long idEmpresa;
    private double valor;
    private String data;
}
