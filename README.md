# 🚗 Sistema de Gerenciamento de Veículos

Este projeto é um sistema para gerenciar uma frota de veículos, permitindo operações CRUD para carros e motos.

## 📌 Tecnologias Utilizadas

### Backend:
- **Linguagem**: Java 17
- **Framework**: Spring Boot
- **Banco de Dados**: MySQL
- **Bibliotecas**:
  - **Spring JDBC** → Conexão e manipulação do banco de dados via queries nativas
  - **Spring Web** → Configuração de API REST
  - **JUnit** → Testes unitários
- **Ferramentas**:
  - **Maven** → Gerenciador de dependências
  - **Postman** → Testes de requisições HTTP
  - **MySQL Workbench** → Administração do banco de dados



### 💻 Frontend:
- **Linguagem**: JavaScript (ES6+)
- **Framework**: React.js
- **Bibliotecas**:
  - **React Router** → Navegação entre páginas
  - **Material-UI (MUI)** → Estilização e componentes visuais
  - **Axios** → Requisições HTTP para o backend
  - **React Hooks** → Gerenciamento de estado e efeitos colaterais
- **Ferramentas**:
  - **Git e GitHub** → Versionamento e controle do código


## 📂 Estrutura do Projeto

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/exemplo/veiculos/
│   │   │   ├── controller/
|   |   |       ├── VeiculoController.java
│   │   │   ├── dao/
|   |   |       ├── VeiculoImpl.java
│   │   │       ├── VeiculoRepository.java
│   │   │   ├── entity/
|   |   |        ├──Veiculo.java
│   │   │        ├── Carro.java
│   │   │        ├── Moto.java
frontend/
├── src/
│   ├── components/
│   │   ├── Header.js
│   │   ├── Footer.js
│   ├── pages/
│   │   ├── Home.js
│   │   ├── AddVeiculo.js
│   │   ├── EditVeiculo.js
│   ├── App.js
```

---

## 🛠️ Configuração do Banco de Dados

1. Instale o MySQL e crie um banco de dados:

```sql
CREATE DATABASE desafiotecnico;
USE desafiotecnico;
```

2. Crie a tabela `Veiculo`:

```sql
CREATE TABLE Veiculo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR(255) NOT NULL,
    fabricante VARCHAR(255) NOT NULL,
    ano INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    tipo ENUM('CARRO', 'MOTO') NOT NULL,
    quantidadePortas INT DEFAULT NULL,
    tipoCombustivel ENUM('gasolina', 'etanol', 'diesel', 'flex') DEFAULT NULL,
    cilindrada INT DEFAULT NULL,
    CONSTRAINT chk_carro CHECK (
        (tipo = 'CARRO' AND quantidadePortas IS NOT NULL AND tipoCombustivel IS NOT NULL AND cilindrada IS NULL) OR
        (tipo = 'MOTO' AND cilindrada IS NOT NULL AND quantidadePortas IS NULL AND tipoCombustivel IS NULL)
    )
);
```

3. Configure as credenciais do MySQL no `application.properties` do backend:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/desafiotecnico
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

```

---

## 🚀 Como Executar a Aplicação

### ✅ Requisitos
Antes de iniciar, garanta que seu ambiente possui os seguintes requisitos instalados:

- **Java 17+**
- **Maven** (caso esteja usando Java 17, ele já vem embutido)
- **MySQL** (ou outro banco compatível)
- **Node.js 18+** e **npm** (ou **yarn**)  
- **Postman** (opcional, para testar as APIs)


### Backend

1. Acesse a pasta do backend:
```sh
cd DesafioTecnicoProgramador
```
2. Compile e execute a aplicação:
```sh
mvn spring-boot:run
```
3. O backend estará disponível em: `http://localhost:8080`

### Frontend

1. Acesse a pasta do frontend:
```sh
cd frontend-veiculos
```
2. Instale as dependências:
```sh
npm install
```
3. Execute o projeto React:
```sh
npm start
```
4. O frontend estará disponível em: `http://localhost:3000`

---

## 🔍 Endpoints Disponíveis

- `GET /veiculos` - Lista todos os veículos
- `POST /veiculo` - Adiciona um novo veículo
- `GET /veiculo/{id}` - Obtém um veículo pelo ID
- `PUT /veiculo` - Atualiza um veículo pelo ID no Json
- `DELETE /veiculo/{id}` - Remove um veículo pelo ID
- `GET /carros` - Lista todos os carros
- `GET /motos` - Lista todos as motos
- `GET /fabricantes/{fabricante}` - Filtra por fabricante
- `GET /anos/{ano}` - Filtra por ano
- `GET /modelos/{modelo}` - Filtra por modelo

---
## Funcionalidades do Frontend 🚀

A interface gráfica foi desenvolvida em **React**, garantindo uma experiência fluida e responsiva. Abaixo estão as principais funcionalidades:

- **Modo Escuro e Claro** 🌙☀️  
  - O usuário pode alternar entre o modo escuro e claro para melhor experiência visual.

- **Filtros Avançados** 🔍  
  - O sistema permite filtrar veículos por:  
    - **Modelo**  
    - **Fabricante**  
    - **Ano de fabricação**  
    - **Tipo** (Carro ou Moto)  

- **Gerenciamento de Veículos** 🛠️  
  - **Adicionar Veículo**: Permite cadastrar um novo carro ou moto com os campos obrigatórios.  
  - **Editar Veículo**: Modifica os dados de um veículo já cadastrado.  
  - **Excluir Veículo**: Remove um veículo permanentemente do sistema.  

- **Navegação e Usabilidade** 🏠  
  - O **botão "Home"** no **Header** permite que o usuário volte rapidamente à página principal.  
  - Interface visualmente diferenciada para **Carros e Motos**:  
    - **Cores distintas** ajudam a identificar rapidamente cada categoria.  

Essa interface foi projetada para ser intuitiva e eficiente, atendendo a todos os requisitos do desafio. 🚗🏍️


---

## 📄 Licença
Este projeto é de uso livre para fins educacionais e profissionais.

