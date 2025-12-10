# Microservices Spring Boot - Sistema de Gerenciamento de Consultas

Sistema distribuído com microserviços utilizando Spring Boot, Eureka Discovery e Spring Cloud Gateway.

## Arquitetura

- **Discovery Server (Eureka)**: Service Discovery na porta 8761
- **Gateway**: API Gateway na porta 8084
- **MySQL Database**: Banco de dados compartilhado na porta 3307
- **Patients Service**: Serviço de pacientes (acesso via gateway)
- **Doctors Service**: Serviço de médicos (acesso via gateway)
- **Appointment Service**: Serviço de agendamentos (via gateway)
- **Notification Service**: Serviço de e-mail (consumido pelo Appointment)

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
- `4-patients-service-gateway`
- `4-doctors-service-gateway`
- `5-appointment-service-gateway`
- `6-notification-service`

**Exemplo:**
```powershell
cd 3-service-discovery
./mvnw package spring-boot:repackage -DskipTests
cd ..

cd 4-service-gateway
./mvnw package spring-boot:repackage -DskipTests
cd ..

cd 4-patients-service-gateway
./mvnw package spring-boot:repackage -DskipTests
cd ..

cd 4-doctors-service-gateway
./mvnw package spring-boot:repackage -DskipTests
cd ..

cd 5-appointment-service-gateway
./mvnw package spring-boot:repackage -DskipTests
cd ..

cd 6-notification-service
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
- **Appointment Service (via Gateway)**: http://localhost:8084/appointments

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
├── 4-patients-service-gateway/   # Serviço de Pacientes (MySQL)
├── 4-doctors-service-gateway/    # Serviço de Médicos (MySQL)
├── 5-appointment-service-gateway/# Serviço de Agendamentos (MySQL + eventos)
├── 6-notification-service/       # Serviço de Notificações por e-mail
└── docker-compose.yml            # Orquestração dos containers + MySQL
```

## Segurança e Boas Práticas

- ✅ **Acesso via Gateway**: Todos os serviços são acessíveis apenas através do Gateway (porta 8084)
- ✅ **MySQL persistente**: Dados persistem entre reinicializações usando Docker volumes
- ⚠️ **Ambiente de desenvolvimento**: Credenciais hardcoded - não usar em produção
- ⚠️ **Banco compartilhado**: Patients e Doctors usam o mesmo banco (microservices compartilham tabelas por simplicidade)

## Testes rápidos (Postman Desktop ou curl)

Use o Gateway (`http://localhost:8084`). Exemplos:

### 1) Listar médicos
```bash
curl -X GET "http://localhost:8084/doctors"
```

### 2) Listar pacientes
```bash
curl -X GET "http://localhost:8084/patients"
```

### 3) Criar agendamento (gera e-mail)
```bash
curl -X POST "http://localhost:8084/appointments" \
	-H "Content-Type: application/json" \
	-d '{
				"patientId": 4,
				"doctorId": 1,
				"appointmentDate": "2026-01-20T14:17:00",
				"reason": "Check-up"
			}'
```

Resposta esperada: `201 Created` e e-mail enviado para o paciente cadastrado.

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
