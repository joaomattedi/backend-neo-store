# Fornecedor Backend

Este é o backend para o sistema de cadastro de fornecedores da NeoStore, desenvolvido em Java com Hibernate, JPA, JUnit5, Http nativo do Java, Docker para o banco de dados e PostgreSQL como banco de dados.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal do projeto.
- **HttpServer**: Utilizado para criar a API REST nativa disponível em `localhost:8080/api`.
- **Hibernate**: Framework ORM utilizado para simplificar a persistência de dados no banco de dados.
- **JPA**: Usado para abstrair a implementação de persistência.
- **JUnit5**: Framework de testes para garantir a qualidade do código.
- **PostgreSQL**: Banco de dados relacional escolhido pela sua robustez e compatibilidade.
- **Docker**: Usado para criar e gerenciar containers, garantindo que o banco de dados possa ser facilmente replicado.
- **Maven**: Facilidade de uso e solicitado no desafio.

## Como Executar o Projeto

### Pré-requisitos

- **Java 17** ou superior
- **Maven** para gerenciar as dependências
- **Docker** para subir o banco de dados PostgreSQL

### Passos para Executar

1. Faça um fork deste repositório
2. Clone o repositório:
   ```bash
   git clone https://github.com/seuprojeto/backend-neo-store.git

3. Entre na pasta do projeto backend:
   ```bash
   cd /caminhodoseuprojeto

4. Compile e empacote o projeto utilizando Maven:
   ```bash
   mvn clean package
   
5. Inicie o banco de dados PostgreSQL utilizando Docker:
   ```bash
   docker-compose up -d

6. Rode o projeto com o comando `mvn exec:java "-Dexec.mainClass=org.desafio.neo.Application"`
    
7. A API estará disponível em `http://localhost:8080/api`

### Rotas da API

- `GET /api/fornecedores/list`: Lista todos os fornecedores com paginação.
- `POST /api/fornecedores`: Cadastra fornecedores em massa a partir de um arquivo JSON.
- `PUT /api/fornecedores/{id}`: Atualiza as informações de um fornecedor específico.
- `DELETE /api/fornecedores/{id}`: Remove um fornecedor do banco de dados.

## Decisões de Desenvolvimento

No início do desenvolvimento da aplicação, optei por utilizar o HttpServer do próprio Java, já que eu não sabia se eu poderia utilizar outros Frameworks como Quark.

Utilizando de boas práticas, separei as reponsabilidades dos endpoints, apliquei a estrutura de pastas mais popular e convencional e implementei alguns testes unitários de acordo com o solicitado.

A estratégia utilizada para lidar com as requisições foi algo que acabei aprendendo conforme fluiu o desenvolvimento, visto que eu nunca havia de fato desenvolvido uma API Rest com o HttpServer.
