# Backend Service

## tests:
1. Main test with BackendService class
2. Pool Overload test with PoolOverloadTestCli
3. TODO: Crud cli test
## VM :
1.  Upload jar using script which use scp
2.  Files system: 
    * Project file exist in /usr/local/smart-building/
    * jar exist in /usr/local/smart-building/lib/**/
3.  Using env variable JAR_LIB to point to jar directory
4.  Env was made by shell file in /etc/profile.d/*.sh

## tests:
- Sockets :
  socket-test.sh --get-connection-interval=1500 --force-additionnal-request-duration=5500 --without-retention
- Pool : pool-overload.sh --initial-load=5 --up-to=10 --get-connection-interval=2500 --force-additionnal-request-duration=5000 --use-crud-operation=insert|update|select|delete --with-retention
