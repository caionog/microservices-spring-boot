# Microservices Spring Boot - Sistema de Gerenciamento de Consultas

Sistema distribuído com microserviços utilizando Spring Boot, Eureka Discovery e Spring Cloud Gateway.

## Arquitetura

- **Discovery Server (Eureka)**: Service Discovery na porta 8761
- **Gateway**: API Gateway na porta 8084
- **Account Service**: Serviço de contas na porta 8081
- **Transaction Service**: Serviço de transações na porta 8082
- **Patients Service**: Serviço de pacientes na porta 8085

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

### 4. Parar os containers

```powershell
docker-compose down
```

## Estrutura do Projeto

```
├── 3-service-discovery/          # Eureka Discovery Server
├── 4-service-gateway/            # Spring Cloud Gateway
├── 4-account-service-gateway/    # Serviço de Contas
├── 4-transaction-service-gateway/# Serviço de Transações
├── 4-patients-service-gateway/   # Serviço de Pacientes
└── docker-compose.yml            # Orquestração dos containers
```

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
