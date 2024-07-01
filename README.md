# API Rest para Gerenciador de Tarefas (Todo List)

## Descrição
Esse é um projeto de uma API para gerenciador de tarefas. Armazena o cadastro de usuários e dados das tarefas, que são exibidas na página.

## Funcionalidades

- Cadastro de novos usuários
- Atualização dos dados dos usuários
- Autenticação Basic Auth para login de usuários
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
    mvn clean package
    ```
7. Execute o arquivo .jar:
    ```sh
    java -jar target/todolist-api-0.0.1-SNAPSHOT.jar

## Licença

Distribuído sob a licença MIT. Veja `LICENSE` para mais informações.
