
# Cat Adoption Service

Welcome to the Cat Adoption Service! This project aims to provide a platform for cat lovers to both adopt and post cats for adoption.


## Tech Stack
 **Core:** 

Java 17, Spring Boot 3, Spring Security, Spring Data JPA, 
Hibernate, PostgreSQL

**Extra Dependencies:**

Lombok, BCrypt, OpenAPI (Swagger), Java Faker, RabbitMQ





## Features

- User Registration and Login: Users can register with a unique username, email, and password. They can log in using their registered credentials.
- Cat Adoption: Users can browse available cats for adoption. They can view cat details such as name, age, breed, and description. If interested in adoption, they can contact the user who added the cat via mobile number provided by post creator.
- Create New Cat Post: Authenticated users can create new cat posts for adoption. They need to provide cat details like name, age, breed, image, and description.
- Update User Profile: Users can update their profile information.
- Delete User Account: Authenticated users can delete their account. Deleting an account will remove all associated cat posts.
- Comments: Users are able to comment on other users profiles.
- Messages: Users can contact other users through message system 


## API Documentation

You can find all available endpoints using Swagger. After running the application, access the Swagger UI at http://localhost:8080/swagger-ui.html

**Post Controller**

```bash
    /api/posts/add - Add a new post. User most be logged in.
```
```bash
    /api/posts/all - Fetch all posts from the database
```
```bash
    /api/posts/by-user/{userId} - Fetch posts that belong to particular user
```
```bash
    /api/posts/by-post-id - Fetch post by it's post id
```
```bash
    /api/posts/delete/{postId} - Delete post by it's post id
```
```bash
    /api/posts/update/{postId} - Update post by it's post id
```

**User Controller**

```bash
    /api/user/login - Login to the application
```
```bash
    /api/user/register - Register new account
```
```bash
    /api/user/session - Returns information about active user session
```
```bash
    /api/user/all-registered - Returns all registerd users
```
```bash
    /api/user/details/{userId} - Fetch details of given user
```
```bash
    /api/user/update-details - Update certain details for currently active user
```
```bash
    /api/user/update-password - Update password for currently logged in user
```
```bash
    /api/user/delete - Delete an user. User must be authorized first to delete the account.
```

**Comments Controller**
```bash
    /api/comments/add/{userId} - Add comment for a user
```
```bash
    /api/comments/find-all- Find all comments for user in a database
```
```bash
    /api/comments/remove/{commentId} - Remove comment with particular id
```
```bash
    /api/comments/edit/{commentId} - Edit comment with particular id
```
**MessageController** 
```bash
    /api/messages/send/{userId} - Send message to an user
```
```bash
    /api/messages/received-all/{userId} - Find all received messages for user
```
```bash
    /api/messages/received-from/{senderId} - Find all received messages sent from particular user
```




## How to run

**Using Docker**

Clone the project to your local machine

```bash
  git clone https://github.com/mich-wierzch/CatAdoption.git
```

Build a docker container using:

```bash
  docker-compose up
```

Access the Swagger at:

```bash
https://localhost:8080/swagger-ui/index.html
```
Access RabbitMQ management system at:

```bash
https://localhost:15672/
```
**Currently this app only contains the backend - refer to API Documentation above to
see the available endpoints.**


**It is advised to use docker to run the application, otherwise it would require the user to manually set up a PostgreSQL database and RabbitMQ server.**

**If using Docker database will automatically populate with 3 users and 90 randomly generated posts and comments with the provided TestDataLoader class.**



## Roadmap

- Next.js frontend (work in progress ETA 09.2023).

- Add more functions


## Authors

- [@mich-wierzch](https://www.github.com/mich-wierzch)


## FAQ

#### Do I need to use docker to run the app?

Yes, currently it is the best and easiest way to set up the whole app properly with necessary dependecies.



