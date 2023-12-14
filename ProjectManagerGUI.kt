package main

import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.effect.DropShadow
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.stage.Stage

class ProjectManagerGUI(private val primaryStage: Stage) {

    private val projectManagerService = ProjectManagerService()
    private val projectsScreen = ProjectsScreen(projectManagerService)
    private val newProjectScreen = NewProjectScreen(projectManagerService)

    fun createMainScreen(): Scene {
        val mainLayout = BorderPane()

        // Set background image at the top

        // Create buttons for navigation
        val viewProjectsButton = Button("View Projects")
        viewProjectsButton.style = "-fx-background-color: green; -fx-text-fill: white;"
        viewProjectsButton.effect = DropShadow()

        viewProjectsButton.setOnAction { showProjectsScreen() }

        val newProjectButton = Button("New Project")
        newProjectButton.style = "-fx-background-color: green; -fx-text-fill: white;"
        newProjectButton.effect = DropShadow()

        newProjectButton.setOnAction { showNewProjectScreen() }

        // Add buttons to the layout
        val buttonsLayout = VBox()  // Adjust the spacing between buttons
        buttonsLayout.alignment = Pos.CENTER
        buttonsLayout.children.addAll(viewProjectsButton, newProjectButton)

        mainLayout.center = buttonsLayout

        return Scene(mainLayout, 800.0, 600.0)
    }

    private fun showProjectsScreen() {
        val projectsScene = projectsScreen.createProjectsScreen(primaryStage)
        primaryStage.scene = projectsScene
    }

    private fun showNewProjectScreen() {
        val newProjectScene = newProjectScreen.createNewProjectScreen(primaryStage)
        primaryStage.scene = newProjectScene
    }

    private fun showAlert(message: String) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = "Information"
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}
