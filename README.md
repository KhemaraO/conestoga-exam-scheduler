\# Conestoga Exam Scheduler



A full-stack web application to help Conestoga College students track their courses and manage exam schedules.

## Features

- Course Management (Add, Edit, Delete)

* Exam Scheduling
* Mark exams as completed/upcoming
* Filter exams by status
* Real-time data persistence
* Conestoga College branded design



\## Tech Stack



\*\*Frontend:\*\*

* React 18
* Tailwind CSS
* Axios for API calls



\*\*Backend:\*\*

* Spring Boot 3.2.0
* Java 17
* Spring Data JPA
* PostgreSQL



\*\*Database:\*\*

* PostgreSQL 16



\## Prerequisites



* Java 17 or higher
* Node.js 18+ and npm
* PostgreSQL 14+
* Maven 3.6+



\## Local Setup Instructions



\### 1. Clone the repository

```bash

git clone https://github.com/KhemaraO/conestoga-exam-scheduler.git

cd conestoga-exam-scheduler

```



\### 2. Setup Database

```sql

-- Create database

CREATE DATABASE conestoga\_scheduler;

```

### 3. Configure Backend



Update `backend/src/main/resources/application.properties`;

```properties

spring.datasource.url=jdbc:postgresql://localhost:5432/conestoga\_scheduler

spring.datasource.username=postgres

spring.datasource.password=YOUR\_PASSWORD



\### 4. Run Backend

```bash

cd backend

mvn spring-boot:run

```



Backend will start on `htt\[://localhost:8081`



\### 5. Setup Frontend

```bash

cd frontend

npm install

```



Create `.env` file:

```
REACT\_APP\_API\_BASE\_URL=http://localhost:8081/api

REACT\_APP\_USER\_ID=1
```

### 6. Run Frontend
```bash
npmstart

```



Frontend will start on `http://localhost:3000`



\## Project Structure

```

conestoga-exam-scheduler/

├── backend/                    # Spring Boot Application

│   ├── src/

│   │   ├── main/

│   │   │   ├── java/

│   │   │   │   └── com/conestoga/scheduler/

│   │   │   │       ├── entity/

│   │   │   │       ├── repository/

│   │   │   │       ├── dto/

│   │   │   │       ├── service/

│   │   │   │       └── controller/

│   │   │   └── resources/

│   │   │       └── application.properties

│   └── pom.xml

│

├── frontend/                   # React Application

│   ├── src/

│   │   ├── components/

│   │   │   ├── courses/

│   │   │   └── exams/

│   │   ├── services/

│   │   └── App.js

│   └── package.json

│

└── README.md

```



\## Roadmap



* \[ ] User AUthentication
* \[ ] Calendar View
* \[ ] Email Notifications
* \[ ] Grade Tracker
* \[ ] Mobile Responsive Design
* \[ ] Export to PDF/Calendar



\## Author

Khemara Oeun - Conestoga College Alumni

## License

This project is for educational purposes.

## Acknowledgements



* Conestoga College for inspiration
* Spring Boot \& React communities
