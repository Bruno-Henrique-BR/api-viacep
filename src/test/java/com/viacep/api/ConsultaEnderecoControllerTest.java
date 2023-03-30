package com.viacep.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

public class ConsultaEnderecoControllerTest {

    @Mock
    private EnderecoService enderecoService;

    @InjectMocks
    private ConsultaEnderecoController consultaEnderecoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar status 200 e o endereço com frete correto")
    void deveRetornarEnderecoComFreteCorreto() throws EnderecoNaoEncontradoException {
        // Arrange
        ConsultaEnderecoRequest request = new ConsultaEnderecoRequest();
        request.setCep("12345678");

        Endereco endereco = new Endereco();
        endereco.setCep("12345678");
        endereco.setLogradouro("Rua Exemplo");
        endereco.setComplemento("Apto 123");
        endereco.setBairro("Bairro Exemplo");
        endereco.setLocalidade("Cidade Exemplo");
        endereco.setUf("SP");

        when(enderecoService.obterEnderecoPorCep(ArgumentMatchers.anyString())).thenReturn(endereco);

        // Act
        ResponseEntity<EnderecoResponse> responseEntity = consultaEnderecoController.consultaEndereco(request);

        // Assert
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        EnderecoResponse response = responseEntity.getBody();
        Assertions.assertEquals("12345678", response.getCep());
        Assertions.assertEquals("Rua Exemplo", response.getRua());
        Assertions.assertEquals("Apto 123", response.getComplemento());
        Assertions.assertEquals("Bairro Exemplo", response.getBairro());
        Assertions.assertEquals("Cidade Exemplo", response.getCidade());
        Assertions.assertEquals("SP", response.getEstado());
        Assertions.assertEquals(7.85, response.getFrete());
    }

    @Test
    @DisplayName("Deve retornar status 404 quando o endereço não é encontrado")
    void deveRetornarStatus404QuandoEnderecoNaoEncontrado() throws EnderecoNaoEncontradoException {
        // Arrange
        ConsultaEnderecoRequest request = new ConsultaEnderecoRequest();
        request.setCep("12345678");

        when(enderecoService.obterEnderecoPorCep(ArgumentMatchers.anyString())).thenThrow(EnderecoNaoEncontradoException.class);

        // Act
        ResponseEntity<EnderecoResponse> responseEntity = consultaEnderecoController.consultaEndereco(request);

        // Assert
        Assertions.assertNull(responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
