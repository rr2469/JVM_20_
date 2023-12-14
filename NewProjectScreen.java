package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewProjectScreen {

    private ProjectManagerService projectManagerService;

    public NewProjectScreen(ProjectManagerService projectManagerService) {
        this.projectManagerService = projectManagerService;
    }

    public Scene createNewProjectScreen(Stage primaryStage) {
        VBox newProjectLayout = new VBox();

        // Input fields for creating a new project
        TextField projectNameField = new TextField("Enter Project Name");
        Button createProjectButton = new Button("Create Project");
        Button backButton = new Button("Back");

        // Task input fields
        TextField taskNameField = new TextField("Enter Task Name");
        TextField taskDurationField = new TextField("Enter Task Duration");

        Button addTaskButton = new Button("Add Task");

        // List to store tasks
        ObservableList<Task> taskList = FXCollections.observableArrayList();

        ListView<Task> taskListView = new ListView<>(taskList);

        addTaskButton.setOnAction(e -> {
            String taskName = taskNameField.getText();
            int taskDuration = Integer.parseInt(taskDurationField.getText());

            Task newTask = new Task(taskName, taskDuration, null); // You might want to initialize dependencies here
            taskList.add(newTask);

            // Clear input fields
            taskNameField.clear();
            taskDurationField.clear();
        });

        createProjectButton.setOnAction(e -> {
            String projectName = projectNameField.getText();
            List<Task> taskListCopy = new ArrayList<>(taskList); // Create a copy to avoid potential side effects
            Project newProject = new Project(projectName, taskListCopy);
            saveProjectsToFile("Projects.txt", projectManagerService.getAllProjects()); // Save all projects
            showAlert("Project created successfully!");
        });

        backButton.setOnAction(e -> goBack(primaryStage));

        newProjectLayout.getChildren().addAll(projectNameField, taskNameField, taskDurationField, addTaskButton, taskListView, createProjectButton, backButton);

        return new Scene(newProjectLayout, 400, 500);
    }


    private void showAlert(String string) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(string);
        alert.showAndWait();
    }

    private void goBack(Stage primaryStage) {
        // You may want to implement additional logic here before going back
        ProjectManagerGUI projectManagerGUI = new ProjectManagerGUI(primaryStage);
        primaryStage.setScene(projectManagerGUI.createMainScreen(primaryStage));
    }

    private void saveProjectsToFile(String fileName, List<Project> projects) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            for (Project project : projects) {
                writer.write("Project Name: " + project.getName());
                writer.newLine();
                writer.write("Tasks:");
                writer.newLine();
                for (Task task : project.getTasks()) {
                    writer.write("  - Task Name: " + task.getName() + ", Duration: " + task.getDuration());
                    writer.newLine();
                }
                writer.newLine(); // Leave a line between projects
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
