# Sulwork Café ☕️

Sistema de gerenciamento de colaboradores e itens de café da manhã.

## 🧰 Tecnologias

- Backend: Java 17 + Spring Boot
- Frontend: Angular 17
- Banco de Dados: PostgreSQL
- Docker & Docker Compose
- Documentação: Swagger

## 🚀 Como rodar localmente com Docker

### Pré-requisitos

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

### Passos

1. Clone o repositório:
```bash
git clone https://github.com/adriano/sulwork-cafe.git
cd sulwork-cafe
cd backend
mvn clean package
cd ..
docker-compose up --build

Acesse:

Frontend: http://localhost:4200

Backend (Swagger): http://localhost:8080/swagger-ui.html

📖 Documentação da API

http://localhost:8080/swagger-ui.html

☁️ Deploy na Nuvem (opcional)
A aplicação foi publicada em:
🔗 Frontend: https://sulwork-cafe-front.railway.app
🔗 Backend: https://sulwork-cafe-back.railway.app/api

Railway ou Render pode ser usado para subir o frontend e backend.

🗃️ Estrutura Docker
O projeto usa docker-compose.yml para orquestrar os seguintes serviços:

db: PostgreSQL 15

backend: aplicação Spring Boot (porta 8080)

frontend: aplicação Angular (porta 4200)


---

### 📦 3. **Swagger ativado no backend**

Você já usou o Swagger? Se ainda não, adicione ao seu `pom.xml` e habilite no Spring Boot. Posso te ajudar com isso.

---

### 🌐 4. **Deploy Railway (ou Render)**

Se quiser ajuda com o deploy no [Railway](https://railway.app/) ou [Render](https://render.com/), posso te guiar passo a passo. Ambos oferecem versão gratuita.



