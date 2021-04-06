##socket-test.sh --use-crud-operation="insert|update|select|delete" --values="{name=Patrice,email=patrice@u.fr,telephone=09923922302}" --localEnv
##socket-test.sh --use-crud-operation="insert|update|select|delete" --json-sample-file=$SMARTBUILDSAMPLE
path="${M2_REPO}/edu/episen/si/ing1/pds/client/1.0-SNAPSHOT"
filejar="${path}/client-1.0-SNAPSHOT-jar-with-dependencies.jar"
mainClass="edu.episen.si.ing1.pds.client.test.SocketTestCli"
exec java -classpath ${filejar} ${mainClass} ${*}