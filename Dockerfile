FROM eclipse-temurin:17-jdk-alpine

# Diretório de trabalho no container
WORKDIR /app

# Copia o JAR gerado
COPY target/sulwork-cafe-0.0.1-SNAPSHOT.jar app.jar


# Expondo a porta da aplicação
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
