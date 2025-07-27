# Travel Smart Guide 🚀

**Travel Smart Guide** is a Java-based application designed to help users explore and plan trips smartly using a local database connection. It offers core functionalities like user management and connectivity with MySQL for efficient data handling.

## 📌 Project Features

- User registration and login system
- MySQL database connectivity via JDBC
- DAO (Data Access Object) design pattern for database operations
- Modular and scalable Java code structure
- Clean separation of logic with packages: `db`, `dao`, `model`

## 🛠️ Technologies Used

- **Java (Core Java)**
- **MySQL** (Database)
- **JDBC** (MySQL Connector/J)
- **Eclipse IDE** (Recommended for development)
- **Git & GitHub** (Version control)

## 📂 Folder Structure

TravelSmartGuide/
│
├── db/
│ └── DBConnection.java # Handles MySQL connection setup
│
├── dao/
│ └── UserDAO.java # Performs DB operations (Insert, Read, etc.)
│
├── model/
│ └── User.java # User model class
│
├── lib/
│ ├── mysql-connector-j-9.4.0.jar # MySQL JDBC driver
│ ├── src.zip # Source files of the connector
│ └── ... (other connector files)
│
├── Main.java # Entry point of the application
├── README.md # Project description and setup guide

