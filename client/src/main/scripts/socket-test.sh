path="${M2_REPO}/edu/episen/si/ing1/pds/client/1.0-SNAPSHOT"
filejar="${path}/client-1.0-SNAPSHOT-jar-with-dependencies.jar"
mainClass="edu.episen.si.ing1.pds.client.test.SocketTestCli"
exec java -classpath ${filejar} ${mainClass} ${*}