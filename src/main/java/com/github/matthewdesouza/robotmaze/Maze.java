package com.github.matthewdesouza.robotmaze;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Objects;

/**
 * The Maze class is a graphical representation of a maze using JavaFX.
 * It handles the movement and positioning of a robot within the maze.
 *
 * @author Matthew DeSouza
 */
public class Maze extends Pane {

    // Initial positions of the robot within the maze.
    private static final int INITIAL_X_POS = 16;
    private static final int INITIAL_Y_POS = 260;

    // Robot image's dimensions
    private static final int ROBOT_X = 25;
    private static final int ROBOT_Y = 25;

    // Pixel step value
    private static final int PIXEL_STEP = 5;

    // Image representing the maze.
    private final Image mazeImage;

    // ImageView representing the robot.
    private final ImageView robotView;

    // Current positions of the robot within the maze.
    private int robotX;
    private int robotY;

    /**
     * Constructs a Maze object and initializes its graphical components.
     *
     * @param mazePath  the path to the maze image resource.
     * @param robotPath the path to the robot image resource.
     */
    public Maze(String mazePath, String robotPath) {
        // Load and display the maze image.
        mazeImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(mazePath)));
        ImageView mazeView = new ImageView(mazeImage);
        getChildren().add(mazeView);

        // Load and display the robot image at its initial position.
        Image robotImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(robotPath)));
        robotView = new ImageView(robotImage);
        robotX = INITIAL_X_POS;
        robotY = INITIAL_Y_POS;
        robotView.setX(robotX);
        robotView.setY(robotY);
        getChildren().add(robotView);
    }

    /**
     * Moves the robot within the maze.
     *
     * @param dx the amount to move in the x direction.
     * @param dy the amount to move in the y direction.
     */
    public void moveRobot(int dx, int dy) {
        // Calculate the step size for the movement.
        int stepX = dx != 0 ? (int) Math.signum(dx) * PIXEL_STEP : 0;
        int stepY = dy != 0 ? (int) Math.signum(dy) * PIXEL_STEP : 0;

        // Calculate the number of steps to move.
        int steps = Math.max(1, Math.max(Math.abs(dx) / PIXEL_STEP, Math.abs(dy) / PIXEL_STEP));

        // Move the robot step by step, stopping if an invalid move is detected.
        for (int i = 0; i < steps; i++) {
            int newX = robotX + stepX;
            int newY = robotY + stepY;

            if (isValidMove(newX, newY)) {
                robotX = newX;
                robotY = newY;
                robotView.setX(robotX);
                robotView.setY(robotY);
                continue;
            }

            break;
        }
    }

    /**
     * Resets the robot to its initial position within the maze.
     */
    public void resetRobot() {
        robotX = INITIAL_X_POS;
        robotY = INITIAL_Y_POS;
        robotView.setX(INITIAL_X_POS);
        robotView.setY(INITIAL_Y_POS);
    }

    /**
     * Determines whether the proposed move is valid.
     *
     * @param x the x-coordinate of the proposed new position.
     * @param y the y-coordinate of the proposed new position.
     * @return true if the move is valid, false otherwise.
     */
    private boolean isValidMove(int x, int y) {
        PixelReader pixelReader = mazeImage.getPixelReader();

        // Check each pixel in the robot picture's area that the robot will occupy after the move.
        for (int i = 0; i < ROBOT_X; i++) {
            for (int j = 0; j < ROBOT_Y; j++) {
                int pixelX = x + i;
                int pixelY = y + j;

                // If the pixel is outside the maze or is not white, the move is invalid.
                if (pixelX < 0 || pixelX >= mazeImage.getWidth() || pixelY < 0 || pixelY >= mazeImage.getHeight() ||
                        !pixelReader.getColor(pixelX, pixelY).equals(Color.WHITE)) {
                    return false;
                }
            }
        }

        // If all the pixels in the 25x25 area are white, the move is valid.
        return true;
    }
}
