#!/bin/bash

REMOTE_USER="ubuntu" # Nombre de usuario del servidor remoto
REMOTE_HOST="backend.pandafit.es" # Nombre o Ip del host
REMOTE_DIR="/opt/tomcat/webapps" # Directorio de destino en el servidor remoto
LOCAL_FILE="target/*.war" # Ruta del archivo local .war
CREDENTIAL_FILE="credentials/pandafit_key.pem" # Ruta de la clave de acceso para el servidor remoto

# Función para mostrar mensajes de error y salir
function error_exit {
    echo "$1" 1>&2
    exit 1
}

# Limpiar el directorio remoto
echo "Limpiando el contenido del directorio remoto ${REMOTE_DIR}..."
ssh -i ${CREDENTIAL_FILE} ${REMOTE_USER}@${REMOTE_HOST} "sudo rm -rf ${REMOTE_DIR}/*" || error_exit "Error al limpiar el directorio remoto."

# Enviar el archivo .war al servidor remoto
echo "Enviando archivo ${LOCAL_FILE} al servidor remoto..."
scp -i ${CREDENTIAL_FILE} ${LOCAL_FILE} ${REMOTE_USER}@${REMOTE_HOST}:/tmp || error_exit "Error al enviar el archivo."

# Mover el archivo .war a la ruta del servidor tomcat
echo "Moviendo archivo a la ruta de despliege ${REMOTE_DIR}..."
ssh -i ${CREDENTIAL_FILE} ${REMOTE_USER}@${REMOTE_HOST} "sudo cp /tmp/*.war ${REMOTE_DIR}/ROOT.war"

# Reiniciar servicio tomcat para que los cambios surjan efecto
echo "Reiniciando servicio tomcat..."
ssh -i ${CREDENTIAL_FILE} ${REMOTE_USER}@${REMOTE_HOST} "sudo systemctl restart tomcat" || error_exit "Error al reiniciar el servicio tomcat."

echo "Despliegue completado exitosamente."
