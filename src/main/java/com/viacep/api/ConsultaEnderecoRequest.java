package com.viacep.api;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ConsultaEnderecoRequest {

    @NotNull
    @Pattern(regexp = "[0-9]{8}")
    private String cep;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}

