jarFile=${M2_REPO}/edu/episen/si/ing1/pds/backend-service/1.0-SNAPSHOT/backend-service-1.0-SNAPSHOT-jar-with-dependencies.jar
pathServer=/usr/local/smart-building
jarPath=lib/edu/episen/si/ing1/pds/backend-service/1.0-SNAPSHOT
configPath=configs/smart-build-config.yaml

scp ${jarFile} root@172.31.254.83:${pathServer}/${jarPath} 


 