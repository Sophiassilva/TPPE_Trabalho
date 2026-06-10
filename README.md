# Trabalho Prático - Técnicas de Programação para Plataformas Emergentes

## Integrantes

| Nome                              | Matrícula |
|-----------------------------------|-----------|
| Antônio José Magalhães Leão Júnior| 190102683 |
| Gabriela Silva Alves   | 211030756 |
| Sophia Souza da Silva  | 231026886 |

## Sobre o projeto

Este trabalho implementa um sistema de **curadoria de dados de autorias científicas**, desenvolvido com a técnica de Test-Driven Development (TDD). O problema abordado é a deduplicação de registros de autores em repositórios científicos, onde um mesmo pesquisador pode aparecer com grafias diferentes dependendo da fonte de dados.

Foram implementados cinco casos de deduplicação:

- **Caso 1 — Diferenças tipográficas:** identifica nomes que diferem apenas por acentuação, cedilha ou uso de caracteres equivalentes (ex.: apóstrofo vs. crase).
- **Caso 2 — Sobrenome + iniciais:** reconhece a equivalência entre o nome completo e versões abreviadas com iniciais, com ou sem ponto.
- **Caso 3 — Partículas e abreviações opcionais:** trata a omissão de partículas como *de*, *da*, *do* e o uso opcional de ponto nas abreviações.
- **Caso 4 — Iniciais agrupadas + sobrenome:** identifica formas como "VC Junior" como equivalentes a "Vanilda Cristina Junior".
- **Caso 5 — IDs diferentes para o mesmo autor:** unifica registros duplicados mapeando todos para o menor ID do grupo.

## Executando a demonstração (Main)

A classe `Main` executa um exemplo completo com registros dos cinco casos de deduplicação e imprime no console os grupos de duplicatas encontrados.

### No Eclipse

1. No **Package Explorer**, expanda `src/br/unb/tppe` e abra `Main.java`.
2. Clique com o botão direito no arquivo e selecione **Run As > Java Application**.
3. A saída aparecerá na aba **Console**.

## Demonstração dos testes



## Linguagem e framework utilizados

A linguagem utilizada foi o Java, versão 21. O framework utilizado foi o JUnit, versão 5.10.0.

## Executar no Eclipse (recomendado)

### Pré-requisitos

- [Eclipse IDE for Java Developers](https://www.eclipse.org/downloads/) (versão 2023-03 ou superior)
- [JDK 21](https://adoptium.net/temurin/releases/?version=21) instalado e configurado no Eclipse

### Passo a passo

1. Abra o Eclipse e vá em **File > Import**.
2. Selecione **Maven > Existing Maven Projects** e clique em **Next**.
3. Em **Root Directory**, clique em **Browse** e selecione a pasta raiz do projeto.
4. Certifique-se de que o arquivo `pom.xml` aparece marcado na lista e clique em **Finish**.
5. Aguarde o Eclipse baixar as dependências automaticamente (acompanhe pela barra de progresso no rodapé).
6. No painel **Package Explorer**, clique com o botão direito na pasta `testes`.
7. Selecione **Run As > JUnit Test**.

Os resultados aparecerão na aba **JUnit**, com barra verde indicando que todos os testes passaram.

## Alternativa: executar pelo terminal com Maven

### Linux

Instale o Java e o Maven:

```
sudo apt install openjdk-21-jdk maven -y
```

Navegue até a raiz do projeto e execute:

```
mvn clean test
```

### Windows

Abra o PowerShell como Administrador e instale as dependências via Winget:

```
winget install EclipseAdoptium.Temurin.21.JDK
winget install Apache.Maven
```

Feche e reabra o terminal para recarregar as variáveis de ambiente. Em seguida, navegue até a raiz do projeto e execute:

```
mvn clean test
```
