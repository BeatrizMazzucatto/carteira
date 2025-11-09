# Carteira
# 💼 Investment Portfolio Manager

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)
![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

**Sistema completo de gestão de múltiplas carteiras de investimentos**

[Funcionalidades](#-funcionalidades) • [Tecnologias](#-tecnologias) • [Instalação](#-instalação) • [Uso](#-como-usar) • [API](#-api-rest) • [Testes](#-testes)

</div>

---

## 📋 Sobre o Projeto

O **Investment Portfolio Manager** é um sistema completo desenvolvido em Java/Spring Boot para gerenciamento de carteiras de investimentos. Permite que investidores gerenciem múltiplas carteiras, registrem transações, acompanhem rentabilidade, calculem valores deflacionados e gerem relatórios detalhados.

### 🎯 Objetivo

Fornecer uma solução robusta e intuitiva para gestão pessoal de investimentos, com suporte a:
- Múltiplas carteiras por investidor
- Registro de transações (compras, vendas, proventos)
- Cálculo automático de rentabilidade
- Análise de inflação e valores deflacionados
- Relatórios consolidados
- Interface de console e API REST

---

## ✨ Funcionalidades

### 🏦 Gestão de Carteiras
- ✅ Criação e edição de múltiplas carteiras
- ✅ Definição de objetivos (Aposentadoria, Reserva de Emergência, etc.)
- ✅ Perfis de risco (Baixo, Moderado, Alto)
- ✅ Prazos de investimento (Curto, Médio, Longo Prazo)
- ✅ Histórico de alterações de valores

### 💰 Transações
- ✅ Registro de compras e vendas
- ✅ Registro de proventos (dividendos, JCP, rendimentos)
- ✅ Cálculo automático de preço médio
- ✅ Gestão de taxas e impostos
- ✅ Validação de valores mínimos

### 📊 Relatórios e Análises
- ✅ Rentabilidade por carteira e consolidada
- ✅ Análise de inflação e valores deflacionados
- ✅ Cálculo de ganho real e poder de compra
- ✅ Relatório de exibição em JSON (para front-end)
- ✅ Histórico completo de transações

### 📈 Cotações
- ✅ Integração com Google Sheets (via JSON)
- ✅ Atualização automática de preços
- ✅ Consulta de cotações em tempo real
- ✅ Suporte a múltiplos tipos de ativos (Ações, FIIs, ETFs, etc.)

### 🔐 Segurança
- ✅ Autenticação JWT
- ✅ Hash de senhas com BCrypt
- ✅ Recuperação de senha
- ✅ Validação de dados

---

## 🛠 Tecnologias

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot 3.2.0** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Segurança e autenticação
- **JWT (jjwt)** - Tokens de autenticação
- **Hibernate** - ORM
- **Maven** - Gerenciamento de dependências

### Banco de Dados
- **MariaDB/MySQL** - Banco de dados principal
- **H2 Database** - Banco em memória para desenvolvimento/testes

### Outras
- **Jackson** - Serialização JSON
- **Swagger/OpenAPI** - Documentação da API
- **JUnit 5** - Testes unitários e de integração
- **Mockito** - Mocks para testes

---

## 📦 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **Java 21** ou superior
  ```bash
  java -version
  ```

- **Maven 3.8+** (opcional, o projeto inclui Maven Wrapper)
  ```bash
  mvn -version
  ```

- **MariaDB/MySQL** (opcional, para produção)
  - Ou use H2 em memória para desenvolvimento

---

## 🚀 Instalação

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/investment-portfolio-manager.git
cd investment-portfolio-manager/carteira
```

### 2. Configure o banco de dados

Edite `src/main/resources/application.properties`:

```properties
# Para desenvolvimento (H2 em memória)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver

# Para produção (MariaDB)
spring.datasource.url=jdbc:mariadb://localhost:3306/investment_db
spring.datasource.username=root
spring.datasource.password=sua_senha
```

### 3. Compile o projeto

```bash
mvn clean install
```

Ou use o Maven Wrapper:

```bash
./mvnw clean install  # Linux/Mac
mvnw.cmd clean install # Windows
```

---

## 💻 Como Usar

### Interface de Console

A aplicação possui uma interface de console interativa e intuitiva.

#### Windows
```cmd
run-console.bat
```

#### Linux/Mac
```bash
./run-console.sh
```

#### Manual
```bash
mvn spring-boot:run
```

### Fluxo de Uso

1. **Login ou Cadastro**
   - Faça login com email e senha
   - Ou crie uma nova conta
   - Opção de recuperação de senha disponível

2. **Menu Principal**
   - Minhas Carteiras
   - Nova Carteira
   - Registrar Transação
   - Relatório de Rentabilidade Total
   - Consultar Ativos
   - Configurações
   - Relatório de Exibição (JSON)
   - Sair

3. **Gestão de Carteiras**
   - Crie carteiras com objetivos específicos
   - Defina perfil de risco e prazo
   - Acompanhe valor atual e rentabilidade

4. **Registro de Transações**
   - Registre compras e vendas
   - Adicione proventos e dividendos
   - Sistema calcula automaticamente preço médio

5. **Relatórios**
   - Visualize rentabilidade consolidada
   - Analise inflação e valores deflacionados
   - Exporte relatório em JSON

---

## 🌐 API REST

A aplicação também expõe uma API REST completa para integração.

### Iniciar API

#### Windows
```cmd
run-app.bat
```

#### Linux/Mac
```bash
./run-app.sh
```

A API estará disponível em: `http://localhost:8080`

### Documentação Swagger

Acesse a documentação interativa da API:

```
http://localhost:8080/swagger-ui.html
```

### Principais Endpoints

#### Investidores
```
POST   /api/investidores              # Criar investidor
GET    /api/investidores/{id}         # Buscar investidor
PUT    /api/investidores/{id}         # Atualizar investidor
POST   /api/auth/login                # Autenticar
```

#### Carteiras
```
GET    /api/carteiras/investidor/{id} # Listar carteiras
POST   /api/carteiras                 # Criar carteira
GET    /api/carteiras/{id}            # Buscar carteira
PUT    /api/carteiras/{id}            # Atualizar carteira
DELETE /api/carteiras/{id}            # Deletar carteira
```

#### Transações
```
POST   /api/transacoes/carteira/{id}  # Criar transação
GET    /api/transacoes/carteira/{id}  # Listar transações
GET    /api/transacoes/{id}           # Buscar transação
PUT    /api/transacoes/{id}           # Atualizar transação
DELETE /api/transacoes/{id}           # Deletar transação
```

#### Cotações
```
GET    /api/cotacoes                  # Listar todas as cotações
GET    /api/cotacoes/{codigo}         # Buscar cotação específica
```

#### Rentabilidade
```
GET    /api/rentabilidade/carteira/{id}        # Rentabilidade da carteira
GET    /api/rentabilidade/ativo/{id}           # Rentabilidade do ativo
GET    /api/rentabilidade/investidor/{id}      # Rentabilidade consolidada
```

#### Relatórios
```
GET    /api/relatorio/investidor/{id}  # Relatório completo em JSON
```

### Exemplo de Uso da API

#### Criar Investidor
```bash
curl -X POST http://localhost:8080/api/investidores \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@example.com",
    "senha": "senha123"
  }'
```

#### Criar Carteira
```bash
curl -X POST http://localhost:8080/api/carteiras \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Carteira Aposentadoria",
    "objetivo": "APOSENTADORIA",
    "prazo": "LONGO_PRAZO",
    "perfilRisco": "MODERADO_RISCO",
    "valorInicial": 10000.00
  }'
```

#### Registrar Compra
```bash
curl -X POST http://localhost:8080/api/transacoes/carteira/1 \
  -H "Content-Type: application/json" \
  -d '{
    "tipoTransacao": "COMPRA",
    "codigoAtivo": "PETR4",
    "nomeAtivo": "Petrobras PN",
    "tipoAtivo": "ACAO",
    "quantidade": 100,
    "precoUnitario": 25.50,
    "taxasCorretagem": 5.00
  }'
```

---

## 🧪 Testes

O projeto possui uma suíte completa de testes:

- ✅ **Testes Unitários** - Services e Utils
- ✅ **Testes de Integração** - Controllers e Repositories
- ✅ **Testes Funcionais** - Fluxos completos end-to-end

### Executar Testes

#### Todos os testes
```bash
./run-tests.sh        # Linux/Mac
run-tests.bat         # Windows
```

#### Por tipo
```bash
./run-tests.sh unit           # Apenas unitários
./run-tests.sh integration    # Apenas integração
./run-tests.sh functional     # Apenas funcionais
```

#### Com Maven
```bash
mvn test                      # Todos os testes
mvn test -Dtest=*Test         # Apenas unitários
mvn test -Dtest=*IntegrationTest  # Apenas integração
```

### Cobertura de Testes

Para gerar relatório de cobertura (requer plugin Jacoco):

```bash
mvn clean test jacoco:report
```

Relatório disponível em: `target/site/jacoco/index.html`

---

## 📁 Estrutura do Projeto

```
carteira/
├── src/
│   ├── main/
│   │   ├── java/com/invest/
│   │   │   ├── config/          # Configurações (WebSocket, CORS, etc.)
│   │   │   ├── console/         # Interface de console
│   │   │   ├── controller/      # Controllers REST
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── exception/       # Tratamento de exceções
│   │   │   ├── model/           # Entidades JPA
│   │   │   ├── repository/      # Repositories Spring Data
│   │   │   ├── service/         # Lógica de negócio
│   │   │   ├── util/            # Utilitários (JWT, etc.)
│   │   │   └── utils/           # Calculadoras e validadores
│   │   └── resources/
│   │       ├── application.properties    # Configurações principais
│   │       └── data/
│   │           └── cotacoes.json         # Cotações de ativos
│   └── test/
│       ├── java/com/invest/
│       │   ├── service/          # Testes unitários
│       │   ├── integration/      # Testes de integração
│       │   ├── functional/      # Testes funcionais
│       │   └── util/            # Testes de utilitários
│       └── resources/
│           └── application-test.properties
├── pom.xml                      # Configuração Maven
├── README.md                    # Este arquivo
├── run-app.sh/.bat              # Scripts de execução
├── run-console.sh/.bat          # Scripts console
└── run-tests.sh/.bat            # Scripts de testes
```

---

## 🔧 Configuração

### Perfis Disponíveis

O projeto suporta múltiplos perfis:

- **default** - MariaDB/MySQL (produção)
- **h2** - H2 em memória (desenvolvimento)
- **windows** - Configuração específica para Windows
- **test** - Configuração para testes

### Variáveis de Ambiente

Configure no `application.properties`:

```properties
# Banco de Dados
spring.datasource.url=jdbc:mariadb://localhost:3306/investment_db
spring.datasource.username=root
spring.datasource.password=sua_senha

# JWT
jwt.secret=sua_chave_secreta_aqui
jwt.expiration=86400000

# Google Sheets (opcional)
google.sheets.spreadsheet.id=seu_id_aqui
```

### H2 Console

Para desenvolvimento, acesse o console H2:

```
http://localhost:8080/h2-console
```

- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Usuário:** `sa`
- **Senha:** (vazio)

---

## 📚 Documentação Adicional

- [Guia do Console](GUIA_CONSOLE.md) - Guia completo da interface de console
- [Guia de Testes](src/test/README.md) - Documentação dos testes
- [Swagger UI](http://localhost:8080/swagger-ui.html) - Documentação interativa da API

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Siga estes passos:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

### Padrões de Código

- Siga as convenções Java
- Adicione testes para novas funcionalidades
- Documente código complexo
- Mantenha cobertura de testes acima de 70%

---

**Última atualização:** 2025

---

<div align="center">

**⭐ Se este projeto foi útil, considere dar uma estrela! ⭐**

</div>
