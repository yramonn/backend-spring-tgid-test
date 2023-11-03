# backend-spring-tgid-test
API de um CRUD de Empresa e Usuario com transações com envio de email utilizando o RabbitMQ

# Acessar Swagger UI
http://localhost:8080/swagger-ui.html

# Tecnologias utilizadas

- Java Versão 11
- Spring Boot
- JPA / Hibernate
- JUnit
- Maven
- RabbitMQ
- MySQL

- # Como executar o projeto
- git clone
- Execute: mvn clean package install
- application.properties - Aplicação foi desenvolvida utilizando o MySQL. Crie um schema que vai receber a criação das tabelas quando á aplicação for inicializada. Configuere o spring.datasource.url, spring.datasource.username, spring.datasource.password com suas devidas Credenciais do seu MySQL. Todas tabelas serão criadas através do: spring.jpa.hibernate.ddl-auto=update.

# Autor

Ramon Silva
https://www.linkedin.com/in/ramon--silva/
