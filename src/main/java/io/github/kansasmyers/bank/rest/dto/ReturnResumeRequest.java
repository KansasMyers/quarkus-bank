package io.github.kansasmyers.bank.rest.dto;

import io.github.kansasmyers.bank.domain.model.OperationType;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class ReturnResumeRequest {

    private Long id;

    private OperationType operationType;

    private Double amount;

    private Double balance;

}
