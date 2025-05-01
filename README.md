# Hortapancs

O projeto **Hortapancs** tem como objetivo criar um sistema de cadastro de hortas comunitárias de PANCS (Plantas Alimentícias Não Convencionais), incentivando a produção e o consumo de alimentos saudáveis. O sistema permite que instituições e voluntários se cadastrem, e que os voluntários sejam automaticamente associados à instituição mais próxima com base no cálculo de distância entre os CEPs fornecidos.

https://github.com/chriscamargo204/hortapancs/tree/master/src/main/java

## Tecnologias Utilizadas

- Java 11
- Spring Boot
- Thymeleaf
- JPA 
- Bootstrap
- Maven
- H2 (Banco de Dados em memória) 

## Funcionalidades

- Cadastro de **instituições** e **voluntários**.
- Cálculo de **distância entre CEPs** para associar voluntários à instituição mais próxima.
- A lógica de **associação** entre voluntários e instituições é baseada no uso da API **ViaCEP** para verificar se o voluntário está dentro do raio de **10km** da instituição mais próxima. Caso contrário, o voluntário poderá escolher manualmente dentre as instituições cadastradas.
- Página inicial com informações sobre PANCS e botões de navegação.
- Integração com o banco de dados para persistir os dados dos voluntários e instituições.
- Interface web simples e intuitiva utilizando **Thymeleaf** e **Bootstrap**.

## Como Rodar o Projeto

### Pré-requisitos

- **Java 11** ou superior.
- **Maven** como gerenciador de dependências.
- Banco de dados  **H2** 

