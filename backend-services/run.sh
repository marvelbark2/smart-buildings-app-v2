args="${1} ${2}"
#mvn clean install compile
exec mvn exec:java -Dexec.mainClass="edu.episen.si.ing1.pds.backend.serveur.BackendServices" -Dexec.args="${args}"