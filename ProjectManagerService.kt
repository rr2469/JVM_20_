package main

import java.io.BufferedWriter
import java.io.FileWriter
import java.io.IOException

class ProjectManagerService {

    private val projects: MutableList<Project> = ArrayList()

    fun getAllProjects(): List<Project> {
        return projects
    }

    fun createProject(project: Project) {
        projects.add(project)
        saveProjectsToFile("Projects.txt")
    }

    private fun saveProjectsToFile(fileName: String) {
    try {
        BufferedWriter(FileWriter(fileName)).use { writer ->
            projects.forEach { project ->
                writer.write("Project Name: ${project.name}")
                writer.newLine()
                writer.write("Tasks:")
                writer.newLine()
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
