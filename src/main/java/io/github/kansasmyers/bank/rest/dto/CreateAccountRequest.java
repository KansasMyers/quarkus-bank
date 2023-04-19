package io.github.kansasmyers.bank.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateAccountRequest {

    @Size(min = 2, max = 255, message = "name should have size [{min},{max}]")
    @NotBlank(message = "name may not be blank")
    private String name;

}
