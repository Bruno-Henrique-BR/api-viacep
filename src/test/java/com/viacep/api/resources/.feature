Feature: Consulta de endereço por CEP

  Scenario: Consulta um endereço válido
    Given um CEP válido
    When faço uma consulta de endereço
    Then o serviço retorna o endereço correto
