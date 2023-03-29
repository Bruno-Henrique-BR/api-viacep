package com.viacep.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Service
@RestController
@RequestMapping("/v1")
@Api(value = "Consulta Endereço API", tags = { "Consulta Endereço" })
public class ConsultaEnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    public ConsultaEnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }
    @ApiOperation(value = "Consulta endereço por CEP", response = EnderecoResponse.class)
    @PostMapping("/consulta-endereco")
    public ResponseEntity<EnderecoResponse> consultaEndereco(@RequestBody @Valid ConsultaEnderecoRequest request) {
        try {
            String cep = request.getCep().replaceAll("\\D+", "");
            if (!validarCep(cep)) {
                return ResponseEntity.badRequest().build();
            }

            Endereco endereco = enderecoService.obterEnderecoPorCep(cep);

            double frete = calcularFrete(endereco.getUf());

            EnderecoResponse response = new EnderecoResponse(
                    endereco.getCep(),
                    endereco.getLogradouro(),
                    endereco.getComplemento(),
                    endereco.getBairro(),
                    endereco.getLocalidade(),
                    endereco.getUf(),
                    frete
            );

            return ResponseEntity.ok(response);
        } catch (EnderecoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private boolean validarCep(String cep) {
        if (cep == null || cep.length() != 8) {
            return false;
        }

        for (int i = 0; i < cep.length(); i++) {
            if (!Character.isDigit(cep.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private double calcularFrete(String uf) {
        switch (uf.toUpperCase()) {
            case "SP":
            case "RJ":
            case "MG":
            case "ES":
                return 7.85;
            case "MT":
            case "MS":
            case "GO":
            case "DF":
                return 12.50;
            case "BA":
            case "SE":
            case "AL":
            case "PE":
            case "PB":
            case "RN":
            case "CE":
            case "PI":
            case "MA":
                return 15.98;
            case "PR":
            case "SC":
            case "RS":
                return 17.30;
            case "AM":
            case "RR":
            case "AP":
            case "PA":
            case "TO":
            case "RO":
            case "AC":
                return 20.83;
            default:
                return 0.0;
        }
    }

}
