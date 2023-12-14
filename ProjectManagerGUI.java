package application;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProjectManagerGUI {

    private ProjectManagerService projectManagerService;
    private ProjectsScreen projectsScreen;
    private NewProjectScreen newProjectScreen;
    private Stage primaryStage;
    private AdjacencyListScreen adjacencyListScreen;

    public ProjectManagerGUI(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.projectManagerService = new ProjectManagerService();
        this.projectsScreen = new ProjectsScreen(projectManagerService);
        this.adjacencyListScreen = new AdjacencyListScreen(projectManagerService);
        this.newProjectScreen = new NewProjectScreen(projectManagerService);
    }

    public Scene createMainScreen(Stage primaryStage) {
        BorderPane mainLayout = new BorderPane();

        // Set background image at the top

        // Create buttons for navigation
        Button viewProjectsButton = new Button("View Projects");
        viewProjectsButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.8), 10, 0, 0, 0)");

        viewProjectsButton.setOnAction(e -> showProjectsScreen(primaryStage));

        Button newProjectButton = new Button("New Project");
        newProjectButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.8), 10, 0, 0, 0)");

        newProjectButton.setOnAction(e -> showNewProjectScreen(primaryStage));

        Button viewAdjacencyListButton = new Button("View Adjacency List");
        viewAdjacencyListButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.8), 10, 0, 0, 0)");

        viewAdjacencyListButton.setOnAction(e -> showAdjacencyListScreen(primaryStage));

        
        // Add buttons to the layout
        VBox buttonsLayout = new VBox(10);  // Adjust the spacing between buttons
        buttonsLayout.setAlignment(Pos.CENTER);
        buttonsLayout.getChildren().addAll(viewProjectsButton, newProjectButton, viewAdjacencyListButton);


        mainLayout.setCenter(buttonsLayout);

        return new Scene(mainLayout, 800, 600);
    }

    private void showProjectsScreen(Stage primaryStage) {
        Scene projectsScene = projectsScreen.createProjectsScreen(primaryStage);
        primaryStage.setScene(projectsScene);
    }

    private void showNewProjectScreen(Stage primaryStage) {
        Scene newProjectScene = newProjectScreen.createNewProjectScreen(primaryStage);
        primaryStage.setScene(newProjectScene);
    }

    private void showProjectsScreen() {
        Scene projectsScene = projectsScreen.createProjectsScreen(primaryStage);
        primaryStage.setScene(projectsScene);
    }

    private void showNewProjectScreen() {
        Scene newProjectScene = newProjectScreen.createNewProjectScreen(primaryStage);
        primaryStage.setScene(newProjectScene);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showAdjacencyListScreen(Stage primaryStage) {
        int[][] adjacencyMatrix = projectManagerService.getAdjacencyMatrix(); // Replace with your logic to get the adjacency matrix
        Scene adjacencyListScene = adjacencyListScreen.createAdjacencyListScreen(primaryStage);
        primaryStage.setScene(adjacencyListScene);
    }
}
