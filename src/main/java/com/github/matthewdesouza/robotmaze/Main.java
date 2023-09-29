package com.github.matthewdesouza.robotmaze;

//import com.sun.javafx.stage.EmbeddedWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * The Main class is the entry point for the JavaFX application.
 * It creates a window containing a TabPane with a two tabs that displays a robot maze, and car maze.
 * <p/>
 *
 * @author Matthew DeSouza
 */
public class Main extends Application {
    // The Maze instance representing the robot maze and car maze.
    private Maze robotMaze;
    private Maze2 carMaze;

    /**
     * The main method to launch the application.
     *
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method is the main entry point for all JavaFX applications.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        // Initialize the Maze with the specified images.
        robotMaze = new Maze("maze.png", "robot.png");
        carMaze = new Maze2("maze2.png","car.png");

        // Create a TabPane and add a Tab with the robot Maze.
        TabPane tabPane = new TabPane();
        Tab robotMazeTab = new Tab("Robot Maze", robotMaze);
        robotMazeTab.setClosable(false); // The tab cannot be closed by the user.
        tabPane.getTabs().add(robotMazeTab);

        // Create car Maze Tab, add it to tabPane.
        Tab carMazeTab = new Tab("Car Maze", carMaze);
        carMazeTab.setClosable(false); // The tab cannot be closed by the user.
        tabPane.getTabs().add(carMazeTab);

        // Create a VBox to vertically stack the TabPane and the Maze.
        VBox root = new VBox(tabPane, robotMaze);
        VBox root2 = new VBox(tabPane, carMaze);

        // Create a Scene with the VBox as the root node.
        Scene scene = new Scene(root, robotMaze.getWidth(), robotMaze.getHeight() + tabPane.getTabMaxHeight());
        // Add an event filter to handle key presses.
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::onKeyPressed);

        //Draw car
        Rectangle rect = new Rectangle();
        Circle frontWheel = new Circle();
        Circle rearWheel = new Circle();
        rect.setFill(Color.LIGHTPINK);
        frontWheel.setFill(Color.BLACK);
        rearWheel.setFill(Color.BLACK);

        // Set up the primary stage.
        primaryStage.setScene(scene);
        primaryStage.setTitle("Robot Maze");
        primaryStage.setResizable(true);
        primaryStage.sizeToScene();
        primaryStage.show();

        // Create a Scene with the VBox as the root node.
        Scene scene2 = new Scene(root2, carMaze.getWidth(), carMaze.getHeight() + tabPane.getTabMaxHeight());
        // Add an event filter to handle key presses.
        scene2.addEventFilter(KeyEvent.KEY_PRESSED, this::onKeyPressd);

        // set up car maze stage
        Stage stage = new Stage();
        stage.setScene(scene2);
        stage.setTitle("CAR Maze");
        stage.setResizable(true);
        stage.sizeToScene();
        stage.show();
    }

    /**
     * Handles key press events to move the robot in the maze.
     *
     * @param event the key event.
     */
    private void onKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
            case W:
                robotMaze.moveRobot(0, -1); // Move up
                break;
            case DOWN:
            case S:
                robotMaze.moveRobot(0, 1); // Move down
                break;
            case LEFT:
            case A:
                robotMaze.moveRobot(-1, 0); // Move left
                break;
            case RIGHT:
            case D:
                robotMaze.moveRobot(1, 0); // Move right
                break;
            case R:
                robotMaze.resetRobot(); // Reset the robot position
                break;
        }
    }
    private void onKeyPressd(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
            case W:
                carMaze.moveCar(0, -1); // Move up
                break;
            case DOWN:
            case S:
                carMaze.moveCar(0, 1); // Move down
                break;
            case LEFT:
            case A:
                carMaze.moveCar(-1, 0); // Move left
                break;
            case RIGHT:
            case D:
                carMaze.moveCar(1, 0); // Move right
                break;
            case R:
                carMaze.resetCar(); // Reset the car position
                break;
        }
    }
}
