jarFile=${M2_REPO}/edu/episen/si/ing1/pds/backend-service/1.0-SNAPSHOT/backend-service-1.0-SNAPSHOT-jar-with-dependencies.jar
pathServer=/usr/local/smart-building/lib/edu/episen/si/ing1/pds/backend-service/1.0-SNAPSHOT

exec scp ${jarFile} pdsBack:${pathServer}
