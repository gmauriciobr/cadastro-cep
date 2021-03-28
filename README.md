# Especificações

- Docker
- Java 8
- Spring Boot
- Spring Data
- Feign
- Swagger
- MySQL 5.7

#### Baixar o projeto utilizando o git:
 ``` 
    git clone https://github.com/gmauriciobr/cadastro-cep.git
 ```

 1. Executar o comando para fazer o build e aguardar a execução:
    ``` 
       docker-compose build 
    ```

 2. Depois de executado o comando anterior, executar o seguinte:
    ``` 
       docker-compose up -d 
    ```

### Link
1. Swagger: http:// localhost:8080/api


### Serviços

```
curl -X POST "http://localhost:8080/api/cadastro" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"cep\": \"01310100\", \"cpf\": \"12312312312\", \"email\": \"fulano@email.com\", \"nome\": \"fulano\"}"
```

```
curl -X PUT "http://localhost:8080/api/cadastro/1" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"cep\": \"01449001\", \"nome\": \"ciclano\"}"
```

```
curl -X GET "http://localhost:8080/api/cadastro/find?email=fulano%40email.com" -H "accept: */*"
```




