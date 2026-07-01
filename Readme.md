# NotaFlow — Demonstração de Broken Access Control (OWASP A01:2021)

Sistema acadêmico de gestão de notas desenvolvido como prova de conceito para
demonstrar a vulnerabilidade **Broken Access Control**, classificada como a
principal ameaça em aplicações web pelo OWASP Top 10 2021.

## 📖 Sobre o Projeto

O NotaFlow simula um sistema onde professores lançam e editam notas de alunos.
O projeto foi desenvolvido em duas versões paralelas para evidenciar, de forma
prática, os riscos da ausência de controles de acesso e a eficácia das medidas
de segurança implementadas para mitigação.

## 🌿 Branches

| Branch | Descrição |
|--------|-----------|
| `inseguro` | Versão vulnerável — sem autenticação nem autorização nos endpoints. Qualquer usuário pode modificar notas de outros alunos via requisição HTTP direta. |
| `seguro` | Versão protegida — implementa Spring Security, JWT com RSA, HTTP Only cookie e RBAC. |

## 🛠️ Tecnologias

- **Back-end:** Spring Boot 4.0, Spring Security, JJWT 0.12.5
- **Front-end:** HTML, CSS e JavaScript puro (servido pelo próprio Spring Boot na versão segura)
- **Banco de dados:** MariaDB
- **Containerização:** Docker e Docker Compose

## 🔓 Versão Insegura (branch `inseguro`)

A versão insegura expõe todos os endpoints REST sem qualquer mecanismo de
proteção. A única configuração presente é a anotação `@CrossOrigin` para
permitir requisições de origens diferentes. Não há autenticação, autorização
ou verificação de identidade — qualquer requisição bem-formada é processada.

**Como rodar:**
```bash
git checkout inseguro
docker compose up -d --build
```

Acesse em: `http://localhost:8081`

## 🔒 Versão Segura (branch `seguro`)

A versão segura implementa as seguintes camadas de proteção:

- **RBAC** — controle de acesso baseado em papéis (`ROLE_PROFESSOR`, `ROLE_ALUNO`, `ROLE_USER`)
- **Spring Security** — regras explícitas de autorização por papel para cada endpoint
- **JWT com RSA** — token assinado com criptografia assimétrica, verificado a cada requisição
- **HTTP Only Cookie** — token inacessível ao JavaScript, protegendo contra XSS
- **Filtro de autenticação** — intercepta todas as requisições e valida o token antes de permitir acesso
- **Front-end integrado** — servido pelo Spring Boot, eliminando problemas de CORS

**Como rodar:**
```bash
git checkout seguro
docker compose up -d --build
```

Acesse em: `http://localhost:8084`

## ⚔️ Demonstração do Ataque

1. Rode a versão insegura
2. Faça login como aluno em `http://localhost:8081`
3. Abra o DevTools ou Postman e envie:
```http
PATCH http://localhost:8080/api/avaliacoes/{id}
Content-Type: application/json

{ "valor": 10.0 }
```
4. A nota é alterada sem qualquer verificação de identidade ou permissão

Na versão segura, a mesma requisição retorna **HTTP 403 Forbidden**.

## 📚 Referências

- AUTH0. Why Broken Access Control Still Dominates OWASP Top 10. Disponível em: https://auth0.com/blog/why-broken-access-control-still-dominates-owasp-top-10/
- SPRING. Spring Security Reference Documentation. Disponível em: https://docs.spring.io/spring-security/reference/servlet/authorization/architecture.html
- ZAPROXY. Access Control Testing. Disponível em: https://www.zaproxy.org/docs/desktop/addons/access-control-testing/

## 👩‍💻 Autora

Desenvolvido como projeto de curso — Segurança da Informação  
Instituto Federal de São Paulo – Campus Guarulhos  
Professor: Robson Ferreira Lopes
