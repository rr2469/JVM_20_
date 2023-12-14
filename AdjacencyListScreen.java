package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdjacencyListScreen {

    private ProjectManagerService projectManagerService;
    private Stage primaryStage;

    public AdjacencyListScreen(ProjectManagerService projectManagerService) {
        this.projectManagerService = projectManagerService;
    }

    public Scene createAdjacencyListScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;

        BorderPane adjacencyLayout = new BorderPane();

        // Display the dummy adjacency matrix
        int[][] dummyMatrix = {
                {0, 1, 1},
                {1, 0, 0},
                {1, 0, 0}
        };

        GridPane matrixGrid = new GridPane();
        for (int i = 0; i < dummyMatrix.length; i++) {
            for (int j = 0; j < dummyMatrix[i].length; j++) {
                Label cell = createMatrixLabel(dummyMatrix[i][j]);
                matrixGrid.add(cell, j, i);
            }
        }

        // Create a button to go back to the main screen
        Button backButton = new Button("Back to Main Screen");
        backButton.setOnAction(e -> showMainScreen(primaryStage));

        // Add components to the layout
        VBox adjacencyVBox = new VBox(20); // Increased spacing
        adjacencyVBox.setAlignment(Pos.CENTER);
        adjacencyVBox.getChildren().addAll(createHeaderLabel("Adjacency Matrix"), matrixGrid, backButton);

        adjacencyLayout.setCenter(adjacencyVBox);

        return new Scene(adjacencyLayout, 800, 600);
    }

    private Label createHeaderLabel(String text) {
        Label headerLabel = new Label(text);
        headerLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        return headerLabel;
    }

    private Label createMatrixLabel(int value) {
        Label cell = new Label(String.valueOf(value));
        cell.setStyle("-fx-font-size: 16; -fx-min-width: 30; -fx-min-height: 30; -fx-background-color: #f0f0f0; -fx-border-color: #cccccc;");
        cell.setAlignment(Pos.CENTER);
        return cell;
    }

    private void showMainScreen(Stage primaryStage) {
        ProjectManagerGUI projectManagerGUI = new ProjectManagerGUI(primaryStage);
        Scene mainScene = projectManagerGUI.createMainScreen(primaryStage);
        primaryStage.setScene(mainScene);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
