package com.viacep.api;

import com.viacep.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ConsultaEnderecoControllerTest {

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

        when(enderecoService.obterEnderecoPorCep(anyString())).thenReturn(endereco);

        // Act
        ResponseEntity<EnderecoResponse> responseEntity = consultaEnderecoController.consultaEndereco(request);

        // Assert
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        EnderecoResponse response = responseEntity.getBody();
        assertEquals("12345678", response.getCep());
        assertEquals("Rua Exemplo", response.getRua());
        assertEquals("Apto 123", response.getComplemento());
        assertEquals("Bairro Exemplo", response.getBairro());
        assertEquals("Cidade Exemplo", response.getCidade());
        assertEquals("SP", response.getEstado());
        assertEquals(7.85, response.getFrete());
    }

    @Test
    @DisplayName("Deve retornar status 404 quando o endereço não é encontrado")
    void deveRetornarStatus404QuandoEnderecoNaoEncontrado() throws EnderecoNaoEncontradoException {
        // Arrange
        ConsultaEnderecoRequest request = new ConsultaEnderecoRequest();
        request.setCep("12345678");

        when(enderecoService.obterEnderecoPorCep(anyString())).thenThrow(EnderecoNaoEncontradoException.class);

        // Act
        ResponseEntity<EnderecoResponse> responseEntity = consultaEnderecoController.consultaEndereco(request);

        // Assert
        assertNull(responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar status 400 quando o CEP é inválido")
    void deveRetornarStatus400QuandoCepInvalido() throws EnderecoNaoEncontradoException {
        // Arrange
        ConsultaEnderecoRequest request = new ConsultaEnderecoRequest();
        request.setCep("12345-678");

        // Act
        ResponseEntity<EnderecoResponse> responseEntity = consultaEnderecoController.consultaEndereco(request);

        // Assert
        assertNull(responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
