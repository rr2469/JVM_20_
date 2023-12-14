package main

import javafx.collections.ObservableList
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

class ProjectsScreen(private val projectManagerService: ProjectManagerService) {

    fun createProjectsScreen(primaryStage: Stage): Scene {
        val projectsLayout = VBox()

        // Fetch all projects from the service and display them
        val projectsListView = ListView<Project>()
        refreshProjectsListView(projectsListView)

        val backButton = Button("Back")

        backButton.setOnAction { goBack(primaryStage) }

        val viewDetailsBox = HBox()
        val projectNameField = TextField()
        projectNameField.promptText = "Project Name"
        val viewDetailsButton = Button("View Details")
        viewDetailsButton.setOnAction { showProjectDetails(projectsListView, projectNameField.text) }

        viewDetailsBox.children.addAll(projectNameField, viewDetailsButton)

        projectsLayout.children.addAll(projectsListView, viewDetailsBox, backButton)

        return Scene(projectsLayout, 800.0, 600.0)
    }

    private fun refreshProjectsListView(projectsListView: ListView<Project>) {
        projectsListView.items.clear()

        // Read projects from the file and add them to the list view
        readProjectsFromFile("Projects.txt", projectsListView.items)
        updateListViewWithFormattedTasks(projectsListView)
    }

    private fun readProjectsFromFile(fileName: String, projects: ObservableList<Project>) {
    try {
        BufferedReader(FileReader(fileName)).use { reader ->
            var line: String?
            var currentProject: Project? = null

            while (reader.readLine().also { line = it } != null) {
                when {
                    line!!.startsWith("Project Name: ") -> {
                        val projectName = line!!.substring("Project Name: ".length)
                        currentProject = Project(projectName, null)
                        projects.add(currentProject)
                    }
                    line!!.startsWith("  - Task Name: ") -> {
                        val taskName = line!!.substring("  - Task Name: ".length, line!!.indexOf(", Duration: "))
                        val taskDuration = line!!.substring(line!!.indexOf(", Duration: ") + ", Duration: ".length).toInt()
                        
                        // Use safe call operator ?.
                        currentProject?.tasks?.add(Task(taskName, taskDuration, null))
                    }
                }
            }
        }
    } catch (ex: IOException) {
        ex.printStackTrace()
    }
}


    private fun updateListViewWithFormattedTasks(projectsListView: ListView<Project>) {
    projectsListView.items.forEach { project ->
        project.tasks?.forEach { task ->
            task?.name = formatTaskName(task)
            task?.duration = formatTaskDuration(task)
            // You might want to format dependencies here if needed
        }
    }
}

    private fun formatTaskName(task: Task): String {
        return "Task: ${task.name}"
    }

    private fun formatTaskDuration(task: Task): Int {
        // Convert duration to int first and then format
        val duration = task.duration.toInt()
        return duration
    }

    private fun showProjectDetails(projectsListView: ListView<Project>, projectName: String) {
        projectsListView.items.asSequence()
            .filter { project -> project.name.equals(projectName, ignoreCase = true) }
            .firstOrNull()
            ?.let { showDetailsAlert(it) }
    }

    private fun showDetailsAlert(project: Project) {
    val alert = Alert(Alert.AlertType.INFORMATION)
    alert.title = "Project Details"
    alert.headerText = "Details for Project: ${project.name}"
    val contentText = StringBuilder()

    // Use safe call operator ?.
    project.tasks?.forEach { task ->
        contentText.append("${formatTaskName(task)}, Duration: ${formatTaskDuration(task)}\n")
    }

    alert.contentText = contentText.toString()
    alert.showAndWait()
}

    private fun goBack(primaryStage: Stage) {
        // You may want to implement additional logic here before going back
        val projectManagerGUI = ProjectManagerGUI(primaryStage)
        primaryStage.scene = projectManagerGUI.createMainScreen()
    }
}
