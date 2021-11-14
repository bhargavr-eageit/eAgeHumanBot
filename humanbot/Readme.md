# eAge HumanBot Application

### References
fot jwt creation and validation referred - https://github.com/auth0/java-jwt

### API Documentation
The following steps can be referred to run the API:

#### Application context path defined - /bot/query
##API 1 - GET - http://localhost:8080/bot/query?query=Hey Service, can you provide me a question with numbers to add?

###### Response is:

Response header with header key 'Authorization' and value as 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJcIlBsZWFzZSBzdW0gdGhlIG51bWJlcnMgMCwxMywxMFwiIiwiaXNzIjoiand0Lmlzc3VlciIsImV4cCI6MTYzNjg2MzMxNH0.3_iYL2zI2NAmrKeoBwbfwSpL_cLkVzTVA_8fyBrIuRk'

Response body as below
Here you go, solve the question: "Please sum the numbers 0,13,10".

###### JWT token set in the response body of the GET request will expire in 5 mins.


##API 2 - POST - http://localhost:8080/bot/query/
 Request should have a proper Authorization header having the valid token generated from the GET request.
 Example: 'Authorization'='Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJcIlBsZWFzZSBzdW0gdGhlIG51bWJlcnMgMCwxMywxMFwiIiwiaXNzIjoiand0Lmlzc3VlciIsImV4cCI6MTYzNjg2MzMxNH0.3_iYL2zI2NAmrKeoBwbfwSpL_cLkVzTVA_8fyBrIuRk'
 
 Request body : Sorry, the original question was "Please sum the numbers 0,13,10". and the answer is 23.
 
##### Response is:
 If the token is valid and answer is correct, it will return "That’s great".
 
 if the token is valid and answer is correct, it will return "That’s wrong. Please try again."
 
 if the token is expired or invalid, it will return "Invalid JWT Authorization header or Token expired!"
 
###### Jwt token validation - it invalid question or question is changed as part of POST - the request fails with 400.
 
 
### How to Run Application
Application runs on the default port - 8080

Application default context path - /bot/query

Application can be with the JVM command java -jar humanbot-0.0.1-SNAPSHOT.jar