package com.ramon.backend_tgid.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tb_empresa")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nome;
    @NotNull
    private String cnpj;
    private double saldo;
    private double taxa;
    @OneToMany(mappedBy = "empresa")
    private List<Transacao> transacoes;
}
