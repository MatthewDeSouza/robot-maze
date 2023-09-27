# Robot Maze

`robot-maze` is a JavaFX application that simulates a robot navigating through a maze. The application loads a maze and a robot image, and the user can control the robot's movement through the maze using keyboard arrow keys. The robot cannot pass through the walls of the maze, and its movement is restricted to valid paths only.

## Features
- Maze and Robot visualization using JavaFX.
- Keyboard controls to navigate the robot through the maze.
- Collision detection to prevent the robot from passing through the maze walls.
- Ability to reset the robot to its initial position.

## Getting Started

### Prerequisites
- Java 8 or later.
- JavaFX SDK.

### Running the Application
1. Clone the repository:
   ```sh
   git clone https://github.com/MatthewDeSouza/robot-maze.git
   ```

2. Navigate to the project directory:
   ```sh
   cd robot-maze
   ```

3. Compile and run the application:
   ```sh
   javac -cp path-to-javafx-sdk/lib/* --add-modules javafx.controls,javafx.fxml -d out src/com/github/matthewdesouza/robotmaze/*.java
   java -cp out:path-to-javafx-sdk/lib/* --add-modules javafx.controls,javafx.fxml com.github.matthewdesouza.robotmaze.Main
   ```
   Replace `path-to-javafx-sdk` with the path to your JavaFX SDK.

## Usage
- Use the arrow keys or `W`, `A`, `S`, `D` keys to move the robot up, left, down, and right respectively.
- Press `R` to reset the robot to its initial position.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements
- [JavaFX](https://openjfx.io/) for providing the graphical framework used in this project.
