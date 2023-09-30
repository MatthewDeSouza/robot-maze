package com.github.matthewdesouza.robotmaze;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;




/**
 * The Main class is the entry point for the JavaFX application.
 * It creates a window containing a TabPane with a single tab that displays a Maze.
 * <p/>
 * TODO: Implement Car racing {@link Tab}.
 *
 * @author Matthew DeSouza
 */
public class Main extends Application {
    // The Maze instance representing the robot maze.
    private Maze robotMaze;

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

        // Create a TabPane and add a Tab with the Maze.
        // TODO: Create carMaze Tab, add it to tabPane.
        TabPane tabPane = new TabPane();
        Tab robotMazeTab = new Tab("Robot Maze", robotMaze);
        robotMazeTab.setClosable(false); // The tab cannot be closed by the user.
        tabPane.getTabs().add(robotMazeTab);

        // Create a VBox to vertically stack the TabPane and the Maze.
        // TODO: Modify VBox to include carMaze.
        VBox root = new VBox(tabPane, robotMaze);

        // Create a Scene with the VBox as the root node.
        Scene scene = new Scene(root, robotMaze.getWidth(), robotMaze.getHeight() + tabPane.getTabMaxHeight());

        // Add an event filter to handle key presses.
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::onKeyPressed);

        // Set up the primary stage.
        primaryStage.setScene(scene);
        primaryStage.setTitle("Robot Maze");
        primaryStage.setResizable(true);
        primaryStage.sizeToScene();
        primaryStage.show();

        // Add the Alt+P key combination listener here
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isAltDown() && event.getCode() == KeyCode.P) {
                animateRobot(); // Start the animation when Alt+P is pressed
            }
        });
    }

    /**
     * create button
     */

    private Button createStartAnimationButton() {
        Button startAnimationButton = new Button("Start Animation (P)");
        startAnimationButton.setMnemonicParsing(true); // Enable mnemonic parsing
        startAnimationButton.setTooltip(new Tooltip("Press Alt+P to start animation"));
        startAnimationButton.setOnAction(event -> animateRobot());
        return startAnimationButton;
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

    /**
     * Nova Alim animate robot method
     */
    private void animateRobot() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE); // or a specific number of cycles if needed

        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), event -> {
            // Calculate the next move for the robot and move it.
            // Stop the animation if the robot reaches the end or cannot move further.
            if (!robotMaze.moveRobotToNextPosition()) {
                timeline.stop();
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

}