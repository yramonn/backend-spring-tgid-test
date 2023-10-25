package com.ramon.backend_tgid.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "tb_transacao")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double valor;
    private Date data;
    @ManyToOne(optional = false)
    @NotNull
    private Empresa empresa;
    @ManyToOne(optional = false)
    @NotNull
    private Usuario usuario;


}
