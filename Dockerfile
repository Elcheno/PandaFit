# Usar una imagen base con JDK 11 y Maven
FROM maven:4.0.0-corretto-17 AS build

# Establecer un directorio de trabajo
WORKDIR /app

# Copiar archivos de tu proyecto al directorio de trabajo
COPY . /app

# Ejecutar Maven para construir el proyecto
RUN mvn clean package

# Crear una nueva imagen basada en corretto 17
FROM corretto:17-jre-slim-buster

# Exponer el puerto que utilizará la aplicación
EXPOSE 8080

# Copiar el archivo JAR construido desde la etapa anterior
COPY --from=build /app/target/pandafit-0.0.1-SNAPSHOT.jar /app/pandafit-0.0.1-SNAPSHOT.jar

# Establecer el punto de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/pandafit-0.0.1-SNAPSHOT.jar"]