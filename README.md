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
   ```

## API Endpoints

The API is available under the base path `/api/game`.

All successful responses will be `200 OK` with a JSON body representing the current game state. Error responses (e.g., for invalid moves) will typically be `400 Bad Request` with a JSON body describing the error.

### 1. Start a New Game

Starts or restarts the Tic Tac Toe game, initializing an empty board and setting Player X as the current player.

* **URL:** `/api/game/start`
* **Method:** `POST`
* **Request Body:** None
* **Success Response (`200 OK`):**
    ```json
    {
        "board": [
            [null, null, null],
            [null, null, null],
            [null, null, null]
        ],
        "currentPlayer": "X",
        "gameStatus": "IN_PROGRESS",
        "message": "New game started. Player X's turn."
    }
    ```

### 2. Make a Move

Allows the current player to make a move on the board.

* **URL:** `/api/game/move`
* **Method:** `POST`
* **Request Body:** JSON object specifying the row and column for the move.
    ```json
    {
        "row": 0,
        "col": 0
    }
    ```
    *(Row and column are 0-indexed)*

* **Success Response (`200 OK`):** (Example after Player X moves to (0,0))
    ```json
    {
        "board": [
            ["X", null, null],
            [null, null, null],
            [null, null, null]
        ],
        "currentPlayer": "O",
        "gameStatus": "IN_PROGRESS",
        "message": "Player O's turn."
    }
    ```
    (If the move results in a win or draw, `gameStatus` and `message` will reflect that.)

* **Error Response (`400 Bad Request`):**
    If the move is invalid (e.g., cell occupied, out of bounds, game already over):
    ```json
    {
        "message": "Cell (0,0) is already occupied."
    }
    ```
    (The message will vary based on the specific error.)

### 3. Get Current Game State

Retrieves the current state of the game board, the current player, and the game status.

* **URL:** `/api/game`
* **Method:** `GET`
* **Request Body:** None
* **Success Response (`200 OK`):**
    ```json
    {
        "board": [
            ["X", "O", null],
            [null, "X", null],
            [null, null, "O"]
        ],
        "currentPlayer": "X",
        "gameStatus": "IN_PROGRESS",
        "message": "Player X's turn."
    }
    ```
    (The `board`, `currentPlayer`, `gameStatus`, and `message` will reflect the actual current state of the game.)
     ```
     ## Testing with Postman/Insomnia

To manually test the API endpoints, you can use a tool like Postman or Insomnia.

1.  **Ensure the application is running:**
    Start your Spring Boot Tic Tac Toe application. By default, it will be accessible at `http://localhost:8080`.

2.  **Using Postman/Insomnia:**
    * **Base URL:** The base URL for all requests will be `http://localhost:8080/api/game`.

    * **Start a New Game:**
        * **Method:** `POST`
        * **URL:** `http://localhost:8080/api/game/start`
        * **Body:** None
        * Send the request. You should receive a JSON response with the initial game state.

    * **Make a Move:**
        * **Method:** `POST`
        * **URL:** `http://localhost:8080/api/game/move`
        * **Body:** Select "raw" and "JSON" type. Provide the move details:
            ```json
            {
                "row": 0,
                "col": 0
            }
            ```
            *(Adjust `row` and `col` values as needed for your test.)*
        * Send the request. You should receive the updated game state or an error message if the move is invalid.

    * **Get Current Game State:**
        * **Method:** `GET`
        * **URL:** `http://localhost:8080/api/game`
        * **Body:** None
        * Send the request. You should receive the current game state.

    * **Headers:** For `POST` requests with a JSON body, ensure the `Content-Type` header is set to `application/json`. Most tools do this automatically when you select JSON as the body type.* ```
  
## Design Choices

This section outlines some of the key design choices made during the development of this Tic Tac Toe application:

* **Programming Language & Framework:**
    * **Java:** Chosen as per the requirements.
    * **Spring Boot:** Utilized for its ease of creating production-ready RESTful web services with minimal configuration. Features like Spring Web (for MVC and REST controllers) and auto-configuration simplify development.

* **Test-Driven Development (TDD):**
    * The development process followed a TDD approach. Tests (using JUnit 5) were written *before* the application code for core game logic (`Board.java`), service layer (`GameService.java`), and controller layer (`GameController.java` using `MockMvc`).
    * This ensures that each component behaves as expected and helps in designing more modular and maintainable code.

* **Layered Architecture:**
    The application is structured into distinct layers:
    * **Domain (`domain/`):** Contains the core game logic and entities (e.g., `Board`, `Player`, `GameStatus`). These are plain Java objects (POJOs/Enums) with no Spring-specific dependencies.
    * **DTO (`dto/`):** Data Transfer Objects (`MoveRequest`, `GameResponse`) are used to define the structure of data exchanged via the API, decoupling the API contract from the internal domain model.
    * **Service (`service/`):** The `GameService` acts as an intermediary between the controllers and the domain logic. It orchestrates game operations and contains business logic not directly part of the `Board`'s responsibilities (like handling a single game instance).
    * **Controller (`controller/`):** Spring MVC RestControllers (`GameController`) handle incoming HTTP requests, delegate to the `GameService`, and return appropriate HTTP responses.
    * **Exception (`exception/`):** Custom exceptions (`InvalidMoveException`) and a global exception handler (`GlobalExceptionHandler` with `@ControllerAdvice`) are used for consistent and clear error reporting to API clients.

* **API Design:**
    * **RESTful Principles:** The API attempts to follow RESTful principles, using standard HTTP methods (`GET`, `POST`) and resource-oriented URLs (e.g., `/api/game`).
    * **JSON:** JSON is used as the data format for API requests and responses due to its widespread adoption and ease of use.
    * **Statelessness (Game Instance):** For this exercise, the `GameService` manages a single, stateful game instance on the server. Each API call interacts with this shared instance. For a multi-game scenario, a more complex state management mechanism (e.g., using game IDs and a store) would be required.

* **Immutability & Data Integrity:**
    * While not strictly enforced everywhere for this exercise's scope, the aim was to keep the core game logic in `Board.java` relatively self-contained.
    * The `Board.getCells()` method returns a copy of the board array to prevent direct external modification of the internal state when exposing it via the API response.

* **Error Handling:**
    * Specific exceptions (e.g., `InvalidMoveException`) are thrown by the service layer for known error conditions.
    * A `@ControllerAdvice` globally handles these exceptions and maps them to appropriate HTTP status codes and response bodies, providing a consistent error contract to the client.

* **Build & Dependency Management:**
    * **Maven:** Used for project build and dependency management, as configured by Spring Initializr. The Maven Wrapper (`mvnw`) is included for portability.

* **Readability and Maintainability:**
    * Efforts were made to write clear, well-commented code with meaningful names for classes, methods, and variables.
    * The TDD approach and layered architecture also contribute to maintainability.
