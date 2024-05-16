#!/bin/bash

REMOTE_USER="ubuntu" # Nombre de usuario del servidor remoto
REMOTE_HOST="backend.pandafit.es" # Nombre o Ip del host
REMOTE_DIR="/opt/tomcat/webapps" # Directorio de destino en el servidor remoto
LOCAL_FILE="target/*.war" # Ruta del archivo local .war
CREDENTIAL_FILE="credentials/pandafit_key.pem" # Ruta de la clave de acceso para el servidor remoto
NAME_WAR_FILE="ROOT" # Nombre del archivo .war en la ruta de despliegue para desarrollar ramas de despliegue

# Función para mostrar mensajes de error y salir
function error_exit {
    echo "$1" 1>&2
    exit 1
}

# Validar argumentos
if [ "$1" == "--test" ]; then
    NAME_WAR_FILE="test"
    echo "Despliegue en la subcarpeta ${NAME_WAR_FILE}."
elif [ "$1" == "--prod" ]; then
    NAME_WAR_FILE="ROOT"
    echo "Despliegue en la subcarpeta ${NAME_WAR_FILE}."
else
    echo "Despliegue por defecto en la subcarpeta ${NAME_WAR_FILE}."
fi

# Limpiar el directorio remoto
echo "Limpiando el contenido del directorio remoto ${REMOTE_DIR}/${NAME_WAR_FILE}..."
ssh -i ${CREDENTIAL_FILE} ${REMOTE_USER}@${REMOTE_HOST} "sudo rm -rf ${REMOTE_DIR}/${NAME_WAR_FILE}" && sudo rm -rf ${REMOTE_DIR}/${NAME_WAR_FILE}.war || error_exit "Error al limpiar el directorio remoto."

# Enviar el archivo .war al servidor remoto
echo "Enviando archivo ${LOCAL_FILE} al servidor remoto..."
scp -i ${CREDENTIAL_FILE} ${LOCAL_FILE} ${REMOTE_USER}@${REMOTE_HOST}:/tmp || error_exit "Error al enviar el archivo."

# Mover el archivo .war a la ruta del servidor tomcat
echo "Moviendo archivo a la ruta de despliege ${REMOTE_DIR}/${NAME_WAR_FILE}..."
ssh -i ${CREDENTIAL_FILE} ${REMOTE_USER}@${REMOTE_HOST} "sudo cp /tmp/*.war ${REMOTE_DIR}/${NAME_WAR_FILE}.war"


# Reiniciar servicio tomcat para que los cambios surjan efecto
echo "Reiniciando servicio tomcat..."
ssh -i ${CREDENTIAL_FILE} ${REMOTE_USER}@${REMOTE_HOST} "sudo systemctl restart tomcat" || error_exit "Error al reiniciar el servicio tomcat."

echo "Despliegue completado exitosamente."
