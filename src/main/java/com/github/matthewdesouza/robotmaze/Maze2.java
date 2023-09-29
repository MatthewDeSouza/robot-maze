package com.github.matthewdesouza.robotmaze;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Maze2 extends Pane{
    private static final int INITIAL_X = 10;
    private static final int INITIAL_Y = 260;

    // Car image dimensions
    private static final int CAR_X = 5;
    private static final int CAR_Y = 5;

    // Pixel move value
    private static final int PIXL_STEP = 5;

    // Image representing maze2.
    private final Image maze2Image;

    // ImageView representing the car.
    private final ImageView carView;

    // Current positions of the car within the maze2 .
    private int carX;
    private int carY;
    public Maze2(String maze2Path, String carPath) {
        // Load and display the maze image.
        maze2Image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(maze2Path)));
        ImageView maze2View = new ImageView(maze2Image);
        getChildren().add(maze2View);

        // Load and display the robot image at its initial position.
        Image carImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(carPath)));
        carView = new ImageView(carImage);
        carX = INITIAL_X;
        carY = INITIAL_Y;
        carView.setX(carX);
        carView.setY(carY);
        getChildren().add(carView);
    }

    /**
     * Moves the car within the maze.
     *
     * @param cx the amount to move in the x direction.
     * @param cy the amount to move in the y direction.
     */
    public void moveCar(int cx, int cy) {
        // Calculate the step size for the movement.
        int stepX = cx != 0 ? (int) Math.signum(cx) * PIXL_STEP : 0;
        int stepY = cy != 0 ? (int) Math.signum(cy) * PIXL_STEP : 0;

        // Calculate the number of steps to move.
        int steps = Math.max(1, Math.max(Math.abs(cx) / PIXL_STEP, Math.abs(cy) / PIXL_STEP));

        // Move the robot step by step, stopping if an invalid move is detected.
        for (int i = 0; i < steps; i++) {
            int newX = carX + stepX;
            int newY = carY + stepY;

            if (isValidMove(newX, newY)) {
                carX = newX;
                carY = newY;
                carView.setX(carX);
                carView.setY(carY);
                continue;
            }

            break;
        }
    }

    /**
     * Resets the robot to its initial position within the maze.
     */
    public void resetCar() {
        carX = INITIAL_X;
        carY = INITIAL_Y;
        carView.setX(INITIAL_X);
        carView.setY(INITIAL_Y);
    }

    /**
     * Determines whether the proposed move is valid.
     *
     * @param x the x-coordinate of the proposed new position.
     * @param y the y-coordinate of the proposed new position.
     * @return true if the move is valid, false otherwise.
     */
    private boolean isValidMove(int x, int y) {
        PixelReader pixelReader = maze2Image.getPixelReader();

        // Check each pixel in the robot picture's area that the robot will occupy after the move.
        for (int i = 0; i < CAR_X; i++) {
            for (int j = 0; j < CAR_Y; j++) {
                int pixelX = x + i;
                int pixelY = y + j;

                // If the pixel is outside the maze or is not white, the move is invalid.
                if (pixelX < 0 || pixelX >= maze2Image.getWidth() || pixelY < 0 || pixelY >= maze2Image.getHeight() ||
                        !pixelReader.getColor(pixelX, pixelY).equals(Color.WHITE)) {
                    return false;
                }
            }
        }

        // If all the pixels in the 25x25 area are white, the move is valid.
        return true;
    }
}

