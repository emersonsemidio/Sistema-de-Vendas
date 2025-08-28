FROM maven:3.8.6-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
# Definir diretório de trabalho
WORKDIR /app
# Copiar o arquivo JAR
COPY target/sistema-0.0.1-SNAPSHOT.jar app.jar
# Expor a porta (Render usa a porta 10000 por padrão)
EXPOSE 10000
# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=10000"]