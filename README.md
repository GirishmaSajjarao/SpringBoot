# SpringBoot
# BFH Java Project – Automated SQL Submission

## 📌 Overview
This Spring Boot application:
1. Calls the **generateWebhook** API to get a `webhook` and `accessToken`.
2. Chooses the assigned SQL question based on the registration number.
   - Reg No ends with odd → Question 1 (used here).
3. Submits the SQL query to the **testWebhook** API using the JWT token.

## ⚙️ Tech Stack
- Java 17
- Spring Boot 3.x
- Maven
- RestTemplate for HTTP calls

bfhjava/
 ├── pom.xml
 ├── README.md
 ├── src/
 │   ├── main/
 │   │   ├── java/
 │   │   │   └── com/
 │   │   │       └── girishma22bce8271/
 │   │   │           └── bfhjava/
 │   │   │               ├── BfhjavaApplication.java
 │   │   │               ├── config/
 │   │   │               │   └── RestConfig.java
 │   │   │               ├── dto/
 │   │   │               │   ├── GenerateWebhookRequest.java
 │   │   │               │   ├── GenerateWebhookResponse.java
 │   │   │               │   └── SubmitQueryRequest.java
 │   │   │               └── runner/
 │   │   │                   └── StartupRunner.java
 │   │   └── resources/
 │   │       └── application.properties
 └── target/
     └── bfhjava-0.0.1-SNAPSHOT.jar   (after you run `mvn clean package`)
