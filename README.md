# Maya 2.0 GUI
This project is created as a part of the assignment project under the UM WIX1002 Fundamentals of Programming class.

## Group Members
Yau Wei Han U2102749  
Chung Qi Lin U2102742  
Lim Zheng Yu U2102809  
Cheong Jin Xuan U2102735  
Hoong Dao Jing U2102744  

## Prerequisite
- Maven
- Java 17
- MySQL server that hosts the maya database. You can get the database from here: [Maya Database](https://drive.google.com/file/d/1NJU9NijX_fX2PqSk5GEr0R0cEker9cnx/view?usp=sharing)

## Getting Started
Clone the project or download the .zip file of the branch.  
Change the username and password on line 13 of the DBConnetor class to match the password of your MySQL server.
```
this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maya","<your username here>","<your password here>");
```

## Installing
Install all the dependencies by doing
```
mvn dependency:resolve
```

## Running
To run this project, do
```
mvn clean javafx:run
```

## Built With
* [JavaFX](https://openjfx.io/) - For the GUI part of the project
* [Maven](https://maven.apache.org/) - Software Project Management tool
* [MySQL JDBC Driver Connector](https://dev.mysql.com/downloads/connector/j/) - For connecting with the MySQL server
* [MySQL](https://www.mysql.com/) - MySQL database
