package main

import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Scene

class Main : Application() {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java, *args)
        }
    }

    override fun start(primaryStage: Stage) {
        val projectManagerGUI = ProjectManagerGUI(primaryStage)
        val scene = projectManagerGUI.createMainScreen()

        primaryStage.title = "Project Management System"
        primaryStage.scene = scene
        primaryStage.show()
    }
}
