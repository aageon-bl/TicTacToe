# KATA/TicTacToe

A simple Tic Tac Toe game implemented with Java and Spring Boot, following a TDD approach.

## Prerequisites

* Java JDK 17 or higher
* Apache Maven 3.6+ (or use the bundled Maven Wrapper `./mvnw`)
* Git

## How to Compile and Run

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/aageon-bl/TicTacToe.git
    cd ./TicTacToe
    ```

2.  **Build the project:**
    Using Maven Wrapper (recommended):
    ```bash
    ./mvnw clean install
    ```
    Or, if you have Maven installed globally:
    ```bash
    mvn clean install
    ```

3.  **Run the application:**
    ```bash
    java -jar target/tictactoe-0.0.1-SNAPSHOT.jar
    ```
    The application will start on `http://localhost:8080` by default.

## How to Run Tests

Using Maven Wrapper:
```bash
./mvnw test
