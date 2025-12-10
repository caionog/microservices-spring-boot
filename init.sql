-- Criar bancos de dados para cada serviço
CREATE DATABASE IF NOT EXISTS db_pacientes;
CREATE DATABASE IF NOT EXISTS db_medicos;
CREATE DATABASE IF NOT EXISTS db_agendamento;
CREATE DATABASE IF NOT EXISTS db_auth;

-- Garantir permissões do usuário root
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
FLUSH PRIVILEGES;
