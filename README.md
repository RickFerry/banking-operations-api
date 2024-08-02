```markdown
# Banking API

## Descrição

A Banking API é uma API RESTful para gerenciar contas bancárias, permitindo operações de saque, depósito, transferência e consulta de saldo. A API é implementada em Java com Spring Boot e não utiliza uma camada de persistência, armazenando os dados em memória para simplicidade.

## Funcionalidades

- **Saque**: Permite retirar uma quantia de uma conta existente.
- **Depósito**: Permite adicionar uma quantia a uma conta existente.
- **Transferência**: Permite transferir uma quantia de uma conta para outra.
- **Consulta de Saldo**: Permite consultar o saldo de uma conta específica.

## Endpoints

### 1. Saque

- **Método**: `POST`
- **URL**: `/event`
- **Descrição**: Realiza um saque de uma conta.
- **Parâmetros**: 
  - `type` (string): Tipo de operação (deve ser "withdraw").
  - `origin` (string): ID da conta de origem.
  - `amount` (integer): Valor a ser sacado.

- **Exemplo de Requisição**:
    ```json
    {
      "type": "withdraw",
      "origin": "100",
      "amount": 5
    }
    ```

- **Resposta**:
  - **Status**: `201 Created` se bem-sucedido.
  - **Corpo da Resposta**:
    ```json
    {
      "origin": {
        "id": "100",
        "balance": 95
      }
    }
    ```
  - **Status**: `404 Not Found` se a conta não existir ou se não houver saldo suficiente.
  - **Corpo da Resposta**:
    ```json
    {
      "error": "Origin account not found or insufficient funds"
    }
    ```

### 2. Depósito

- **Método**: `POST`
- **URL**: `/event`
- **Descrição**: Realiza um depósito em uma conta.
- **Parâmetros**:
  - `type` (string): Tipo de operação (deve ser "deposit").
  - `origin` (string): ID da conta de destino.
  - `amount` (integer): Valor a ser depositado.

- **Exemplo de Requisição**:
    ```json
    {
      "type": "deposit",
      "origin": "100",
      "amount": 50
    }
    ```

- **Resposta**:
  - **Status**: `201 Created` se bem-sucedido.
  - **Corpo da Resposta**:
    ```json
    {
      "origin": {
        "id": "100",
        "balance": 150
      }
    }
    ```
  - **Status**: `404 Not Found` se a conta não existir.

  - **Corpo da Resposta**:
    ```json
    {
      "error": "Origin account not found"
    }
    ```

### 3. Transferência

- **Método**: `POST`
- **URL**: `/event`
- **Descrição**: Realiza uma transferência entre duas contas.
- **Parâmetros**:
  - `type` (string): Tipo de operação (deve ser "transfer").
  - `origin` (string): ID da conta de origem.
  - `destination` (string): ID da conta de destino.
  - `amount` (integer): Valor a ser transferido.

- **Exemplo de Requisição**:
    ```json
    {
      "type": "transfer",
      "origin": "100",
      "destination": "300",
      "amount": 15
    }
    ```

- **Resposta**:
  - **Status**: `201 Created` se bem-sucedido.
  - **Corpo da Resposta**:
    ```json
    {
      "origin": {
        "id": "100",
        "balance": 85
      },
      "destination": {
        "id": "300",
        "balance": 315
      }
    }
    ```
  - **Status**: `404 Not Found` se a conta de origem não existir ou se houver falha na transferência.

  - **Corpo da Resposta**:
    ```json
    {
      "error": "Transfer failed"
    }
    ```

### 4. Consulta de Saldo

- **Método**: `GET`
- **URL**: `/balance`
- **Descrição**: Consulta o saldo de uma conta.
- **Parâmetros**:
  - `account_id` (string): ID da conta.

- **Exemplo de Requisição**:
    ```http
    GET /balance?account_id=100
    ```

- **Resposta**:
  - **Status**: `200 OK` se bem-sucedido.
  - **Corpo da Resposta**:
    ```
    150
    ```
  - **Status**: `404 Not Found` se a conta não existir.

  - **Corpo da Resposta**:
    ```json
    {
      "error": "Account not found"
    }
    ```

## Executando a Aplicação

1. **Clone o Repositório**:
    ```bash
    git clone https://github.com/seu-usuario/seu-repositorio.git
    cd seu-repositorio
    ```

2. **Compile e Execute**:
    ```bash
    ./mvnw spring-boot:run
    ```

3. **Acesse a API**:
    A API estará disponível em `http://localhost:8080`.

## Testes

Para executar os testes automatizados, utilize o seguinte comando:

```bash
./mvnw test
```

## Contribuindo

Se desejar contribuir com o projeto, por favor, faça um fork do repositório e envie um pull request com suas alterações.

## Licença

Este projeto está licenciado sob a Licença MIT - consulte o arquivo [LICENSE](LICENSE) para mais detalhes.

---

Se precisar de mais assistência ou tiver dúvidas, sinta-se à vontade para abrir uma issue no repositório ou entrar em contato!

```