# Teachly

Teachly is an educational platform that allows educators to create interactive lessons by combining games and exercises with custom triggers and rewards. The platform is designed to gamify the learning experience, making education more engaging and effective.

## Features

- **Interactive Lessons**: Create and manage lessons with detailed explanations and descriptions
- **Custom Exercises**: Build exercises that students can complete as part of lessons
- **Trigger System**: Define custom events that trigger the execution of exercises
- **Reward System**: Create rewards that students can earn by completing exercises correctly or incorrectly
- **Tag Organization**: Organize content with tags for easy discovery
- **User Authentication**: Secure login with Google OAuth2

## Loaders
Teachly allows to export lessons to a script-file that can be executed by the game engine thanks special Teachly-loaders. Following loaders are currently available:
- [Teachly-Minecraft](https://github.com/LoreSchaeffer/Teachly-Minecraft) by LoreSchaeffer

## Technology Stack

- **Backend**: Java with Spring Boot
- **Frontend**: HTML, CSS, JavaScript with Thymeleaf templating
- **Database**: JPA/Hibernate for data persistence (tested with PostgreSQL)
- **Authentication**: OAuth2 for secure user authentication
- **UI Framework**: AdminLTE for responsive design

<br><br>

## Self-Hosting and Deployment

### Prerequisites

- Java 21 or higher
- Maven
- A Google OAuth2 client ID (for authentication)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/BisUmTo/Teachly.git
   cd Teachly
   ```
2. Build the project with Maven:
   
   ```bash
   ./mvnw clean install
    ```
3. Configure OAuth2 credentials in `application.properties`
4. Run the application:
   
   ```bash
   ./mvnw spring-boot:run
    ```
5. Access the application at [http://localhost:8080](http://localhost:8080)
## Backend Project Structure
- `src/main/java/net/delugan/teachly/` - Java source code
  - `lesson/` - Lesson-related components
  - `exercise/` - Exercise-related components
  - `trigger/` - Trigger system components
  - `reward/` - Reward system components
  - `user/` - User management and authentication
  - `utils/` - Utility classes

## API Documentation
The REST API provides endpoints for managing lessons, exercises, triggers, and rewards. API documentation is available at `/api` when the application is running.

## Contributing
Contributions are welcome! Please feel free to submit a Pull Request or open an Issue.

## Main Libraries
- Spring Boot
- Thymeleaf
- AdminLTE
- Blockly - Used for visual programming components