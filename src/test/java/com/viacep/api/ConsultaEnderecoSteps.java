package com.viacep.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConsultaEnderecoSteps {

    private ConsultaEnderecoRequest request;
    private ResponseEntity<EnderecoResponse> responseEntity;
    private ViaCepCliente viaCepCliente = new ViaCepCliente();
    private EnderecoService enderecoService = new EnderecoService(viaCepCliente);
    private ConsultaEnderecoController controller;

    @BeforeEach
    public void setUp() {
        controller = new ConsultaEnderecoController(enderecoService);
    }

    @AfterEach
    public void tearDown() {
        controller = null;
    }

    @Test
    @DisplayName("Consulta endereço com CEP válido")
    public void consulta_endereco_com_cep_valido() {
        // Dado
        request = new ConsultaEnderecoRequest();
        request.setCep("01001000"); // CEP válido para o teste

        // Quando
        responseEntity = controller.consultaEndereco(request);

        // Então
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        EnderecoResponse response = responseEntity.getBody();
        assertNotNull(response);

        assertEquals("01001-000", response.getCep());
        assertEquals("Praça da Sé", response.getRua());
        assertEquals("lado ímpar", response.getComplemento());
        assertEquals("Sé", response.getBairro());
        assertEquals("São Paulo", response.getCidade());
        assertEquals("SP", response.getEstado());
        assertEquals(7.85, response.getFrete(), 0.01);
    }
}
