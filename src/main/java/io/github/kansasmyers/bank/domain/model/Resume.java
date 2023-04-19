package io.github.kansasmyers.bank.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "resumes")
@Data
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private OperationType operationType;

    private Double amount;

    private Double balance;

}
