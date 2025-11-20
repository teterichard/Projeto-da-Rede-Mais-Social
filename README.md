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

---

## 📋 Pré-requisitos

* Java **JDK 8+**
* MySQL **8.0+**
* Terminal/Prompt
* Navegador atualizado

---

## 🗄️ Configuração do Banco de Dados

1. Inicie o MySQL
2. Execute:

   ```bash
   mysql -u root -p < scriptBancoDados.txt
   ```
3. Verifique se as tabelas `candidato` e `afiliacao` foram criadas.

---

## ▶️ Executando o Sistema

No terminal, dentro da pasta do projeto:

```bash
bash run.sh
```

O servidor iniciará na porta **8080**.

Acesse:
**[http://localhost:8080](http://localhost:8080)**

---

## 🌐 Acesso ao Sistema

* `index.html` — Página inicial
* `tipo-afiliacao.html` — Escolha CPF/CNPJ
* `login.html` — Login para candidatos cadastrados

---

Claro! Aqui está **somente a seção “Estrutura Completa do Projeto (Código)”**, formatada em **Markdown limpo e organizado**:

---

## 📁 Estrutura Completa do Projeto (Código)

```
Projeto-da-Rede-Mais-Social/
│
├── src/
│   ├── WebServer.java                     # Servidor HTTP principal
│   ├── AfiliacaoController.java           # Controlador do processo de afiliação
│   ├── Candidato.java                     # Modelo de candidato
│   ├── Afiliacao.java                     # Modelo de afiliação
│   ├── CandidatoDAO.java                  # DAO para operações de candidato
│   ├── AfiliacaoDAO.java                  # DAO para operações de afiliação
│   ├── DatabaseConnection.java            # Gerenciador de conexão MySQL
│   ├── EmailService.java                  # Serviço de envio de e-mails
│   └── Validador.java                     # Validação de CPF, CNPJ e e-mail
│
├── bin/                                   # Arquivos .class compilados
│
├── web/
│   ├── index.html                         
│   ├── tipo-afiliacao.html                
│   ├── formulario-identificacao.html      
│   ├── formulario-cnpj.html               
│   ├── formulario-representante-cnpj.html 
│   ├── formulario-perfil.html             
│   ├── termo-compromisso.html             
│   ├── validacao-email.html               
│   ├── status-aguardando.html             
│   ├── login.html                         
│   └── styles.css                         
│
├── mysql-connector-j-8.0.33.jar           
├── scriptBancoDados.txt                   
├── run.sh                                 
└── README.md                              
```

---
                     
