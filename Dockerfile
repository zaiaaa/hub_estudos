# Estágio 1: Build com Maven e Java 17 (Usando imagem da Eclipse Temurin)
FROM maven:3.8.5-openjdk-17 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto
COPY . .

# Executa o build (pulando os testes para ser mais rápido no Render)
RUN mvn clean package -DskipTests

# Estágio 2: Execução (Imagem leve)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Porta que o Render vai usar (ele costuma ler a variável PORT, mas 8080 é o padrão Spring)
EXPOSE 8080

# Copia o jar gerado no estágio anterior
# O caminho correto após o 'mvn package' geralmente é target/*.jar
COPY --from=build /app/target/hub_estudos-0.0.1-SNAPSHOT.jar app.jar

# Comando para rodar a aplicação
ENTRYPOINT [ "java", "-jar", "app.jar" ]