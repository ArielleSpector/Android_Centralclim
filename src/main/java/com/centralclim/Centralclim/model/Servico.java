package com.centralclim.Centralclim.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "servicos")

public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // tipo de servico
    @Column(name = "descricao")
    private String descricao;

    //data do agendamento
    @Column(name = "data_agendamento")
    private LocalDate dataAgendamento;

    private BigDecimal valor;

    //Funcionario que vai fazer o servico
    @ManyToOne
    @JoinColumn(name = "usuario_id") // relacionando com a tabela usuario
    private Usuario usuario;

    //Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id") // relacionando com a tabela clientes
    private Cliente cliente;

    //controlar o andamento do servico
    @Enumerated(EnumType.STRING)
    private StatusServico status;




}
