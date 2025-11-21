# 🌐 Rede Mais Social — Documentação Unificada

O **Rede Mais Social** é um sistema web que gerencia o processo de afiliação de candidatos (Pessoa Física ou Jurídica) a iniciativas e ONGs, promovendo confiabilidade, validação por e-mail e controle de aprovação.
O sistema utiliza arquitetura **MVC**, integra **Java + MySQL** e possui interface web responsiva.

---

## 📌 Visão Geral do Processo

* O candidato solicita afiliação preenchendo seus dados.
* O sistema valida informações e envia um token por e-mail.
* A afiliação é analisada e pode ser aprovada ou rejeitada.
* Após aprovação, o candidato se torna voluntário e pode atuar em campanhas e ONGs compatíveis.

---

## 📚 Estrutura da Wiki (Links Oficiais)

Toda a documentação detalhada está organizada na Wiki:

* **Home**
  [https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki](https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki)

1. **Descrição do projeto e cenários**
   [https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki/1.-Descri%C3%A7%C3%A3o-do-projeto-e-cen%C3%A1rios](https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki/1.-Descri%C3%A7%C3%A3o-do-projeto-e-cen%C3%A1rios)

2. **Sequência de Telas – Cenário 1**
   [https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki/2.-Sequ%C3%AAncia-de-Telas--%E2%80%90-Cen%C3%A1rio-1](https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki/2.-Sequ%C3%AAncia-de-Telas--%E2%80%90-Cen%C3%A1rio-1)

3. **Sequência de Telas – Cenário 2**
   [https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki/3.-Sequ%C3%AAncia-de-Telas-%E2%80%90-Cen%C3%A1rio-2](https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki/3.-Sequ%C3%AAncia-de-Telas-%E2%80%90-Cen%C3%A1rio-2)

4. **Diagrama UML**
   [https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki/4.-Diagrama-UML](https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki/4.-Diagrama-UML)

5. **Diagrama de Classes de Sequência**
   [https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki/5.-Diagrama-de-Classes-de-Sequ%C3%AAncia](https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki/5.-Diagrama-de-Classes-de-Sequ%C3%AAncia)

6. **Modelo Entidade-Relacionamento (MER)**
   [https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki/6.-Modelo-Entidade-Relacionamento-(MER)](https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki/6.-Modelo-Entidade-Relacionamento-%28MER%29)

7. **Script Banco de Dados**
   [https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki/7.-Script-Banco-de-Dados](https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki/7.-Script-Banco-de-Dados)

8. **Implemen**
   [https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki/8.-Implementa%C3%A7%C3%A3o-do-projeto](https://github.com/teterichard/Projeto-da-Rede-Mais-Social/wiki/8.-Implementa%C3%A7%C3%A3o-do-projeto)

---
## Funcionalidades


### Para Candidatos
- Cadastro como Pessoa Física (CPF) ou Pessoa Jurídica (CNPJ)
- Formulário multi-etapas (identificação → perfil → termo)
- Validação de email com token único
- Consulta de status da solicitação
- Login para retornar ao cadastro


### Para o Sistema
- Validação de email, CPF e CNPJ
- Geração automática de tokens
- Envio de emails de confirmação (simulado)
- Armazenamento no MySQL

## Pré-requisitos

* Java **JDK 8+**
* MySQL **8.0+**
* Terminal/Prompt
* Navegador atualizado

---

## Comandos de Instalação e Execução

1. Inicie o MySQL
```
sudo service mysql start
  ```
2. Crie o banco de dados
   ```
   sudo mysql < scriptBancoDados.txt
   ```
3. Configura acesso sem senha
      ```
   sudo mysql -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY ''; FLUSH PRIVILEGES;"
         ```
5. Execute o sistema:

   ```bash
   bash run.sh
   ```
---

## Executando o Sistema

No terminal, dentro da pasta do projeto:

```bash
bash run.sh
```

O servidor iniciará na porta **8080**.

Acesse:
**[http://localhost:8080](http://localhost:8080)**

---

## Outro Comando Útil
Consultar banco de dados
sudo mysql -e "USE rede_mais_social; SELECT * FROM candidato;"
## Fluxo de Dados

1. **Frontend** → Envia JSON via `fetch()`
2. **WebServer** → Recebe e roteia para handler adequado
3. **Controller** → Valida dados e aplica regras de negócio
4. **DAO** → Executa operações no banco de dados
5. **MySQL** → Armazena/retorna dados
6. **Backend** → Retorna JSON com resultado
7. **Frontend** → Exibe resposta ao usuário
