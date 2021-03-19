#socket-test.sh --get-connection-interval=1500 --force-additionnal-request-duration=5500 --without-retention
path="${M2_REPO}/edu/episen/si/ing1/pds/backend-service/1.0-SNAPSHOT"
filejar="${path}/backend-service-1.0-SNAPSHOT-jar-with-dependencies.jar"
mainClass="edu.episen.si.ing1.pds.backend.server.test.socketTest.SocketsTest"
exec java -classpath ${filejar} ${mainClass} ${*}