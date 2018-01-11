### auth-server

![](https://img.shields.io/badge/language-java-orange.svg) ![](https://img.shields.io/badge/qq-923260818-brightgreen.svg) ![](https://img.shields.io/badge/build-%20passing-brightgreen.svg) ![](https://img.shields.io/badge/created-December-brightgreen.svg)

#### 概述

授权服务（spring  security、oauth2、jwt）

请求示例:

1. /oauth/token(获取token、刷新token)

   获取token

   请求示例:

   ```
   url:
   	http://localhost:9999/oauth/token
   method:
   	post
   header:
   	Content-Type: application/x-www-form-urlencoded
   	Authorization: Basic ZnVjazpmdWNr
   body:
   	username: ufuck
   	password: pfuck
   	grant_type: password
   ```
   返回值示例:

   ```
   {
       "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTQyMzIwODUsInVzZXJJZCI6InRlc3QtZnVjayIsInVzZXJfbmFtZSI6InVmdWNrIiwianRpIjoiMDQ4NzEwNjctNGE0Zi00Y2I1LThiYjgtMDJhYjExNmE0NDJlIiwiY2xpZW50X2lkIjoiZnVjayIsInNjb3BlIjpbImFsbCJdfQ.crLO5dQsmvNqkn3VvsWJFadw1uCS-pFZXlxH1pJ4Ego",
       "token_type": "bearer",
       "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1ZnVjayIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiIwNDg3MTA2Ny00YTRmLTRjYjUtOGJiOC0wMmFiMTE2YTQ0MmUiLCJleHAiOjE1MTY3ODA4ODUsInVzZXJJZCI6InRlc3QtZnVjayIsImp0aSI6IjYwODczODA0LTYwZDYtNDA4MC04NGJkLTYwZmY3MDQ5MTE1NyIsImNsaWVudF9pZCI6ImZ1Y2sifQ.LOlBo4b3gaD3D43apgEUfCcQEyw-S-TE9DguDzFQd5M",
       "expires_in": 43199,
       "scope": "all",
       "userId": "test-fuck",
       "jti": "04871067-4a4f-4cb5-8bb8-02ab116a442e"
   }
   ```
   刷新token

   请求示例:

   ```
   url:
   	http://localhost:9999/oauth/token
   method:
   	post
   header:
   	Content-Type: application/x-www-form-urlencoded
   	Authorization: Basic ZnVjazpmdWNr
   body:
   	refresh_token: 			   eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1ZnVjayIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiIwNDg3MTA2Ny00YTRmLTRjYjUtOGJiOC0wMmFiMTE2YTQ0MmUiLCJleHAiOjE1MTY3ODA4ODUsInVzZXJJZCI6InRlc3QtZnVjayIsImp0aSI6IjYwODczODA0LTYwZDYtNDA4MC04NGJkLTYwZmY3MDQ5MTE1NyIsImNsaWVudF9pZCI6ImZ1Y2sifQ.LOlBo4b3gaD3D43apgEUfCcQEyw-S-TE9DguDzFQd5M
   	grant_type: refresh_token
   ```

   返回值示例:

   ```
   {
       "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTQyMzIyMjYsInVzZXJJZCI6InRlc3QtZnVjayIsInVzZXJfbmFtZSI6InVmdWNrIiwianRpIjoiNjAyZGQ3YjMtZjZlNC00ODgyLWE5MTAtODY5ZmI1N2NjZjk1IiwiY2xpZW50X2lkIjoiZnVjayIsInNjb3BlIjpbImFsbCJdfQ.bBpx5Ia2TUBMzvT1DA6AqbTL0m9c80ND3MVKzWt6tGc",
       "token_type": "bearer",
       "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1ZnVjayIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiI2MDJkZDdiMy1mNmU0LTQ4ODItYTkxMC04NjlmYjU3Y2NmOTUiLCJleHAiOjE1MTY3ODA4ODUsInVzZXJJZCI6InRlc3QtZnVjayIsImp0aSI6IjYwODczODA0LTYwZDYtNDA4MC04NGJkLTYwZmY3MDQ5MTE1NyIsImNsaWVudF9pZCI6ImZ1Y2sifQ.1J-Q0BBO3RJTrNmowD6q2PMr16q6PASwYyhuMWX4Qsw",
       "expires_in": 43199,
       "scope": "all",
       "userId": "test-fuck",
       "jti": "602dd7b3-f6e4-4882-a910-869fb57ccf95"
   }
   ```

2. /oauth/check_token(校验token)

   请求示例:

   ```
   url:
   	http://localhost:9999/oauth/check_token
   method:
   	post
   header:
   	Content-Type: application/x-www-form-urlencoded
   	Authorization: Basic ZnVjazpmdWNr
   body:
   	token: 			   eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTQyMzIyMjYsInVzZXJJZCI6InRlc3QtZnVjayIsInVzZXJfbmFtZSI6InVmdWNrIiwianRpIjoiNjAyZGQ3YjMtZjZlNC00ODgyLWE5MTAtODY5ZmI1N2NjZjk1IiwiY2xpZW50X2lkIjoiZnVjayIsInNjb3BlIjpbImFsbCJdfQ.bBpx5Ia2TUBMzvT1DA6AqbTL0m9c80ND3MVKzWt6tGc
   ```

   返回值示例:

   ```
   {
       "exp": 1514232226,
       "userId": "test-fuck",
       "user_name": "ufuck",
       "jti": "602dd7b3-f6e4-4882-a910-869fb57ccf95",
       "client_id": "fuck",
       "scope": [
           "all"
       ]
   }
   ```


#### 备注

如果你有好的意见或建议，欢迎给我们提issue或pull request。