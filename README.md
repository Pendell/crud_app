# crud_app
This is the beginnings of a search engine/web crawler (also a (very) basic CRUD application) using java, maven, spring boot, and docker.

Currently, the application will prompt the user for a stock symbol and try to grab the most recent price from google finance's website. In future updates, I would like to put the results into the db to minimize hits to the google finance website (please don't block me).

## Overview
A small project to get some exposure to Docker, Spring+Maven, and working RESTfully. Starting the docker containers will spin up a website on localhost:8080 where the user can input some text. The text is merely echoed back in another page, but next step is to get the application actually crawlin'!

## Running in Docker
Simply "docker compose up" in this folder. This will pull the postgres image, and build the web application image, and start both containers.

The postgres-container starts first. It runs a 'CreateDB.sql' script to get the initial 'users' table that the web app will interact with. The postgres-container exposes the port 5432:5432 on localhost

The web-app-container will begin booting once the postgres-container passes it's healthcheck (see compose.yaml). The web-app-container exposes port 8080:8080. This container links the postgres-container to itself under the hostname database. As such, this container will try to connect to "database:5432"

## Interaction
Navigate to localhost:8080 to visit the home index.

There's no 'front-end' quite yet, so to interact with the db you'll have to generate your own requests.

### C is for creating
The DB only has one table, 'users'. To add a user, send a POST request to "localhost:8080/create" with {"name": "user1"} as the body of the json request. The json given here will add a user with the name "user1" to database. If this is the first entry, the 'id' field will be 1.

### R is for reading
Visit "localhost:8080/users" to GET a (not-so-pretty) list of all the users in the current db.

### U is for updating
Get the user id from "localhost:8080/users". Send a PUT request with the name in the json body to "localhost:8080/users/{id}" to update user entries. For example, if 'user1' was created using the methods above, sending a PUT request with a body of {"name": "John"} to "localhost:8080/users/1" will change the name 'user1' to 'John'.

### D is for deleting
Get the user id from "localhost:8080/users". Send a DELETE  "localhost:8080/delete/{id}" to delete the entry associated with key 'id'.w