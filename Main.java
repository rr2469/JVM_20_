package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ProjectManagerGUI projectManagerGUI = new ProjectManagerGUI(primaryStage);
        Scene scene = projectManagerGUI.createMainScreen(primaryStage);
        primaryStage.setTitle("Project Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
