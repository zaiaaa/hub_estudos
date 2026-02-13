# Estágio 1: Build com Maven e Java 17 (Usando imagem da Eclipse Temurin)
FROM dvmarques/openjdk-17-jdk-alpine-with-timezone

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto
COPY target/hub_estudos-0.0.1-SNAPSHOT.jar app.jar

# Porta que o Render vai usar (ele costuma ler a variável PORT, mas 8080 é o padrão Spring)
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]