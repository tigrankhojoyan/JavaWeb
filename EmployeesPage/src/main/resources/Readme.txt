Admin Handler

Get all employees
curl -v -X GET -H "Content-type:application/json" -H "Authorization:FD aw4567123edkk99"  http://localhost:15155/EmployeesPage/admin/data/employees/getAllData
Delete an employee
curl -v -X DELETE -H "Content-type:application/json" -H "Authorization:FD aw4567123edkk99"  http://localhost:15155/EmployeesPage/admin/data/employees/deleteData?fullName=Raul+Gonzales
Add an employee
curl -v -X POST -d '{"salary":400000, "fullName":"Raul Gonzales", "address":"Liber u90", "userName":"raul7G", "password": "Labs1234",  "employeeData":{"country":"Spain", "city": "Madrid", "gender": "male", "age":37, "email":"raulG@google.com"}}' -H "Content-type:application/json"  -H "Authorization:FD aw4567123edkk99" http://localhost:15155/EmployeesPage/admin/data/registration
Update salary for an employee
curl -v -X PUT  -H "Content-type:application/json" -H "Authorization:FD aw4567123edkk99" -d '{"salary":370000, "fullName":"Icker Casillas", "address":"Liber u94"}' http://localhost:15155/EmployeesPage/admin/data/employees/updateEmployee
------------------------------------------------------
Employee Handler

