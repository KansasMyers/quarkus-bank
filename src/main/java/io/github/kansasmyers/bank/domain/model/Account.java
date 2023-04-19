package io.github.kansasmyers.bank.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "id_agency")
    private Integer idAgency;

    private Double balance;

}
