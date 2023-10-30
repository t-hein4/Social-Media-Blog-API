# Project: Social media blog API

## Overview

When building a full-stack application, we're typically concerned with both a front end, that displays information to the user and takes in input, and a backend, that manages persisted information.

This project will be a backend for a hypothetical social media app, where we manage our users’ accounts as well as any messages that they submit to the application. The application will function as a micro-blogging or messaging app. In our hypothetical application, any user should be able to see all of the messages posted to the site, or they can see the messages posted by a particular user. In either case, we require a backend which is able to deliver the data needed to display this information as well as process actions like logins, registrations, message creations, message updates, and message deletions.

## List of features implemented

### 1: The API can process new User registrations.

User can create a new Account on the endpoint POST localhost:8080/register. The body will contain a representation of a JSON Account without the account id.

- The registration will be successful if and only if the username is not blank, the password is at least 4 characters long, and an Account with that username does not already exist. If all these conditions are met, the response body should contain a JSON of the Account, including its account_id. The response status should be 200 OK, which is the default. The new account should be persisted to the database.
- If the registration is not successful, the response status should be 400. (Client error)

#### API endpoint

/register

- Example Request
  ```shell
    curl --location --request POST 'http://localhost:8080/register' \
    --header 'Content-Type: text/plain' \
    --data '{
        "username": "user",
        "password": "password"
    }'
  ```
- Example Response
  ```shell
    {
      "account_id":3,
      "username":"Test1",
      "password":"password"
    }
  ```

### 2: The API can process User logins.

Users can verify their login on the endpoint POST localhost:8080/login. The request body will contain a JSON representation of an Account, not containing an account_id. In the future, this action may generate a Session token to allow the user to securely use the site.

- The login will be successful if and only if the username and password provided in the request body JSON match a real account existing on the database. If successful, the response body should contain a JSON of the account in the response body, including its account_id. The response status should be 200 OK, which is the default.
- If the login is not successful, the response status should be 401. (Unauthorized)

#### API endpoint

/login

- Example Request
  ```shell
    curl --location 'http://localhost:8080/login' \
    --header 'Content-Type: text/plain' \
    --data '{
        "username": "testuser1",
        "password": "password"
    }'
  ```
- Example Response
  ```shell
    {
      "account_id": 1,
      "username": "testuser1",
      "password": "password"
    }
  ```

### 3: The API can process the creation of new messages.

User can submit a new post on the endpoint POST localhost:8080/messages. The request body will contain a JSON representation of a message, which should be persisted to the database, but will not contain a message_id.

- The creation of the message will be successful if and only if the message_text is not blank, is under 255 characters, and posted_by refers to a real, existing user. If successful, the response body should contain a JSON of the message, including its message_id. The response status should be 200, which is the default. The new message should be persisted to the database.
- If the creation of the message is not successful, the response status should be 400. (Client error)

#### API endpoint

/messages

- Example Request
  ```shell
    curl --location 'http://localhost:8080/messages' \
    --header 'Content-Type: text/plain' \
    --data '{
        "posted_by": 1,
        "message_text": "Testing api"
    }'
  ```
- Example Response
  ```shell
    {
        "message_id": 2,
        "posted_by": 1,
        "message_text": "Testing api",
        "time_posted_epoch": 0
    }
  ```

### 4: The API can retrieve all messages.

User can submit a GET request on the endpoint GET localhost:8080/messages.

- The response body should contain a JSON representation of a list containing all messages retrieved from the database. It is expected for the list to simply be empty if there are no messages. The response status should always be 200, which is the default.

#### API endpoint

/messages

- Example Request
  ```shell
    curl --location 'http://localhost:8080/messages'
  ```
- Example Response
  ```shell
    [
      {
        "message_id":1,
        "posted_by":1,
        "message_text":"test message 1",
        "time_posted_epoch":1669947792
      }
    ]
  ```

### 5: The API can retrieve a message by its ID.

User can submit a GET request on the endpoint GET localhost:8080/messages/{message_id}.

- The response body should contain a JSON representation of the message identified by the message_id. It is expected for the response body to simply be empty if there is no such message. The response status should always be 200, which is the default.

#### API endpoint

/messages/{message_id}

- Example Request
  ```shell
    curl --location 'http://localhost:8080/messages/1'
  ```
- Example Response
  ```shell
    {
      "message_id":1,
      "posted_by":1,
      "message_text":"test message 1",
      "time_posted_epoch":1669947792
    }
  ```

### 6: The API can delete a message identified by a message ID.

User can submit a DELETE request on the endpoint DELETE localhost:8080/messages/{message_id}.

- The deletion of an existing message should remove an existing message from the database. If the message existed, the response body should contain the now-deleted message. The response status should be 200, which is the default.
- If the message did not exist, the response status should be 200, but the response body should be empty. This is because the DELETE verb is intended to be idempotent, ie, multiple calls to the DELETE endpoint should respond with the same type of response.

#### API endpoint

/messages/{message_id}

- Example Request
  ```shell
    curl --location --request DELETE 'http://localhost:8080/messages/2' \
    --header 'Content-Type: text/plain' \
  ```
- Example Response
  ```shell
    {
        "message_id": 2,
        "posted_by": 1,
        "message_text": "Testing api",
        "time_posted_epoch": 0
    }
  ```

### 7: The API can update a message text identified by a message ID.

User can submit a PATCH request on the endpoint PATCH localhost:8080/messages/{message_id}. The request body should contain a new message_text values to replace the message identified by message_id. The request body can not be guaranteed to contain any other information.

- The update of a message should be successful if and only if the message id already exists and the new message_text is not blank and is not over 255 characters. If the update is successful, the response body should contain the full updated message (including message_id, posted_by, message_text, and time_posted_epoch), and the response status should be 200, which is the default. The message existing on the database should have the updated message_text.
- If the update of the message is not successful for any reason, the response status should be 400. (Client error)

#### API endpoint

/messages/{message_id}

- Example Request
  ```shell
    curl --location --request PATCH 'http://localhost:8080/messages/1' \
    --header 'Content-Type: text/plain' \
    --data '{
        "message_text":"test message 1"
    }'
  ```
- Example Response
  ```shell
    {
        "message_id": 1,
        "posted_by": 1,
        "message_text": "test message 1",
        "time_posted_epoch": 1669947792
    }
  ```

### 8: The API can retrieve all messages written by a particular user.

User can submit a GET request on the endpoint GET localhost:8080/accounts/{account_id}/messages.

- The response body should contain a JSON representation of a list containing all messages posted by a particular user, which is retrieved from the database. It is expected for the list to simply be empty if there are no messages. The response status should always be 200, which is the default.

#### API endpoint

/accounts/{id}/messages

- Example Request
  ```shell
    curl --location 'http://localhost:8080/accounts/1/messages'
  ```
- Example Response
  ```shell
    [
      {
        "message_id":1,
        "posted_by":1,
        "message_text":"test message 1",
        "time_posted_epoch":1669947792
      }
    ]
  ```

## List of Technologies used

- Java
- JDBC
- SQL
- Javalin
- Junit
- Mockito

## Getting started

- Clone the project by copying and pasting the following command in the terminal.
  `git@github.com:t-hein4/Social-Media-Blog-API.git`
- Open it in any IDE such as IntelliJ IDEA or Visual Studio Code.
- Start the server by running the 'Main.java' file. (location: src/main/java/)
- You can start calling the APIs from the terminal.

## Contributors

- [kevinchilds](https://github.com/kevinchilds)
- [tedbeast](https://github.com/tedbeast)
- [jhigherevature](https://github.com/jhigherevature)
