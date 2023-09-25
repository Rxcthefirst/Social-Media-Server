# Social Media Application Backend

Written in Java with Spring Boot, Spring MVC, Spring Data JPA,  H2 in memory database, and Jacoco. The frontend, made with Angular, is at <https://github.com/Revature-Group-2/Capstone-Project-Frontend>

## Requirements

- JDK 11
- Maven

## HTTP Endpoints

### POST /auth/login

### POST /auth/logout

### POST /auth/register

### GET /auth/restore-session

### GET /chatrooms

### GET /image/{id}

### POST /image

### GET /post

### PUT /post

### PUT /post/comment

### GET /post/{id}

### GET /post/one/{id}

### DELETE /post/{id}

### GET /post//subscribed/{id}

### GET /profile

### GET /profile/{id}

### POST /profile/change-password

### GET /profile/general-information

### POST /profile/general-information

### GET /profile/profile-location

### POST /profile/profile-location

### GET /profile/profile-education

### POST /profile/profile-education

### GET /profile/profile-work

### POST /profile/profile-work

### GET /profile/profile-marital-status

### POST /profile/profile-marital-status

### POST /profile/profile-background

### POST /profile/profile-avatar

### GET /profile/all

### PUT /profile/update-photos

### PATCH /profile/update-photos

### GET /search/profiles

### GET /search/specific-profiles

### PATCH /subscription

### PATCH /subscription/unsubscribe

### POST /vote

### GET /vote/one/{userId}&{postId}

## Websocket Endpoints

### Message /message/{roomName}

### Subscribe /{roomName}

## Frontend

In addition to the above endpoints, URLs will autmatically route to URLS in the angular router.
