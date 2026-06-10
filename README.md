# Trabalho Prático - Técnicas de Programação para Plataformas Emergentes

## Integrantes

| Nome                              | Matrícula |
|-----------------------------------|-----------|
| Antônio José Magalhães Leão Júnior| 190102683 |
| Gabriela Silva Alves   | 211030756 |
| Sophia Souza da Silva  | 231026886 |

## Linguagem e framework utilizados

A linguagem utilizada foi o Java, versão 21. O framework utilizado foi o JUnit. versão 5.10.0 .

## Executar no Linux

### Instale o Java (JDK 21)

```
sudo apt install openjdk-21-jdk -y
```

### Instale o Maven pelo terminal

```
sudo apt install maven -y 
```

### Rode os testes 

Navegue até a raiz do projeto e execute:

```
mvn clean test
```

## Executar no Windows

Uma forma de se fazer é utilizando o gerenciador de pacotes nativo chamado Winget. Clique no menu Iniciar, digite PowerShell ou Terminal, clique com o botão direito e selecione "Executar como Administrador".

### Instale o Java (JDK 21)

No terminal, execute o comando abaixo e pressione Y (Sim) se o Windows pedir confirmação:

```
winget install EclipseAdoptium.Temurin.21.JDK
```

### Instale o Maven

No mesmo terminal, execute:

```
winget install Apache.Maven
```

### Reinicie o Terminal

Feche a janela do PowerShell que você estava usando e abra uma nova, para que o Windows recarregue as configurações e reconheça os comandos java e mvn.

### Rode os testes

Navegue até a raiz do projeto e execute:

```
mvn clean test
```