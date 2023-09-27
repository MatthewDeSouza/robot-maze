module com.github.matthewdesouza.robotmaze {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.github.matthewdesouza.robotmaze to javafx.fxml;
    exports com.github.matthewdesouza.robotmaze;
}