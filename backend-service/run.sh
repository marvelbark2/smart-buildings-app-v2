args="${1} ${2}"
filejar="target/backend-service-1.0-SNAPSHOT-jar-with-dependencies.jar"
mainClass="edu.episen.si.ing1.pds.backend.serveur.BackendService"
classp="${filejar}:src/main/resources"
exec java -classpath ${classp} ${mainClass} ${args}