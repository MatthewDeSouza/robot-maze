package com.github.matthewdesouza.robotmaze;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.image.PixelReader;

import java.util.Objects;

public class Maze extends Pane {
    private static final int INITIAL_X_POS = 16;
    private static final int INITIAL_Y_POS = 260;
    private static final int ROBOT_X = 25;
    private static final int ROBOT_Y = 25;
    private static final int PIXEL_STEP = 5;

    private final Image mazeImage;
    private final ImageView robotView;
    private int robotX;
    private int robotY;

    public Maze(String mazePath, String robotPath) {
        mazeImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(mazePath)));
        ImageView mazeView = new ImageView(mazeImage);
        getChildren().add(mazeView);

        Image robotImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(robotPath)));
        robotView = new ImageView(robotImage);
        robotX = INITIAL_X_POS;
        robotY = INITIAL_Y_POS;
        robotView.setX(robotX);
        robotView.setY(robotY);
        getChildren().add(robotView);
    }

    public void moveRobot(int dx, int dy) {
        int stepX = dx != 0 ? (int) Math.signum(dx) * PIXEL_STEP : 0;
        int stepY = dy != 0 ? (int) Math.signum(dy) * PIXEL_STEP : 0;
        int steps = Math.max(1, Math.max(Math.abs(dx) / PIXEL_STEP, Math.abs(dy) / PIXEL_STEP));

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

    public void resetRobot() {
        robotX = INITIAL_X_POS;
        robotY = INITIAL_Y_POS;
        robotView.setX(INITIAL_X_POS);
        robotView.setY(INITIAL_Y_POS);
    }

    public boolean moveRobotToNextPosition() {
        // Calculate the next position based on your logic.
        int nextX = robotX + PIXEL_STEP;
        int nextY = robotY;

        // Check if the move is valid.
        if (isValidMove(nextX, nextY)) {
            robotX = nextX;
            robotY = nextY;
            robotView.setX(robotX);
            robotView.setY(robotY);
            return true; // The robot can move.
        } else {
            return false; // The robot cannot move further.
        }
    }

    private boolean isValidMove(int x, int y) {
        PixelReader pixelReader = mazeImage.getPixelReader();

        for (int i = 0; i < ROBOT_X; i++) {
            for (int j = 0; j < ROBOT_Y; j++) {
                int pixelX = x + i;
                int pixelY = y + j;

                if (pixelX < 0 || pixelX >= mazeImage.getWidth() || pixelY < 0 || pixelY >= mazeImage.getHeight() ||
                        !pixelReader.getColor(pixelX, pixelY).equals(Color.WHITE)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void startRobotAnimation() {
        Timeline timeline = new Timeline(); // Initialize the timeline

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(100), event -> {
                    // Calculate the next move for the robot and move it.
                    // Stop the animation if the robot reaches the end or cannot move further.
                    if (!moveRobotToNextPosition()) {
                        timeline.stop();
                    }
                })
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
