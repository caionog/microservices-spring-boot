# Alteracoes em relacao ao modelo anterior (Monolitico/MVC)

## Visao geral
- Migracao para arquitetura de microsservicos com API Gateway e Service Discovery.
- Servicos atuais: patients, doctors, appointment, notification, gateway, discovery, keycloak.
- Atendimento/prontuario nao implementado; focamos em agendamento + notificacao por e-mail.

## Principais diferencas vs diagrama anterior
- **Separacao por servicos**: Antes monolitico/MVC com repositos centralizados; agora cada dominio em um servico proprio (pacientes, medicos, agendamentos, notificacoes).
- **Interacao**: Appointment usa Feign para validar paciente/medico e acionar notificacao.
- **Resiliencia**: CircuitBreaker no NotificationIntegrationService; fallback para nao quebrar fluxo de agendamento.
- **Eventos**: Appointment publica evento de dominio e listener aciona notificacao (event-driven light).
- **Seguranca**: Keycloak (OIDC/JWT) via Gateway em vez de AuthController/RepositorioUsuario interno.
- **Dados**: Diagrama sugeria bases separadas; implementacao atual usa MySQL compartilhado por limitacao de hardware (conceitualmente cada servico teria seu schema). Keycloak usa Postgres separado.
- **Funcionalidades ausentes em relacao ao diagrama antigo**:
  - Verificar disponibilidade de medico (HorarioService) ainda nao implementado.
  - Consultas/relatorios por medico no dia, filtros, status de notificacao detalhado: nao implementados.
  - Atendimento/Prontuario (diagnostico, prescricao) nao implementado.
  - Notificacao por WhatsApp/SMS (Twilio) nao implementada; apenas e-mail SMTP.
  - Front (LoginGUI, MenuPrincipalGUI, etc.) nao existe; uso via Postman/API.
- **Simplificacoes**: Controllers REST diretos; sem facades IRepositorio*; uso de Spring Data JPA direto.

## Motivos das alteracoes
- Atender requisitos de microservicos (Gateway + Discovery + 2+ servicos com persistencia).
- Reduzir escopo para entrega: concentrar em agendamento + notificacao por e-mail.
- Limite de hardware: compartilhamento de MySQL em vez de instancias/bases separadas.

## Trabalho futuro sugerido
- Implementar verificacao de disponibilidade de medico (HorarioService) antes do agendamento.
- Adicionar consultas/relatorios (por medico/dia, filtros) e status de notificacao na entidade consulta.
- Expandir notificacoes para SMS/WhatsApp (Twilio).
- Incluir atendimento/prontuario (diagnostico, prescricao) e prontuario business.
- Isolar schemas/bases por servico quando houver capacidade.
