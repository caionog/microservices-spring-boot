# 🔐 Guia de Configuração do Keycloak para Sistema de Agendamento

## Passo 1: Acesse o Keycloak Admin Console

1. Abra seu navegador e acesse: **http://localhost:8081**
2. Clique em "Administration Console"
3. Faça login com as credenciais padrão:
   - **Username:** `admin`
   - **Password:** `admin`

---

## Passo 2: Criar um Realm

1. No canto superior esquerdo, veja "Master" (o realm padrão)
2. Clique em "Master" → "Create Realm"
3. Preencha os dados:
   - **Realm name:** `consultas-realm`
   - Deixe as outras opções padrão
4. Clique em "Create"

---

## Passo 3: Criar um Client

### 3.1 - General Settings
1. No menu esquerdo, vá em **Clients**
2. Clique em "Create Client"
3. Preencha:
   - **Client type:** `OpenID Connect`
   - **Client ID:** `consultas-app`
   - **Name:** `Consultas App`
   - **Description:** (deixe em branco)
   - **Always display in UI:** `Off`
4. Clique "Next"

### 3.2 - Capability config
5. Na próxima tela, configure:
   - **Client authentication:** `On`
   - **Authorization:** `Off`
   - **Authentication flow:**
     - ✅ Standard flow
     - ✅ Direct access grants
     - ❌ Implicit flow
     - ❌ Service accounts roles
     - ❌ OAuth 2.0 Device Authorization Grant
     - ❌ OIDC CIBA Grant
6. Clique "Save"

### 3.3 - Login Settings
7. Vá na aba **Login Settings** e preencha:
   - **Root URL:** (deixe em branco)
   - **Home URL:** (deixe em branco)
   - **Valid redirect URIs:** `http://localhost:8084/*`
   - **Valid post logout redirect URIs:** (deixe em branco)
   - **Web origins:** `http://localhost:8084`
8. Clique "Save"

### 3.4 - Obter Client Secret (para depois)
9. Na aba **Credentials**, copie o **Client Secret** (usaremos se necessário)

---

## Passo 4: Criar um User de Teste

### 4.1 - Informações Básicas
1. No menu esquerdo, vá em **Users**
2. Clique em "Add User"

### 4.2 - Seção General
3. Preencha os campos:
   - **Username:** `recepcionista` (obrigatório - marcado com *)
   - **Email:** `recepcionista@example.com`
   - **First name:** `Recepcionista`
   - **Last name:** `Teste`

### 4.3 - Configurações de Ação
4. Na seção **Required user actions**:
   - **Select action:** (deixe em branco - sem ações requeridas)
   
5. **Email verified:** `On` (marque para indicar que o email já foi verificado)

### 4.4 - Groups (Grupos)
6. Na seção **Groups**:
   - (deixe em branco - não adicione a nenhum grupo por enquanto)

7. Clique em **Create**

### 4.5 - Definir Senha
8. Após criar, você será redirecionado para os detalhes do user
9. Vá na aba **Credentials**
10. Clique em "Set password"
11. Defina a senha:
    - **Password:** `senha123`
    - **Confirm password:** `senha123`
    - **Temporary:** `Off` (para não forçar mudança no login)
12. Clique em "Set Password"

---

## Passo 5: (Opcional) Criar Roles

1. No menu esquerdo, vá em **Roles**
2. Clique em "Create Role"
3. Defina:
   - **Role name:** `recepcionista`
   - **Description:** `Role para recepcionistas`
4. Clique "Save"
5. Volte em **Users** → `recepcionista`
6. Vá na aba **Role mapping**
7. Clique "Assign role"
8. Selecione a role `recepcionista`
9. Clique "Assign"