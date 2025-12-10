# 🔧 Arquitetura do Sistema de Agendamento

## 📋 Banco de Dados

### MySQL (Microserviços)
- **Host:** localhost
- **Porta:** 3307
- **Usuário:** root
- **Senha:** root

**Bancos de dados (Schemas):**
- `db_pacientes` - Serviço de Pacientes
- `db_medicos` - Serviço de Médicos
- `db_agendamento` - Serviço de Agendamento
- `db_auth` - Autenticação (reservado)

### PostgreSQL (Keycloak)
- **Host:** localhost
- **Porta:** 5432
- **Usuário:** keycloak
- **Senha:** keycloak
- **Banco:** keycloak

---

## 🚀 Primeira Execução

### Passo 1: Subir os serviços
```bash
docker-compose up --build -d
```

### Passo 2: Aguardar tudo ficar pronto
- MySQL: ~5 segundos
- PostgreSQL: ~10 segundos
- Keycloak: ~40-60 segundos

### Passo 3: Verificar status
```bash
docker-compose ps
```

---

## 🔐 Configurar Keycloak Manualmente

A primeira configuração do Keycloak deve ser feita manualmente. **Isso é feito uma única vez!**

Após a primeira configuração, todos os dados são persistidos no PostgreSQL e nunca serão perdidos.

### Siga o guia: `KEYCLOAK_SETUP.md`

---

## 📊 Arquitetura de Microserviços

```
┌─────────────────────────────────────────┐
│         API Gateway (8084)              │
│    (Autenticação via Keycloak)          │
└────────────────┬────────────────────────┘
                 │
    ┌────────────┼────────────┐
    │            │            │
┌───▼──┐   ┌────▼───┐   ┌───▼──┐
│Paci- │   │ Médicos│   │Agenda│
│entes │   │        │   │mento │
│(8XXX)│   │(8XXX)  │   │(8XXX)│
└───┬──┘   └────┬───┘   └───┬──┘
    │           │           │
    └───────────┼───────────┘
                │
        ┌───────▼────────┐
        │ MySQL (3307)   │
        │                │
        │ db_pacientes   │
        │ db_medicos     │
        │ db_agendamento│
        │ db_auth        │
        └────────────────┘

┌──────────────────────┐
│ Keycloak (8081)      │
│ OAuth2/OpenID Connect│
└──────────────────────┘
        │
┌───────▼─────────────┐
│ PostgreSQL (5432)   │
│ keycloak DB         │
└─────────────────────┘
```

---

## 🔄 Fluxo de Autenticação

```
1. Cliente faz POST para login no Keycloak
   → Recebe access_token JWT

2. Cliente usa o token em requisições para o Gateway
   → Authorization: Bearer {token}

3. Gateway valida o token contra Keycloak
   → Se válido: proxia para o serviço
   → Se inválido: retorna 401

4. Serviço processa a requisição e retorna dados
```

---

## 🛠️ Servicemen Descobertos

- **Eureka Discovery (8761):** Service Registry
- **Gateway (8084):** API Gateway com OAuth2
- **Patients Service:** Via Gateway
- **Doctors Service:** Via Gateway
- **Keycloak (8081):** Identity Provider
- **MySQL (3307):** Banco de dados principal
- **PostgreSQL (5432):** Banco de dados Keycloak

---

## 📝 Variáveis de Configuração

### Pacientes Service
- **Database:** db_pacientes
- **Driver:** MySQL

### Médicos Service
- **Database:** db_medicos
- **Driver:** MySQL

### Agendamento Service
- **Database:** db_agendamento
- **Driver:** MySQL

### Keycloak
- **Database:** PostgreSQL
- **Realm:** consultas-realm
- **Client:** consultas-app
- **User:** recepcionista (senha123)
