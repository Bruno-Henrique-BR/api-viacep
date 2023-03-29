package com.viacep.api;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ViaCepCliente {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ViaCepCliente.class);

    public Endereco consultarCep(String cep) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Endereco endereco = restTemplate.getForObject("https://viacep.com.br/ws/" + cep + "/json", Endereco.class);
            return endereco;
        } catch (RestClientException e) {
            LOGGER.error("Erro ao consultar o CEP: " + cep, e);
            return null;
        }
    }
}
