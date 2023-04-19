package io.github.kansasmyers.bank.rest.dto;

import io.github.kansasmyers.bank.domain.model.OperationType;
import io.github.kansasmyers.bank.util.ValueOfEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateOperationRequest {

    @NotNull
    private Double amount;

    @ValueOfEnum(enumClass = OperationType.class, message = "accepted values: WITHDRAW, DEPOSIT")
    private String type;

}
