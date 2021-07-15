# TODO APP

REST API to do app created with Spring Boot

## TABLE OF CONTENTS

* [For what](#for-what-?)
* [Technologies](#technologies)
* [How to start](#how-to-start)

## For what ?

This API was created for use with a prepared user interface,    
but if you want check data in JSON

* GET `/api/tasks`

#####Example output:

```
[
    {
        "id": 15,
        "name": "learn history",
        "complete": "NO",
        "date": "2019/01/24, 7:37 PM",
        "listId": 1
    },
    {
        "id": 16,
        "name": "play cyberpunk 2077",
        "complete": "NO",
        "date": "2019/01/24, 7:39 PM",
        "listId": 2
    }
]
```
* GET `/api/lists`

#####Example output:

```
[
    {
        "id": 1,
        "name": "study"
    },
    {
        "id": 2,
        "name": "games"
    }
]
```

## Technologies

* JAVA 11
* Spring Boot 2.5.0
* Lombok 1.18.20  
* Junit5 5.7.2
* AssertJ 3.19.0
* Mockito 3.9.0

## How to start

```
git clone https://github.com/igor-on/ToDo-rest-api.git
```

### After you clone repository

* You need to create the appropriate tables in mySql data base and add credentials in application.properties file

