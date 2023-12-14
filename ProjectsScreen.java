package application;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProjectsScreen {

    private ProjectManagerService projectManagerService;

    public ProjectsScreen(ProjectManagerService projectManagerService) {
        this.projectManagerService = projectManagerService;
    }

    public Scene createProjectsScreen(Stage primaryStage) {
        VBox projectsLayout = new VBox(10);  // Adjust the spacing between elements
        projectsLayout.setPadding(new Insets(10));

        // Fetch all projects from the service and display them
        ListView<Project> projectsListView = new ListView<>();
        refreshProjectsListView(projectsListView);

        Button backButton = new Button("Back");

        backButton.setOnAction(e -> goBack(primaryStage));

        HBox viewDetailsBox = new HBox(10);
        TextField projectNameField = new TextField();
        projectNameField.setPromptText("Project Name");
        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.setOnAction(e -> showProjectDetails(projectsListView, projectNameField.getText()));

        viewDetailsBox.getChildren().addAll(projectNameField, viewDetailsButton);

        projectsLayout.getChildren().addAll(projectsListView, viewDetailsBox, backButton);

        return new Scene(projectsLayout, 800, 600);
    }

    // Inside the ProjectsScreen class

    private void refreshProjectsListView(ListView<Project> projectsListView) {
        projectsListView.getItems().clear();

        // Read projects from the file and add them to the list view
        readProjectsFromFile("Projects.txt", projectsListView.getItems());
    }

    private void readProjectsFromFile(String fileName, ObservableList<Project> projects) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Project currentProject = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Project Name: ")) {
                    String projectName = line.substring("Project Name: ".length());
                    currentProject = new Project(projectName, null); // You might want to initialise tasks here
                    projects.add(currentProject);
                } else if (line.startsWith("  - Task Name: ")) {
                    // Assuming the file structure follows the pattern specified in your previous code
                    String taskName = line.substring("  - Task Name: ".length(), line.indexOf(", Duration: "));
                    int taskDuration = Integer.parseInt(line.substring(line.indexOf(", Duration: ") + ", Duration: ".length()));
                    Task newTask = new Task(taskName, taskDuration, null); // You might want to initialise dependencies here
                    currentProject.getTasks().add(newTask);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void updateListViewWithFormattedTasks(ListView<Project> projectsListView) {
        projectsListView.getItems().forEach(project -> {
            project.getTasks().forEach(task -> {
                // Format task details and update the task in the project
                task.setName(formatTaskName(task));
                task.setDuration(formatTaskDuration(task));
                // You might want to format dependencies here if needed
            });
        });
    }

    private String formatTaskName(Task task) {
        return "Task: " + task.getName();
    }

    private int formatTaskDuration(Task task) {
        // Convert duration to int first and then format
        int duration = (int) task.getDuration();
        return duration;
    }

    private void showProjectDetails(ListView<Project> projectsListView, String projectName) {
        projectsListView.getItems().stream()
                .filter(project -> project.getName().equalsIgnoreCase(projectName))
                .findFirst()
                .ifPresent(this::showDetailsAlert);
    }

    private void showDetailsAlert(Project project) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Project Details");
        alert.setHeaderText("Details for Project: " + project.getName());
        StringBuilder contentText = new StringBuilder();
        project.getTasks().forEach(task -> contentText.append(formatTaskName(task)).append(", Duration: ").append(formatTaskDuration(task)).append("\n"));
        alert.setContentText(contentText.toString());
        alert.showAndWait();
    }

    private void goBack(Stage primaryStage) {
        // You may want to implement additional logic here before going back
        ProjectManagerGUI projectManagerGUI = new ProjectManagerGUI(primaryStage);
        primaryStage.setScene(projectManagerGUI.createMainScreen(primaryStage));
    }
}
