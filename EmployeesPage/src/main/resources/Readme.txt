Admin Handler

Get all employees
curl -v -X GET -H "Content-type:application/json" -H "Authorization:FD aw4567123edkk99"  http://localhost:15155/EmployeesPage/admin/data/employees/getAllData

Delete an employee
curl -v -X DELETE -H "Content-type:application/json" -H "Authorization:FD aw4567123edkk99"  http://localhost:15155/EmployeesPage/admin/data/employees/deleteData?userName=raul7G

Add an employee
curl -v -X POST -d '{"salary":400000, "fullName":"Raul Gonzales", "address":"Liber u90", "userName":"raul7G", "password": "Labs1234",  "employeeData":{"country":"Spain", "city": "Madrid", "gender": "male", "age":37, "email":"raulG@google.com"}}' -H "Content-type:application/json"  -H "Authorization:FD aw4567123edkk99" http://localhost:15155/EmployeesPage/admin/data/registration

Update salary for an employee
curl -v -X PUT  -H "Content-type:application/json" -H "Authorization:FD aw4567123edkk99" -d '{"salary":370000, "fullName":"Icker Casillas", "address":"Liber u94"}' http://localhost:15155/EmployeesPage/admin/data/employees/updateEmployee
------------------------------------------------------
Employee Handler
Login to system using employee's credentials
curl -v -X POST -H "Content-type:application/json" -H "Authorization:FD aw4567123edkk99" -d '{"userName":"raul7G", "password":"Labs1234"}' http://localhost:15155/EmployeesPage/employee/data/login

Send message
curl -v -X POST -H 'access-token: POW25$2@WZ!@GES3I@5)65#(&!)6>G`#0BF%GOPA2<@B4I&?#C?>%HQ(W5C2&BI(T3MM$~@8SFY#WK<`5~(8~<~JL>4~SZ~(>6@HU`()TVZ@~XL72Y~`DG@GW?~CM@L5B<*NO~YLUMFJFI9)*9(H$M' -H "Content-type:application/json" -H "Authorization:FD aw4567123edkk99" -d '{"messageText":"Whats up?", "messageSubject": "greeting", "recipients":"superFrank"}' http://localhost:15155/EmployeesPage/employee/data/sendMessage

Get an inbox message
curl -v -X GET -H 'access-token: 7902FCE4LZ<@5A2K3?N~9F`I(%8M3Z`<39XWPNXER)2K28NKKD<17FCYG%MCR@Y#@F0<@)4IL(F45ARJ(Y&$BV7R?&*`)KKY58K4@W!GKMN88<W5D!CW<QB&*)C2)MR&L>7@CT1AX*@7W34~SOC2I(' -H "Content-type:application/json" -H "Authorization:FD aw4567123edkk99" http://localhost:15155/EmployeesPage/employee/data/getaninbox?id=1

Get a sent message
curl -v -X GET -H 'access-token: 2!>&1RNR>7X6M$MRMSARM)0V*RZ0RODKS!7Y>4XO0EQKK$UK`WDMMB0SCX)25%S)1>?A)U9$DO@<U~LW~QI5`$D>E*Z2X!4ZPL@6XCL`KPOLN#DLGGM3V&JZ2X!ED!QF@F3@@OM2~R~&17RH*TQ&1C' -H "Content-type:application/json" -H "Authorization:FD aw4567123edkk99" http://localhost:15155/EmployeesPage/employee/data/getasent?id=8

Delete a send message
curl -v -X POST -H 'access-token: !MR`M%3~OTXFKWT%A98CYD@P$(A7R$BNH3GNH~C#@U6H*EB&NWFPK87&XE>Q)?`F9T0#AKBTY$Y)12$1YV6)U%Q3C8X!9&V?RC#6IJ>0OY*!!2CF94Z@*N*B`<U@L88@F67X%)#&<M8Q2WTH62@N`3' -H "Authorization:FD aw4567123edkk99" -d '{}' http://localhost:15155/EmployeesPage/employee/data/deleteasent?id=8

Delete an inbox message
curl -v -X POST -H 'access-token: %V#%0$@X!U#1!TJ?&HSPE2F!)`M7H~P35D<ROHSG9ZKD78EKU@6Q2~RQX2((#`O4)9IB>MCVS>2IHT6M5OJ3`L<7DVR8$CX<Y1UKHY7Y31W?`&(<X$QX?$@C(A&23G`76S)(J7UM~DDMAJ~<&O?*II' -H "Authorization:FD aw4567123edkk99" -d '{}' http://localhost:15155/EmployeesPage/employee/data/deleteaninbox?id=11

