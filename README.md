# Microservices Spring Boot - Sistema de Gerenciamento de Consultas

Sistema distribuído com microserviços utilizando Spring Boot, Eureka Discovery e Spring Cloud Gateway.

## Arquitetura

- **Discovery Server (Eureka)**: Service Discovery na porta 8761
- **Gateway**: API Gateway na porta 8084
- **MySQL Database**: Banco de dados compartilhado na porta 3307
- **Account Service**: Serviço de contas (acesso via gateway)
- **Transaction Service**: Serviço de transações (acesso via gateway)
- **Patients Service**: Serviço de pacientes (acesso via gateway)
- **Doctors Service**: Serviço de médicos (acesso via gateway)

## Pré-requisitos

- Java 11 ou superior
- Maven 3.6+
- Docker Desktop
- Variável de ambiente `JAVA_HOME` configurada

## Como executar

### 1. Compilar os serviços

Execute o comando abaixo em cada pasta de serviço que será utilizado:

```powershell
./mvnw package spring-boot:repackage -DskipTests
```

Serviços a compilar:
- `3-service-discovery`
- `4-service-gateway`
- `4-account-service-gateway`
- `4-transaction-service-gateway`
- `4-patients-service-gateway`
- `4-doctors-service-gateway`

**Exemplo:**
```powershell
cd 3-service-discovery
./mvnw package spring-boot:repackage -DskipTests
cd ..

cd 4-service-gateway
./mvnw package spring-boot:repackage -DskipTests
cd ..

cd 4-account-service-gateway
./mvnw package spring-boot:repackage -DskipTests
cd ..

cd 4-transaction-service-gateway
./mvnw package spring-boot:repackage -DskipTests
cd ..

cd 4-patients-service-gateway
./mvnw package spring-boot:repackage -DskipTests
cd ..

cd 4-doctors-service-gateway
./mvnw package spring-boot:repackage -DskipTests
cd ..
```

### 2. Subir os containers com Docker

**Certifique-se que o Docker Desktop está aberto e rodando!**

Na raiz do projeto, execute:

```powershell
docker-compose up --build -d
```

### 3. Verificar os serviços

- **Eureka Dashboard**: http://localhost:8761
- **Gateway**: http://localhost:8084
- **Patients Service (via Gateway)**: http://localhost:8084/patients
- **Doctors Service (via Gateway)**: http://localhost:8084/doctors

**Nota**: Os serviços individuais não são acessíveis diretamente - todo o tráfego passa pelo Gateway (arquitetura de microserviços).

### 4. Acessar o banco de dados MySQL

Use qualquer cliente MySQL (DBeaver, MySQL Workbench, etc.):
- **Host**: localhost
- **Port**: 3307
- **Database**: microservices
- **Username**: root
- **Password**: root

### 5. Parar os containers

```powershell
docker-compose down
```

**Para remover também os dados do MySQL:**
```powershell
docker-compose down -v
```

## Estrutura do Projeto

```
├── 3-service-discovery/          # Eureka Discovery Server
├── 4-service-gateway/            # Spring Cloud Gateway
├── 4-account-service-gateway/    # Serviço de Contas
├── 4-transaction-service-gateway/# Serviço de Transações
├── 4-patients-service-gateway/   # Serviço de Pacientes (MySQL)
├── 4-doctors-service-gateway/    # Serviço de Médicos (MySQL)
└── docker-compose.yml            # Orquestração dos containers + MySQL
```

## Segurança e Boas Práticas

- ✅ **Acesso via Gateway**: Todos os serviços são acessíveis apenas através do Gateway (porta 8084)
- ✅ **MySQL persistente**: Dados persistem entre reinicializações usando Docker volumes
- ⚠️ **Ambiente de desenvolvimento**: Credenciais hardcoded - não usar em produção
- ⚠️ **Banco compartilhado**: Patients e Doctors usam o mesmo banco (microservices compartilham tabelas por simplicidade)

## Troubleshooting

### Erro: JAVA_HOME not found

Configure a variável de ambiente `JAVA_HOME`:
```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-19"
```

### Erro: Docker space

Limpe o cache do Docker:
```powershell
docker builder prune -f
docker volume prune -f
docker image prune -a -f
```

### Verificar espaço usado pelo Docker

```powershell
docker system df
```
