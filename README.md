# 📘 **DDD, Validação e Regras de Negócio em Java 25 com JPA**

Este documento explica como implementar um sistema totalmente alinhado com:

* **DDD (Domain-Driven Design)**
* **Validação na borda (Validation)**
* **Regras de negócio no domínio (Business Rules)**
* **JPA + Postgres**
* **Arquitetura limpa e modular**
* **Java 25 com recursos modernos**

Seguindo rigorosamente os princípios dos artigos:

1. **Mark Seemann — “Validation and Business Rules”**
2. **James Hickey — “Where Do I Put My Business Rules and Validation?”**

> 🔽
> **Links das referências (inserir aqui):**
>
> * [link 1]
> * [link 2]
>   🔼

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
* Deve ocorrer na **borda do sistema**
* NÃO deve depender do domínio

### ✔ Regras de negócio (Business Rules)

São decisões sobre o domínio:

* Pode ou não pode?
* Faz sentido no mundo real?
* Está permitido de acordo com o estado do sistema?

Ou seja:

* Dependem de contexto
* Podem consultar banco
* São específicas do negócio
* Devem estar **dentro do domínio**

📌 **Ele defende separar rigorosamente essas duas responsabilidades.**

---

## 1.2 O que James Hickey defende (Onde colocar o quê)

James Hickey responde à pergunta:

**“Onde coloco minhas validações e regras?”**

E a resposta é:

### ✔ Validações simples → na Borda (DTO + Bean Validation)

Ex.: campo não nulo, e-mail válido, número positivo.

### ✔ Value Objects → garantem invariantes

Eles NUNCA nascem inválidos. Isso protege o domínio de estados incoerentes.

### ✔ Regras de negócio → no Domínio

Ficam dentro de:

* Entidades
* Value Objects
* Domain Services

### ❌ Nunca em:

* Controllers
* DTOs
* Repositórios
* Infraestrutura

---

# ⚠️ 1.3 Sobre validações redundantes (Importante!)

Em algumas arquiteturas, você pode acabar duplicando validações, como:

* Validar `@NotNull` no DTO (borda)
* E validar novamente no domínio (por exemplo, em um Value Object)

Isso acontece muito porque:

* Bean Validation protege a API
* Value Objects protegem o domínio
* Interfaces ou records exigem invariantes internos

### ✔ É aceitável ter validações redundantes?

Sim — dependendo da sua filosofia.

### ✔ Mas pode ser desnecessário?

Também sim.

---

# 🎯 **Melhor prática (recomendada pelos artigos):**

👉 **Escolha UMA abordagem principal para validações simples**
e seja consistente.

### 📌 Abordagem 1 (mais usada):

* **DTO:** validações estruturais (NotNull, tamanho, formato)
* **Domínio:** invariantes e regras de negócio (ex.: "deve ser positivo")

### 📌 Abordagem 2 (mais defensiva):

* **DTO:** validação mínima
* **Domínio:** valida tudo (simples + invariantes + regras)

### ✔ Quando duplicar validações?

Quando:

* o valor chega ao domínio vindo de outra origem (ex.: fila, CLI, integração)
* o domínio precisa ser 100% seguro
* possível ataque/bypass à borda

### ✔ Quando NÃO duplicar?

Quando:

* domínio recebe dados *somente* via API
* borda já garante todas as pré-condições

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

Recebe requisições HTTP e faz validação estrutural.

### 2. Application

Orquestra casos de uso; sem regra de negócio.

### 3. Domain

Onde estão **todas as regras**, invariantes, entidades e value objects.

### 4. Infrastructure

Banco, JPA, mappers, configurações — sem regra de negócio.

---

# 🚀 3. Novidades e vantagens do **Java 25** para DDD

Java 25 trouxe melhorias que fortalecem a arquitetura e o domínio.

---

## ✔ 3.1 Records maduros (Value Objects perfeitos)

Ideal para:

* imutabilidade
* invariantes
* validação interna
* modelagem limpa

---

## ✔ 3.2 Pattern Matching expandido

Facilita:

* validações declarativas
* combinação de regras
* switches legíveis
* modelagem funcional

---

## ✔ 3.3 Sealed Classes / Interfaces

Controla quem pode implementar estaticamente:

* `Valid`, `Invalid`
* estados fechados no domínio
* tipos exclusivos para invariantes

---

## ✔ 3.4 Virtual Threads (Loom)

Beneficia:

* chamadas JPA
* I/O não bloqueante com custo baixíssimo
* escalabilidade de requests simultâneos

---

## ✔ 3.5 JVM mais rápida e segura

Melhorias em:

* G1/ZGC
* Hotspot
* validações do classloader

---

# 🧩 4. Estrutura de pastas recomendada

```java
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

---

# 🔄 5. Fluxo completo da aplicação

1. API recebe request
2. DTO valida pré-condições (Bean Validation)
3. Application cria o caso de uso
4. Domínio avalia invariantes e regras
5. Infra persiste dados
6. API devolve resposta

---

# 🏦 6. Persistência com JPA

* Entidades JPA ficam na **infraestrutura**
* Domínio nunca utiliza JPA diretamente
* Mappers fazem Domínio ↔ Persistence
* Repositórios Spring Data abstraem o acesso

---

# ⚙️ 7. Instalação, Build e Execução

### 7.1 Instalar Java 25

Linux (SDKMAN):

```shell
sdk install java 25-open
sdk use java 25-open
```

Windows (Winget):

```shell
winget install EclipseAdoptium.Temurin.25.JDK
```

macOS:

```shell
brew install temurin25
```

Verificar versão:

```shell
java -version
```

---

### 7.2 Clonar o projeto

```shell
git clone https://github.com/seuusuario/nome-projeto.git
cd nome-projeto
```

---

### 7.3 Build

```shell
mvn clean package
```

Testes:

```shell
mvn test
```

---

### 7.4 Rodar

```shell
mvn spring-boot:run
```

URL padrão:

```shell
http://localhost:8080
```

---

### 7.5 Rodar com Docker e Postgres

```shell
docker-compose up -d
```

E executar:

```shell
mvn spring-boot:run
```

---

# 💎 8. Benefícios dessa arquitetura

* Domínio forte, expressivo e protegido
* Validações claras e separadas
* Evita duplicação desnecessária de validações (ou lida com ela conscientemente)
* Independência de infraestrutura
* Código mais testável e previsível
* Modelagem alinhada com Java moderno
* Sem regras espalhadas pela API, application ou infra
* Claridade total de responsabilidades

---

# 🏁 **Conclusão**

Combinar:

* Princípios das duas documentações
* DDD moderno
* Java 25 e seus recursos avançados
* Estrutura limpa e modular

gera uma aplicação:

* **coesa**
* **forte**
* **segura**
* **previsível**
* **e fácil de manter**

As validações ficam **onde devem ficar**.
As regras de negócio ficam **onde devem ficar**.
E o domínio permanece **protegido**, como manda o DDD.

---
---

# 📚 **Glossário**

Abaixo estão todos os conceitos mencionados no README explicados de forma clara.

---

# 🟦 **Java 25 (Conceitos e Recursos Modernos)**

### **Record**

Um tipo imutável introduzido para representar dados.
Ideal para DTOs e Value Objects no DDD.

* gera automaticamente: construtor, equals, hashCode e toString
* não tem setters → imutável por padrão
* permite “compact constructors” para validar invariantes

---

### **Compact Constructor**

Um construtor enxuto usado dentro de um record para validar dados recebidos.

Exemplo conceitual:

```java
record Amount(BigDecimal value) {
    public Amount {
        if (value <= 0) throw error;
    }
}
```

Serve para impedir que o record nasça inválido.

---

### **Pattern Matching**

Recurso avançado do Java que permite escrever comparações e verificações de tipos de maneira muito mais clara e segura.

Facilita:

* validações condicionais
* switches mais limpos
* lógica declarativa
* evitar `instanceof` e casts repetidos

---

### **Pattern Matching for Switch**

Extensão do switch tradicional permitindo:

* condições (guards)
* trabalhar com records
* sealed types
* lógica mais legível

É muito usado no domínio para modelar regras.

---

### **Sealed Classes / Sealed Interfaces**

Tipos que controlam quem pode implementá-los.

Exemplo conceitual:

```java
sealed interface Result permits Valid, Invalid {}
```

Vantagens:

* garante estados fechados
* útil para representar resultados de validação
* permite que o switch trate *todas* as possibilidades

---

### **Virtual Thread (Project Loom)**

Novo modelo de execução de threads muito leves.

Benefícios:

* lidar com I/O sem bloqueio
* permitir milhares/milhões de chamadas simultâneas
* ideal para APIs e aplicações DDD que consultam banco

É transparente: parece thread normal, mas com custo muito menor.

---

### **G1 / ZGC**

Garbage Collectors otimizados.

* **G1** : baixa latência previsível
* **ZGC** : praticamente pausas de GC imperceptíveis

Ambos melhoram desempenho em APIs modernas.

---

### **JIT / HotSpot**

Componentes da JVM que otimizam o código durante a execução.

* JIT (Just-In-Time Compiler) compila trechos mais usados para código nativo
* HotSpot otimiza loops e padrões de execução

Melhora a performance do domínio sem você fazer nada.

---

---

# 🟥 **DDD (Domain-Driven Design)**

### **Domínio**

Coração da aplicação.
Onde vivem:

* regras de negócio
* invariantes
* entidades
* value objects

Não deve depender de frameworks (como JPA).

---

### **Entidade (Domain Entity)**

Objeto do domínio que possui identidade própria e regras associadas.

Exemplo: Order, Customer.

Características:

* carrega regras de negócio
* tem invariantes internos
* existe independentemente da persistência

---

### **Value Object**

Objeto imutável que representa um conceito (valor, medida, identificação).

Características:

* sem identidade
* imutável
* validado no construtor
* reforça invariantes

Exemplo: OrderAmount, Email, CPF, Price.

---

### **Invariante**

Regra que sempre deve ser verdadeira para que um objeto exista corretamente.

Exemplos:

* Amount > 0
* Email válido
* A data não pode estar no futuro

Garantidos por:

* Value Objects
* Entidades de domínio

---

### **Regra de Negócio**

Decisões sobre o funcionamento real da empresa.

Exemplos:

* cliente inativo não pode comprar
* pedido mínimo é R$ 100
* assinatura só renova dentro do prazo

Devem estar **exclusivamente** no domínio.

---

### **Validação**

Garantia de que os dados têm formato correto.

Exemplos:

* campo obrigatório
* número mínimo
* tipo correto

Pertence à **borda do sistema** (DTO).

---

### **Application Layer (Service)**

Orquestra o caso de uso.

* chama o domínio
* chama os repositórios
* não contém regra de negócio

É a “cola” da arquitetura.

---

### **Infrastructure**

Onde ficam detalhes técnicos:

* JPA Entities
* Repositórios Spring Data
* Conexão com banco
* Configurações

Domínio não deve depender dessa camada.

---

### **JPA Entity (Infra)**

Representação de uma tabela no banco.

* mutável
* possui setters
* usada apenas pela infraestrutura

Nunca deve ser usada dentro do domínio.

---

### **Mapper**

Componente que transforma:

* Domínio → Entity JPA
* Entity JPA → Domínio

Serve como fronteira entre os mundos.

---

---

# 🟨 **Validação vs Regras de Negócio**

### **Validação Estrutural**

Feita no DTO, com Bean Validation.

* NotNull
* Positive
* Email
* Tamanho mínimo

Sem contexto.
Sem depender de banco.

---

### **Validação do Domínio (Business Rules)**

Regras internas necessárias para que o modelo exista corretamente.

Exemplo:

* Amount não pode ser menor que 100
* Cliente precisa estar ativo
* Data deve estar dentro de uma tolerância

---

### **Validação Redundante**

Quando você valida a mesma coisa:

* no DTO
* e no domínio
* ou no Value Object
* ou no serviço
* ou na interface

Pode acontecer, mas deve ser **uma escolha consciente**.

---

### **Recomendação**

Escolha uma estratégia:

* validação simples na borda + invariantes no domínio
  OU
* validação completa no domínio (defensiva)

Mas **evite duplicar sem querer**.

---

---

# 🟩 **Arquitetura**

### **API Layer**

Cuida de:

* controllers
* endpoints
* DTOs
* validação estrutural

---

### **Application Layer**

Responsável por:

* iniciar caso de uso
* chamar o domínio
* chamar a infraestrutura
* transações

---

### **Domain Layer**

Contém:

* decisões
* modelos ricos
* invariantes
* regras complexas

Não conhece banco, HTTP, controllers, nem JPA.

---

### **Infra Layer**

Contém:

* Banco
* JPA
* Mappers
* Configurações

É substituível.

---
