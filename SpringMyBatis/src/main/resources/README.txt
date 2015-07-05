Add user 
curl -v -X POST -H "Content-type:application/json" -d '{"emailId":"test1u@gmail.com","password":"zaqxsw12","firstName":"testFirstName2","lastName":"testLastName2"}' http://localhost:15155/SpringMyBatis/rest/user/registration
Get user
curl -v -X GET http://localhost:15155/SpringMyBatis/rest/user/getall