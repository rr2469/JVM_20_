package main

import javafx.collections.FXCollections
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.IOException

class NewProjectScreen(private val projectManagerService: ProjectManagerService) {

    fun createNewProjectScreen(primaryStage: Stage): Scene {
        val newProjectLayout = VBox()

        // Input fields for creating a new project
        val projectNameField = TextField("Enter Project Name")
        val createProjectButton = Button("Create Project")
        val backButton = Button("Back")

        // Task input fields
        val taskNameField = TextField("Enter Task Name")
        val taskDurationField = TextField("Enter Task Duration")

        val addTaskButton = Button("Add Task")

        // List to store tasks
        val taskList = FXCollections.observableArrayList<Task>()

        val taskListView = ListView(taskList)

        addTaskButton.setOnAction {
            val taskName = taskNameField.text
            val taskDuration = taskDurationField.text.toInt()

            val newTask = Task(taskName, taskDuration, null) // You might want to initialize dependencies here
            taskList.add(newTask)

            // Clear input fields
            taskNameField.clear()
            taskDurationField.clear()
        }

        createProjectButton.setOnAction {
            val projectName = projectNameField.text
            val taskListCopy = ArrayList(taskList) // Create a copy to avoid potential side effects
            val newProject = Project(projectName, taskListCopy)
            projectManagerService.createProject(newProject)
            saveProjectsToFile("Projects.txt", projectManagerService.getAllProjects()) // Save all projects
            showAlert("Project created successfully!")
        }

        backButton.setOnAction { goBack(primaryStage) }

        newProjectLayout.children.addAll(projectNameField, taskNameField, taskDurationField, addTaskButton, taskListView, createProjectButton, backButton)

        return Scene(newProjectLayout, 400.0, 500.0)
    }

    private fun showAlert(string: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = "Information"
        alert.headerText = null
        alert.contentText = string
        alert.showAndWait()
    }

    private fun goBack(primaryStage: Stage) {
        // You may want to implement additional logic here before going back
        val projectManagerGUI = ProjectManagerGUI(primaryStage)
        primaryStage.scene = projectManagerGUI.createMainScreen()
    }

    private fun saveProjectsToFile(fileName: String, projects: List<Project>) {
    try {
        BufferedWriter(FileWriter(fileName, true)).use { writer ->
            for (project in projects) {
                writer.write("Project Name: ${project.name}")
                writer.newLine()
                writer.write("Tasks:")
                writer.newLine()
                
                // Use safe call operator ?. to handle potential null values in project.tasks
                project.tasks?.forEach { task ->
                    writer.write("  - Task Name: ${task.name}, Duration: ${task.duration}")
                    writer.newLine()
                }
                writer.newLine() // Leave a line between projects
            }
        }
    } catch (ex: IOException) {
        ex.printStackTrace()
    }
}

}
