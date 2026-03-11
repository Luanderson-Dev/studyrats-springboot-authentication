# StudyRats API 🐭📚
![Java](https://img.shields.io/badge/java-25-orange)
![Spring](https://img.shields.io/badge/springboot-4.x-green)

API REST para o projeto StudyRats com autenticação e segurança

Implementação feita para demonstrar o fluxo de autenticação de usuário.

# 🚀 Tecnologias utilizadas

* Java 25
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* Maven
* Swagger / OpenAPI
* PostgreSQL (ou H2 para desenvolvimento)

---

# 📦 Arquitetura

A aplicação segue uma arquitetura em camadas:

Controller → Service → Repository → Database

```
Controller
   ↓
Service
   ↓
Repository
   ↓
Database
```

### Camadas

**Controller**
Responsável por expor os endpoints REST.

**Service**
Contém a lógica de negócio da aplicação.

**Repository**
Responsável pela comunicação com o banco de dados utilizando Spring Data JPA.

**Config**
Configurações de segurança, JWT e documentação.

---

# 🔐 Autenticação

A API utiliza autenticação baseada em **JWT** com **Access Token** e **Refresh Token**.

Fluxo de autenticação:

```
Register
   ↓
Login
   ↓
Access Token
   ↓
Refresh Token
   ↓
Logout
```

---

# ⚙️ Como rodar o projeto

### 1 - Clonar o repositório

```
git clone https://github.com/seu-usuario/studyrats-api.git
```

### 2 - Entrar na pasta do projeto

```
cd studyrats-api
```

### 3 - Rodar a aplicação

```
mvn spring-boot:run
```

A API iniciará em:

```
http://localhost:8080
```

---

# 📚 Documentação da API

A documentação interativa está disponível em:

```
http://localhost:8080/swagger-ui/index.html
```

---

# 🔑 Configuração

Arquivo:

```
application.properties
```

Exemplo:

```
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5432/studyrats
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update

jwt.secret=studyrats-super-secret-key-ultra-secure-2026
jwt.expiration=86400000
```

---

# 📡 Principais endpoints

## Autenticação

### Registrar usuário

```
POST /auth/register
```

### Login

```
POST /auth/login
```

### Refresh Token

```
POST /auth/refresh
```

### Logout

```
POST /auth/logout
```

---

# 👤 Usuário

### Obter usuário logado

```
GET /users/me
```

Requer token JWT no header:

```
Authorization: Bearer TOKEN
```

---

# 🧪 Testando a API

Você pode testar a API utilizando:

* Insomnia
* Postman
* Swagger UI

Fluxo recomendado de teste:

```
1 - Register
2 - Login
3 - Copiar access token
4 - Testar endpoint protegido
5 - Testar refresh token
6 - Logout
```

---

# 📂 Estrutura do projeto

```
studyrats
│
├── config
│   ├── SecurityConfig.java
│   ├── JwtService.java
│   └── JwtFilter.java
│
├── security
│   ├── PasswordService.java
│
├── service
│   ├── AuthService.java
│   └── RefreshTokenService.java
│
├── controller
│   └── AuthController.java
│
├── repository
│   ├── UserRepository.java
│   └── RefreshTokenRepository.java
│
├── entity
│   ├── User.java
│   └── RefreshToken.java
│
└── dto
|   ├── request
|   |  ├── LoginRequest.java
|   |  ├── RegisterRequest.java
|   |  ├── RefreshRequest.java
|   ├── response
|      └── AuthResponse.java
│
└── StudyratsApplication
```

---
# 👨‍💻 Autor

Luanderson

Engenheiro / Desenvolvedor Backend
