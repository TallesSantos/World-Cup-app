# ⚽ World Cup API

API desenvolvida com Spring Boot para gerenciamento de dados relacionados à Copa do Mundo.

O projeto também possui suporte para disponibilização de imagens locais através de rotas públicas.

---

# 🚀 Tecnologias Utilizadas

- Java
- Spring Boot
- Maven
- REST API

---

# ⚙️ Configuração da Aplicação

A aplicação utiliza o arquivo `application.yml` para definir as configurações do servidor e armazenamento de imagens.

## 📄 application.yml

```yml
server:
  port: 8081

spring:
  application:
    name: 'World Cup api'

resource:
  server-side-pattern-path: '/images/**'
  storage:
    path: 'D:/images'
```

---

# 🔍 Explicação das Configurações

---

## 🌐 Porta da aplicação

```yml
server:
  port: 8081
```

Define a porta HTTP utilizada pela aplicação.

A API ficará disponível em:

```txt
http://localhost:8081
```

---

## 🏷️ Nome da aplicação

```yml
spring:
  application:
    name: 'World Cup api'
```

Define o nome interno da aplicação Spring Boot.

Esse nome pode ser utilizado em:
- logs
- monitoramento
- observabilidade

---

## 🖼️ Configuração de imagens

### Rota pública de imagens

```yml
resource:
  server-side-pattern-path: '/images/**'
```

Define o padrão de rota utilizado para acessar imagens no servidor.

Exemplo:

```txt
http://localhost:8081/images/brazil.png
```

---

### Diretório físico das imagens

```yml
resource:
  storage:
    path: 'D:/images'
```

Define a pasta física onde as imagens ficam armazenadas.

Exemplo de estrutura:

```txt
D:/images
 ├── brazil.png
 ├── argentina.png
 ├── france.png
```

---

# 🌍 Exemplo de acesso a imagem

Arquivo salvo em:

```txt
D:/images/brazil.png
```

Pode ser acessado por:

```txt
http://localhost:8081/images/brazil.png
```

---

# ▶️ Executando o Projeto

## 📦 Instalar dependências

```bash
mvn clean install
```

---

## 🚀 Executar aplicação

```bash
mvn spring-boot:run
```

---

# 🛠️ Gerar Build

```bash
mvn clean package
```

O arquivo `.jar` será gerado em:

```txt
/target
```

---

# ▶️ Executando o arquivo JAR

Após gerar o build:

```bash
java -jar target/world-cup-api.jar
```

---

# 📁 Estrutura recomendada

```txt
src
 ├── main
 │   ├── java
 │   └── resources
 │       └── application.yml
```

---

# 📌 Observações

- Certifique-se de que a pasta configurada em:

```yml
resource.storage.path
```

exista no sistema operacional.

---

## 🪟 Windows

```txt
D:/images
```

ou

```txt
D:\\images
```

---

## 🐧 Linux

```txt
/var/images
```

---

# 📜 Licença

Projeto desenvolvido para fins educacionais.
Projeto desenvolvido para fins educacionais.