curl -v -X GET http://localhost:8088/api/service/example
curl -v -X POST -d '{"userName":"usname", "password":"paSS1#@sword", "userData":{"fullName":"name", "address":"address", "age":11}}' -H 'Content-type:application/json' http://localhost:8088/api/service/createUser
curl -v -X DELETE -H 'Content-type:application/json' http://localhost:8081/api/service/deleteUser?userName=usname