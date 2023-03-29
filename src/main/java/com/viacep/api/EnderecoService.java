package com.viacep.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {


    @Autowired
    private ViaCepCliente viaCepCliente;

    @Autowired
    public EnderecoService(ViaCepCliente viaCepCliente) {
        this.viaCepCliente = viaCepCliente;
    }

    public Endereco obterEnderecoPorCep(String cep) throws EnderecoNaoEncontradoException {
        Endereco endereco = viaCepCliente.consultarCep(cep);
        if (endereco == null) {
            throw new EnderecoNaoEncontradoException("Endereço não encontrado para o CEP informado.");
        }
        return endereco;
    }
}
