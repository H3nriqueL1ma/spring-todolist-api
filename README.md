# API Rest para Gerenciador de Tarefas (Todo List)

## Descrição
Esse é um projeto de uma API para gerenciador de tarefas. Armazena o cadastro de usuários e dados das tarefas, que são exibidas na página.

## Funcionalidades

- Cadastro de novos usuários
- Autenticação Basic Auth para login de usuários
- Validação de emails e domínios
- Validação de links
- Troca de senha para usuários que esqueceram a senha
- Criação de novas tarefas
- Exclusão das tarefas
- Marcar tarefas como completas ou pendentes
- Listar todas as tarefas de um usuário
- Listar as tarefas completas de um usuário
- Listar as tarefas pendentes de um usuário

## Tecnologias

- Spring Framework
- Java 17
- Maven
- PostgreSQL
- Docker e Docker Compose

## Documentação da API

### Usuário

#### Registro do Usuário

```http
POST /user
```

##### Request:

| Body            | Tipo     | Descrição                                    |
| :-------------- | :------- | :------------------------------------------- |
| `username`      | `string` | **Obrigatório**. Nome do Usuário             |
| `email`         | `string` | **Obrigatório**. Email do Usuário            |
| `password`      | `string` | **Obrigatório**. Senha do Usuário            |

##### Response:

- Response 201

#

#### Login do Usuário

```http
POST /user/login
```

##### Request:

| Body            | Tipo     | Descrição                                    |
| :-------------- | :------- | :------------------------------------------- |
| `username`      | `string` | **Obrigatório**. Nome do Usuário             |
| `password`      | `string` | **Obrigatório**. Senha do Usuário            |

OBS: Login com request de Basic Auth (Authorization: Basic {credenciais criptografadas})

##### Response:

- Response 200

#

#### Verificação do Email do Usuário

```http
POST /user/email-verify/{email}
```

**`email` Email do Usuário a ser verificado**

##### Response:

- Response 200 - caso seja encontrado o email
- Response 404 - caso o email não seja encontrado

#

#### Atualizar Senha do Usuário

```http
POST /user/reset-pass
```

##### Request:

| Body            | Tipo     | Descrição                                    |
| :-------------- | :------- | :------------------------------------------- |
| `newPass`       | `string` | **Obrigatório**. Nova Senha do Usuário       |
| `email`         | `string` | **Obrigatório**. Email do Usuário            |

##### Response:

- Response 200 - caso seja encontrado o email
- Response 404 - caso o email não seja encontrado

#

### Tasks

#### Criar Task

```http
POST /user/task
```

##### Request:

| Body            | Tipo     | Descrição                                    |
| :-------------- | :------- | :------------------------------------------- |
| `username`      | `string` | **Obrigatório**. Nome do Usuário             |
| `taskUser`      | `string` | **Obrigatório**. Conteúdo da Task            |

#

#### Ler Tasks do Usuário

```http
GET /user/task/read-user-tasks?reqParam
```

##### Filtros de buscas

| queryParams    |   Tipo   |                   Descrição |
| :------------- | :------: | --------------------------: |
| `username`     | `string` | Nome do Usuário             |

##### Response:

- Response 200 (application/json)

  - Body
      
          [
             {
                "taskId": 1,
                "user": {
                      "id": 1,
                      "username": "Usuário",
                      "email": "email@email.com",
                      "password": "$2a$10$or.07FJjcp/zHJ1OoU/aIePgzWsY9okdOjkmYrFO8ra0nA0Z8Ol0i",
                      "tasks": []
                },
                "taskContent": "Fazer compras",
                "completed": false
             },
             {
                "taskId": 1,
                "user": {
                      "id": 1,
                      "username": "Usuário",
                      "email": "email@email.com",
                      "password": "$2a$10$or.07FJjcp/zHJ1OoU/aIePgzWsY9okdOjkmYrFO8ra0nA0Z8Ol0i",
                      "tasks": []
                },
                "taskContent": "Lavar o carro",
                "completed": true
             },
          ]

#

#### Deletar Task

```http
DELETE /user/task/delete-user-task/{task_id}
```

**`task_id` ID da Task a ser deletada**

#

#### Mudar Status da Task

```http
POST /user/task/read-user-tasks/status
```

##### Request:

| Body            | Tipo     | Descrição                                    |
| :-------------- | :------- | :------------------------------------------- |
| `taskID`        | `int`    | **Obrigatório**. ID da Task                  |
| `status`        | `boolean`| **Obrigatório**. Novo Status da Task         |

##### Response:

- Response 201

#

#### Ler Tasks com Status Ativas do Usuário

```http
GET /user/task/read-user-tasks/active?reqParam
```

##### Filtros de buscas

| queryParams    |   Tipo   |                   Descrição |
| :------------- | :------: | --------------------------: |
| `username`     | `string` | Nome do Usuário             |

##### Response:

- Response 200 (application/json)

  - Body
      
          [
             {
                "taskId": 1,
                "user": {
                      "id": 1,
                      "username": "Usuário",
                      "email": "email@email.com",
                      "password": "$2a$10$or.07FJjcp/zHJ1OoU/aIePgzWsY9okdOjkmYrFO8ra0nA0Z8Ol0i",
                      "tasks": []
                },
                "taskContent": "Beber água",
                "completed": false
             },
             {
                "taskId": 1,
                "user": {
                      "id": 1,
                      "username": "Usuário",
                      "email": "email@email.com",
                      "password": "$2a$10$or.07FJjcp/zHJ1OoU/aIePgzWsY9okdOjkmYrFO8ra0nA0Z8Ol0i",
                      "tasks": []
                },
                "taskContent": "Tomar banho",
                "completed": false
             },
          ]

#

#### Ler Tasks com Status Completadas do Usuário

```http
GET /user/task/read-user-tasks/complete?reqParam
```

##### Filtros de buscas

| queryParams    |   Tipo   |                   Descrição |
| :------------- | :------: | --------------------------: |
| `username`     | `string` | Nome do Usuário             |

##### Response:

- Response 200 (application/json)

  - Body
      
          [
             {
                "taskId": 1,
                "user": {
                      "id": 1,
                      "username": "Usuário",
                      "email": "email@email.com",
                      "password": "$2a$10$or.07FJjcp/zHJ1OoU/aIePgzWsY9okdOjkmYrFO8ra0nA0Z8Ol0i",
                      "tasks": []
                },
                "taskContent": "Jogar videogame",
                "completed": true
             },
             {
                "taskId": 1,
                "user": {
                      "id": 1,
                      "username": "Usuário",
                      "email": "email@email.com",
                      "password": "$2a$10$or.07FJjcp/zHJ1OoU/aIePgzWsY9okdOjkmYrFO8ra0nA0Z8Ol0i",
                      "tasks": []
                },
                "taskContent": "Estudar matemática",
                "completed": true
             },
          ]

#

## Instalação (Recomendo o uso da IDE Intellij IDEA para execução do projeto. Já que a instalação pelo VS Code é um tanto complexa. Além de executar a instalação em um ambiente Ubuntu ou usar o WSL)

1. Clone o repositório:
   ```sh
   git clone https://github.com/H3nriqueL1ma/spring-todolist-api.git
    ```
2. Configure um banco de dados PostgreSQL, ou qualquer banco de sua escolha, se atentando a mudar a dependência de Driver Postgres para um Driver do banco escolhido.
3. Atualize o arquivo 'application.properties' com as configurações do seu banco de dados.
4. Instale as dependências:
    ```sh
    sudo apt install openjdk-17-jdk
    ```
    ```sh
    sudo apt install maven
    ```
5. Monte o pacote:
    ```sh
    mvn clean package -DskipTests
    ```
7. Execute o arquivo .jar:
    ```sh
    java -jar target/todolist-api-0.0.1-SNAPSHOT.jar
    ```
    
## Licença

Distribuído sob a licença MIT. Veja `LICENSE` para mais informações.
