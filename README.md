![Cobertura de Testes](.github/badges/coverage-badge.svg)

InsuMax - Backend (Stock API)

API REST responsável pelo gerenciamento de **produtos**,
**matérias-primas** e **simulação de produção** da aplicação
**InsuMax**.

##  Tecnologias Utilizada
-   Java 21
-   Spring Boot 3
-   Docker
-   Oracle Database

## Arquitetura

O projeto segue uma abordagem baseada em:

-   Use Cases (Application Layer)
-   Separação entre leitura e escrita
-   Clean Code e SOLID
-   Tratamento global de exceções

Estrutura principal:

    src/main/java/com/gustavonascimento/stock
     ├── config/
     ├── controllers/
     ├── entities/
     ├── repositories/
     ├── records/
     ├── usecases/
     ├── security/
     └── exceptions/
     
# Executando com Docker Compose (Recomendado)

O projeto já possui um `docker-compose.yml` configurado com:

-   Oracle XE
-   Backend Spring Boot

### Subir os serviços

``` bash
docker compose up --build
```

A aplicação estará disponível em:

    http://localhost:8080

Swagger:

    http://localhost:8080/swagger-ui/index.html#/

## Banco Oracle

-   Porta: 1521\
-   Database: XEPDB1\
-   Usuário: estoque\
-   Senha: Estoque@123


# Rodando via Docker Hub

Imagem publicada:

    limanascimento/stock:latest

### Pull da imagem

``` bash
docker pull limanascimento/stock:latest
```

### Executar container

``` bash
docker run -p 8080:8080 limanascimento/stock:latest
```

# Rodando os testes

``` bash
mvn clean test
```

Testes unitários cobrem:

-   Repositórios
-   Use Cases
-   Regras da simulação de produção

# Documentação da API

Swagger disponível em:

    http://localhost:8080/swagger-ui/index.html#/


# Principais Funcionalidades

-   CRUD de Produtos
-   CRUD de Matérias-Primas
-   Associação Produto × Matéria-Prima
-   Simulação de Produção com priorização por maior valor

# Variáveis de Ambiente

    SPRING_PROFILES_ACTIVE
    SPRING_DATASOURCE_URL
    SPRING_DATASOURCE_USERNAME
    SPRING_DATASOURCE_PASSWORD
