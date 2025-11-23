# 📘 **DDD, Validação e Regras de Negócio em Java 25 com JPA**

Este documento explica como implementar um sistema totalmente alinhado com:

* **DDD (Domain-Driven Design)**
* **Validação na borda (Validation)**
* **Regras de negócio no domínio (Business Rules)**
* **JPA + Postgres** (sem Kafka)
* **Arquitetura limpa e modular**
* **Java 25 com recursos modernos**

seguindo rigorosamente os princípios dos artigos:

1. **Mark Seemann — “Validation and Business Rules”**
2. **James Hickey — “Where Do I Put My Business Rules and Validation?”**

Este README aprofunda conceitos, camadas, separação de responsabilidades e usa **recursos do Java 25** para tornar o domínio mais expressivo, seguro e limpo.

---

# 🧠 1. Conceitos centrais das documentações

## 1.1 O que Mark Seemann defende (Validation ≠ Business Rules)

Mark Seemann mostra que muitas pessoas confundem “validação” com “regras de negócio”.

Segundo ele:

### ✔ Validação (Validation)

É apenas verificação de:

* formato
* obrigatoriedade
* limites simples
* tipos
* coerência mínima

Características:

* Pura (sem banco, sem API externa)
* Determinística
* Sem contexto
* Está na **borda do sistema**

### ✔ Regras de negócio (Business Rules)

São decisões sobre o domínio:

* Pode ou não pode?
* Faz sentido no mundo real?
* Está permitido de acordo com o estado do sistema?

Ou seja:

* Dependem de contexto
* Dependem de dados reais
* Podem consultar banco
* São específicas do negócio

📌 **Ele defende separar rigorosamente essas duas responsabilidades.**

---

## 1.2 O que James Hickey defende (Onde colocar o quê)

James Hickey responde à pergunta:

**“Onde coloco minhas validações e regras?”**

E a resposta é:

### ✔ Validações simples → na Borda (DTO + Bean Validation)

Ex.: campo não nulo, e-mail válido, número positivo.

### ✔ Value Objects → garantem invariantes

Não podem nascer inválidos.
Isso protege o domínio de estados incoerentes.

### ✔ Regras de negócio → no Domínio

Dentro de:

* Entidades
* Value Objects
* Domain Services

### ❌ Nunca em:

* Controllers
* DTOs
* Repositórios
* Infraestrutura

---

# 🧱 2. Conceitos de DDD aplicados neste projeto

## 2.1 Domínio como centro

O **domínio** é o lugar onde:

* Regras importantes vivem
* Decisões são tomadas
* O modelo reflete o negócio real

## 2.2 Camadas

O sistema é separado em quatro camadas:

### 1. API

Recebe requisições HTTP e faz validação estrutural (pré-condições).

### 2. Application

Orquestra casos de uso, sem regra de negócio.

### 3. Domain

Onde estão **todas as regras**, invariantes, entidades e value objects.

### 4. Infrastructure

Banco, JPA, mappers, configurações — sem regra de negócio.

---

# 🚀 3. O que há de novo e poderoso no **Java 25** para DDD

O Java 25 trouxe melhorias que deixam a modelagem muito mais limpa e forte.

Aqui está a visão aprofundada:

---

## ✔ 3.1 Records mais maduros (Value Objects perfeitos)

Records agora são totalmente ideais para:

* Value Objects imutáveis
* Garantir invariantes
* Modelos conceituais do domínio

Com “compact constructors” mais poderosos, você pode garantir:

* validações internas
* invariantes obrigatórios
* sem boilerplate

Eles fortalecem o domínio e deixam a modelagem natural.

---

## ✔ 3.2 Pattern Matching expandido

O Java 25 expandiu o pattern matching para:

* switch mais expressivo
* matching em classes, sealed types, records
* maior segurança de tipos

Isso facilita:

* pipelines de validação
* combinações de resultados de negócio
* regras condicionais complexas
* clareza sem ifs aninhados

É excelente para separar:

* resultado válido
* resultado inválido
* regras específicas por tipo

---

## ✔ 3.3 Sealed Classes e Sealed Interfaces

Esses tipos controlam **quem pode implementar/extender**.

No contexto de validação e domínio, isso permite:

* Representar estados válidos/invalidos
* Comunicá-los sem exceções
* Garantir que nenhum estado inesperado apareça
* Forçar o switch a tratar todos os casos

Perfeito para criar:

* `ValidationResult` (Valid / Invalid)
* Tipos fechados no domínio
* Estados específicos de entidade

---

## ✔ 3.4 Virtual Threads (Project Loom)

Java 25 possui virtual threads estabilizados, o que significa:

* manipulação ultraleve de chamadas IO (banco, APIs)
* sem bloqueio de threads reais
* sem custos de criação de threads

Para aplicações DDD que usam JPA:

* cada caso de uso pode rodar em uma virtual thread
* reduz drastically overhead em cenários de múltiplas requisições

---

## ✔ 3.5 Melhorias de performance e segurança da JVM

Java 25 incorpora mais otimizações em:

* G1 e ZGC
* Code cache
* Hotspot JIT
* Regras de segurança aprimoradas

O resultado:

* domínio rápido
* persistência mais eficiente
* camadas separadas sem penalidade

---

# 🧩 4. Estrutura de pastas recomendada

```
src/main/java/com/example/order/
  api/
    controllers/
    dtos/
  application/
    services/
  domain/
    entities/
    valueobjects/
    validators/
    exceptions/
  infrastructure/
    repository/
      entities/
      mappers/
    config/
```

Explicação:

| Pasta            | Responsabilidade                       |
| ---------------- | -------------------------------------- |
| `api`            | controllers + validação via DTO        |
| `application`    | orquestração (casos de uso), sem regra |
| `domain`         | regras de negócio e invariantes        |
| `infrastructure` | banco, JPA, mappers                    |

---

# 🔄 5. Fluxo completo da aplicação

1. **O usuário chama a API**
2. A requisição passa por **Bean Validation** (validação estrutural)
3. A Application Layer recebe o request validado
4. Ela converte para o formato do domínio e chama o **domínio**
5. O domínio aplica:

    * invariantes
    * validações puras
    * regras de negócio
    * consultas a repositórios
6. Se tudo estiver correto, a Application Layer:

    * salva no banco via repositório
7. A API responde ao cliente

---

# 🏦 6. Persistência com JPA (sem Kafka)

Este projeto usa:

* Entidades JPA **somente na infraestrutura**
* Mappers para converter **Domínio ↔ JPA**
* Repositórios Spring Data para CRUD
* Postgres ou H2 como banco de dados

NUNCA expomos JPA ao domínio.

---

# ⚙️ 7. Instalação, Build e Execução

Abaixo estão as instruções completas para instalar e rodar **com Java 25**.

---

## ✔ 7.1 Instalar Java 25

### No Linux (SDKMAN)

```
sdk install java 25-open
sdk use java 25-open
```

### No Windows (Winget)

```
winget install EclipseAdoptium.Temurin.25.JDK
```

### No macOS (Homebrew)

```
brew install temurin25
```

Verifique:

```
java -version
```

Deve aparecer algo como:

```
openjdk 25.x.x ...
```

---

## ✔ 7.2 Clonar o projeto

```
git clone https://github.com/seuusuario/nome-projeto.git
cd nome-projeto
```

---

## ✔ 7.3 Build com Maven

```
mvn clean package
```

Se quiser rodar os testes:

```
mvn test
```

---

## ✔ 7.4 Rodar a aplicação

```
mvn spring-boot:run
```

A aplicação sobe por padrão em:

```
http://localhost:8080
```

---

## ✔ 7.5 Docker (opcional)

### Subir banco Postgres:

Crie um `docker-compose.yml` com Postgres.

Depois rode:

```
docker-compose up -d
```

### Executar aplicação com banco configurado

Configurar `application.yml` com suas credenciais e rodar:

```
mvn spring-boot:run
```

---

# 💎 8. Benefícios dessa arquitetura

* Domínio limpo, forte e expressivo
* Regras de negócio isoladas
* Validação clara e separada
* Independência de infraestrutura
* Fácil de evoluir e testar
* Aproveita 100% os recursos avançados do Java 25
* Evita espalhar lógica por camadas erradas
* Evita corrupção de domínio
* Código mais claro e sustentável

---

# 🏁 **Conclusão**

Combinando:

* Princípios das duas documentações
* DDD real
* Recursos modernos do Java 25
* Arquitetura clara e modular
* Persistência isolada via JPA

Este projeto cria uma base sólida, elegante e extremamente testável.

Ele garante que:

* **Validações** estão na borda
* **Regras de negócio** estão no domínio
* **JPA** fica completamente isolado
* **Domínio é independente e forte**

É uma arquitetura moderna, robusta e preparada para escalabilidade.

---
