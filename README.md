# api-viacep

# Consulta de endereço por CEP 
Este é um projeto para consulta de endereço a partir de um CEP. 
Ele recebe uma requisição com um CEP válido e retorna as informações do endereço correspondente, juntamente com o valor do frete para aquela região.  
# Como utilizar
Para utilizar o serviço, é necessário enviar uma requisição POST para a rota /v1/consulta-endereco, informando o CEP desejado no corpo da requisição, no formato JSON. O CEP deve conter apenas números.  
Exemplo de requisição:  

### POST /v1/consulta-endereco 
```
{   
"cep": "01234567" 
} 
```
### Exemplo de resposta:  
```
{   
"cep": "01234567",   
"logradouro": 
"Rua Exemplo",   
"complemento": "",   
"bairro": 
"Bairro Exemplo",   
"localidade": "São Paulo",   
"uf": "SP",   
"frete": 7.85 
} 
```
Caso o CEP informado não seja válido, a resposta será um código de erro 400 (Bad Request). 
Se o CEP informado não for encontrado, a resposta será um código de erro 404 (Not Found).  
# Como visualizar a documentação da API utilizando o Swagger 
Para visualizar a documentação da API utilizando o Swagger, basta acessar a URL http://localhost:8080/swagger-ui.html após executar o projeto. 
Lá você encontrará todos os endpoints disponíveis, bem como os parâmetros necessários para cada requisição. 
Você também pode testar cada endpoint diretamente pelo Swagger.  
# Tecnologias utilizadas 
O projeto foi desenvolvido utilizando o framework Spring Boot e a linguagem de programação Java. Para a documentação da API, foi utilizado o Swagger. O serviço de consulta de endereço foi implementado utilizando a API pública ViaCEP.
