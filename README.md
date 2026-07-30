# 📚 Biblioteca API

API REST para gerenciamento de biblioteca, construída com Java e Spring Boot. Permite o cadastro de livros, autores e usuários, além do controle completo de empréstimos — incluindo a atualização automática de estoque disponível.

## 🚀 Tecnologias

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Lombok
- Bean Validation (Jakarta Validation)
- Maven

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

```
Controller → Service → Repository → Model (Entidade)
```

- **Controller** — expõe os endpoints REST e valida as requisições
- **Service** — concentra a lógica de negócio (ex: controle de estoque de empréstimos)
- **Repository** — acesso ao banco de dados via Spring Data JPA
- **Model** — entidades JPA mapeadas para as tabelas do banco
- **DTO** — objetos de entrada/saída da API, separados das entidades
- **Exception** — tratamento centralizado de erros, com respostas padronizadas

## ✨ Funcionalidades

- CRUD completo de **livros**, **autores**, **usuários** e **empréstimos**
- Ao criar um livro, o autor é resolvido pelo nome — se não existir, é cadastrado automaticamente
- Controle de disponibilidade: ao registrar um empréstimo, a quantidade de exemplares disponíveis é decrementada; ao registrar a devolução, é reposta
- Validação de regras de negócio (ex: não é possível emprestar um livro sem exemplares disponíveis)
- Validação de dados de entrada (campos obrigatórios, formato de e-mail)
- Tratamento de exceções centralizado, com respostas padronizadas (404 para recurso não encontrado, 400 para violação de regra de negócio)

## ⚙️ Como executar

### Pré-requisitos
- Java 17+
- Maven
- PostgreSQL instalado e em execução

### Passos

1. Clone o repositório:
```bash
git clone https://github.com/Luizhenrique1304/biblioteca-api.git
cd biblioteca-api
```

2. Crie o banco de dados no PostgreSQL:
```sql
CREATE DATABASE biblioteca;
```

3. Copie o arquivo de exemplo de configuração e preencha com suas próprias credenciais:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Edite o `application.properties` recém-criado com seu usuário e senha do PostgreSQL:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/biblioteca
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

> O `application.properties` está no `.gitignore` e não é versionado — cada pessoa configura o seu localmente com suas próprias credenciais.

4. Execute a aplicação:
```bash
mvn spring-boot:run
```

Como `ddl-auto=update` está configurado, o Hibernate cria as tabelas automaticamente na primeira execução — não é necessário criar `TB_LIVROS`, `TB_AUTORES`, `TB_USUARIOS` e `TB_EMPRESTIMOS` manualmente.

A API estará disponível em `http://localhost:8080`.

## 📌 Endpoints principais

### Autores — `/biblioteca/autores`
| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/biblioteca/autores` | Lista todos os autores |
| GET | `/biblioteca/autores/{id}` | Busca um autor por ID |
| POST | `/biblioteca/autores` | Cria um novo autor |
| PUT | `/biblioteca/autores/{id}` | Atualiza um autor |
| DELETE | `/biblioteca/autores/{id}` | Remove um autor |

### Livros — `/biblioteca/livros`
| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/biblioteca/livros` | Lista todos os livros |
| GET | `/biblioteca/livros/{id}` | Busca um livro por ID |
| POST | `/biblioteca/livros` | Cria um novo livro |
| PUT | `/biblioteca/livros/{id}` | Atualiza um livro |
| DELETE | `/biblioteca/livros/{id}` | Remove um livro |

**Exemplo de requisição (POST):**
```json
{
  "titulo": "Clean Code",
  "anoPublicacao": 2008,
  "quantidade": 5,
  "nomeAutor": "Robert C. Martin"
}
```

### Usuários — `/biblioteca/usuarios`
| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/biblioteca/usuarios` | Lista todos os usuários |
| GET | `/biblioteca/usuarios/{id}` | Busca um usuário por ID |
| POST | `/biblioteca/usuarios` | Cria um novo usuário |
| PUT | `/biblioteca/usuarios/{id}` | Atualiza um usuário |
| DELETE | `/biblioteca/usuarios/{id}` | Remove um usuário |

**Exemplo de requisição (POST):**
```json
{
  "nome": "Maria Silva",
  "email": "maria.silva@email.com"
}
```

### Empréstimos — `/biblioteca/emprestimos`
| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/biblioteca/emprestimos` | Lista todos os empréstimos |
| GET | `/biblioteca/emprestimos/{id}` | Busca um empréstimo por ID |
| POST | `/biblioteca/emprestimos` | Registra um novo empréstimo |
| PUT | `/biblioteca/emprestimos/{id}` | Atualiza um empréstimo (ex: registrar devolução) |
| DELETE | `/biblioteca/emprestimos/{id}` | Remove um empréstimo |

**Exemplo de requisição (POST) — criar empréstimo:**
```json
{
  "livroId": 1,
  "usuarioId": 1
}
```

**Exemplo de requisição (PUT) — registrar devolução:**
```json
{
  "livroId": 1,
  "usuarioId": 1,
  "dataEmprestimo": "2026-07-29",
  "dataDevolucao": "2026-08-05",
  "devolvido": true
}
```

## 🔒 Tratamento de erros

A API retorna respostas padronizadas para os principais cenários de erro:

**Recurso não encontrado (404):**
```json
{
  "status": 404,
  "erro": "Livro não encontrado com id: 99",
  "timestamp": "2026-07-30T20:48:00"
}
```

**Violação de regra de negócio (400):**
```json
{
  "status": 400,
  "erro": "Não há exemplares disponíveis para empréstimo.",
  "timestamp": "2026-07-30T20:48:00"
}
```

## 👤 Autor

Desenvolvido por Luiz Henrique de Oliveira, estudante de Ciência da Computação.

[LinkedIn](https://www.linkedin.com/in/luiz-henrique-brito-de-oliveira-8b0b01344)
