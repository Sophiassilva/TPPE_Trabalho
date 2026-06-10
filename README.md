# Trabalho Prático - Técnicas de Programação para Plataformas Emergentes

## Integrantes

| Nome                              | Matrícula |
|-----------------------------------|-----------|
| Antônio José Magalhães Leão Júnior| 190102683 |
| Gabriela Silva Alves   | 211030756 |
| Sophia Souza da Silva  | 231026886 |

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
