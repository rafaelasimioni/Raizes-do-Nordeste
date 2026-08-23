# RAÍZES DO NORDESTE

APIRest desenvolvida para uma rede de lanchonetes fictícia, permitindo o gerenciamento de unidades, produtos, estoque e pedidos, com autenticação e autorização por perfil de usuário, além de simulação de pagamentos e atualização do status dos pedidos.

## Tecnologias utilizadas

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring%20Security-4.1.0-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue)
![MapStruct](https://img.shields.io/badge/MapStruct-1.6.3-red)
![JWT](https://img.shields.io/badge/JWT-4.5.2-purple)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-green)
![Maven](https://img.shields.io/badge/Maven-build-red)
![Git](https://img.shields.io/badge/Git-version%20control-orange)
![GitHub](https://img.shields.io/badge/GitHub-repository-black)

## Configuração do ambiente

Antes de executar a aplicação, é necessário configurar as variáveis de ambiente utilizadas para conexão com o banco de dados e autenticação JWT.

### 1. Configuração do `.env`

O projeto disponibiliza um arquivo `.env.example` com as variáveis necessárias.

Faça uma cópia do arquivo:

```bash
cp .env.example .env
```
| Variável      | Descrição                                   |
| ------------- | ------------------------------------------- |
| `DB_URL`      | URL de conexão com o PostgreSQL             |
| `DB_USERNAME` | Usuário do banco de dados                   |
| `DB_PASSWORD` | Senha do banco de dados                     |
| `JWT_SECRET`  | Chave utilizada para geração dos tokens JWT |

## Banco de dados

O projeto utiliza PostgreSQL para persistência dos dados.

### 1. Criar o banco de dados

Crie um banco de dados PostgreSQL para o projeto.

Exemplo:

```sql
CREATE DATABASE raizes_nordeste;
```

2. Configurar a conexão

Após criar o banco, configure no arquivo .env as informações de conexão com o PostgreSQL.

## Instalação

Clone o repositório:

```bash
git clone https://github.com/rafaelasimioni/Raizes-do-Nordeste 
```
Acesse a pasta do projeto: 
```bash
cd Raizes-do-Nordeste
```
Instale as dependências:
```bash
mvn clean install
```
## Execução da aplicação

Para iniciar a aplicação, execute:

```bash
mvn spring-boot:run
```

## Documentação da API

A documentação da API é disponibilizada por meio do Swagger/OpenAPI.

Com a aplicação em execução, acesse:

```text
http://localhost:8080/swagger-ui/index.html
```
## Testes

A coleção de testes da API foi desenvolvida utilizando o Insomnia (formato insomnia v5).

A coleção está disponível no repositório em:

`insomnia/raizes-do-nordeste.yaml`

Para executar os testes:

1. Abra o Insomnia.
2. Importe o arquivo `raizes-do-nordeste.yaml`.
3. Inicie a aplicação.
4. Configure as variáveis de ambiente necessárias.
5. Execute as requisições da coleção.

## Links

- [Repositório no GitHub](https://github.com/rafaelasimioni/Raizes-do-Nordeste)
- [Swagger](http://localhost:8080/swagger-ui/index.html)
- Coleção Insomnia: `insomnia/raizes-do-nordeste.yaml`