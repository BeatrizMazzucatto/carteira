# Guia de Testes - Investment Portfolio Manager

Este documento descreve a estrutura de testes do projeto, incluindo testes unitários, de integração, funcionais e automatizados.

## 📋 Índice

- [Estrutura de Testes](#estrutura-de-testes)
- [Tipos de Testes](#tipos-de-testes)
- [Executando Testes](#executando-testes)
- [Cobertura de Testes](#cobertura-de-testes)
- [Boas Práticas](#boas-práticas)

## 📁 Estrutura de Testes

```
src/test/
├── java/
│   └── com/invest/
│       ├── service/              # Testes unitários de Services
│       │   ├── TransacaoServiceTest.java
│       │   ├── CarteiraServiceTest.java
│       │   └── InvestidorServiceTest.java
│       ├── util/                 # Testes unitários de Utils
│       │   └── JwtUtilTest.java
│       ├── integration/          # Testes de integração
│       │   ├── TransacaoControllerIntegrationTest.java
│       │   └── CarteiraRepositoryIntegrationTest.java
│       └── functional/           # Testes funcionais
│           └── FluxoCompletoTransacaoTest.java
└── resources/
    └── application-test.properties
```

## 🧪 Tipos de Testes

### 1. Testes Unitários

Testam componentes isolados (services, utils) usando mocks.

**Localização:** `src/test/java/com/invest/service/` e `src/test/java/com/invest/util/`

**Exemplos:**
- `TransacaoServiceTest` - Testa lógica de negócio de transações
- `CarteiraServiceTest` - Testa lógica de negócio de carteiras
- `InvestidorServiceTest` - Testa lógica de negócio de investidores
- `JwtUtilTest` - Testa utilidades de JWT

**Características:**
- Usam `@ExtendWith(MockitoExtension.class)`
- Mockam dependências com `@Mock`
- Testam métodos isoladamente
- Execução rápida

### 2. Testes de Integração

Testam a interação entre componentes (controllers, repositories, banco de dados).

**Localização:** `src/test/java/com/invest/integration/`

**Exemplos:**
- `TransacaoControllerIntegrationTest` - Testa endpoints REST de transações
- `CarteiraRepositoryIntegrationTest` - Testa operações de banco de dados

**Características:**
- Usam `@SpringBootTest` ou `@DataJpaTest`
- Usam banco H2 em memória
- Testam fluxos completos de API
- Execução mais lenta que testes unitários

### 3. Testes Funcionais

Testam fluxos completos de negócio do ponto de vista do usuário.

**Localização:** `src/test/java/com/invest/functional/`

**Exemplos:**
- `FluxoCompletoTransacaoTest` - Testa fluxos completos de transações

**Características:**
- Usam `@SpringBootTest`
- Testam cenários end-to-end
- Simulam uso real da aplicação
- Execução mais lenta

## 🚀 Executando Testes

### Executar Todos os Testes

```bash
# Linux/Mac
./run-tests.sh

# Windows
run-tests.bat
```

### Executar por Tipo

```bash
# Apenas testes unitários
./run-tests.sh unit

# Apenas testes de integração
./run-tests.sh integration

# Apenas testes funcionais
./run-tests.sh functional
```

### Executar com Maven

```bash
# Todos os testes
mvn clean test

# Teste específico
mvn test -Dtest=TransacaoServiceTest

# Testes por padrão
mvn test -Dtest="*Test"           # Apenas unitários
mvn test -Dtest="*IntegrationTest" # Apenas integração
```

### Executar no IDE

1. **IntelliJ IDEA:**
   - Clique com botão direito na classe de teste
   - Selecione "Run 'NomeDoTeste'"

2. **Eclipse:**
   - Clique com botão direito na classe de teste
   - Selecione "Run As" > "JUnit Test"

## 📊 Cobertura de Testes

### Adicionar Cobertura (Jacoco)

Adicione ao `pom.xml`:

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

Gerar relatório:
```bash
mvn clean test jacoco:report
```

Relatório disponível em: `target/site/jacoco/index.html`

## ✅ Boas Práticas

### 1. Nomenclatura de Testes

Use padrão: `deve[Comportamento]Quando[Condicao]`

```java
@Test
@DisplayName("Deve criar transação quando dados válidos")
void deveCriarTransacaoQuandoDadosValidos() {
    // ...
}
```

### 2. Estrutura AAA (Arrange-Act-Assert)

```java
@Test
void exemploTeste() {
    // Arrange - Preparar dados
    TransacaoRequest request = new TransacaoRequest();
    request.setTipoTransacao(TipoTransacao.COMPRA);
    
    // Act - Executar ação
    Transacao resultado = service.createTransacao(1L, request);
    
    // Assert - Verificar resultado
    assertNotNull(resultado);
    assertEquals(TipoTransacao.COMPRA, resultado.getTipoTransacao());
}
```

### 3. Isolamento

- Cada teste deve ser independente
- Use `@BeforeEach` para setup comum
- Limpe dados entre testes com `@Transactional`

### 4. Mocks

- Mock apenas dependências externas
- Não mocke a classe sendo testada
- Use `verify()` para verificar interações

### 5. Assertions

- Use assertions específicas
- Prefira `assertEquals` sobre `assertTrue`
- Use `@DisplayName` para descrições claras

## 🔧 Configuração

### application-test.properties

Configurações específicas para testes:
- Banco H2 em memória
- Logging reduzido
- Serviços externos desabilitados

## 📝 Adicionando Novos Testes

### Teste Unitário

```java
@ExtendWith(MockitoExtension.class)
class NovoServiceTest {
    @Mock
    private Repository repository;
    
    @InjectMocks
    private NovoService service;
    
    @Test
    void deveTestarComportamento() {
        // ...
    }
}
```

### Teste de Integração

```java
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class NovoControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void deveTestarEndpoint() {
        // ...
    }
}
```

## 🐛 Troubleshooting

### Testes falhando

1. Verifique se o banco H2 está configurado
2. Confirme que `application-test.properties` está correto
3. Verifique se todas as dependências estão mockadas

### Erros de compilação

1. Execute `mvn clean compile`
2. Verifique se todas as classes existem
3. Confirme versões de dependências

## 📚 Referências

- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)

