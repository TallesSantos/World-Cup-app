# Stage 1: build
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src
COPY app/data ./data

RUN mvn clean package -Dmaven.test.skip=true -B


# Stage 2: runtime
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.war app.war

# cria diretórios necessários
RUN mkdir -p /app/data /app/storage

# copia arquivos de dados
COPY --from=build /app/data /app/data

EXPOSE 8091

ENTRYPOINT ["java", "-jar", "app.war"]