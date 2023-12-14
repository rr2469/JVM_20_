package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectManagerService {

    private List<Project> projects;

    public ProjectManagerService() {
        this.projects = new ArrayList<>();
    }

    public List<Project> getAllProjects() {
        return projects;
    }

    public void createProject(Project project) {
        projects.add(project);
        saveProjectsToFile("Projects.txt");
    }

    private void saveProjectsToFile(String fileName) {
        
    }
    
    public void readProjectsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Project Name:")) {
                    String projectName = line.substring("Project Name:".length()).trim();
                    List<Task> tasks = readTasksFromBufferedReader(br);
                    projects.add(new Project(projectName, tasks));
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }
    }

    private List<Task> readTasksFromBufferedReader(BufferedReader br) throws IOException {
        List<Task> tasks = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
            if (line.startsWith("- Task Name:")) {
                String[] parts = line.split(",");
                String taskName = parts[0].substring("- Task Name:".length()).trim();
                int duration = Integer.parseInt(parts[1].substring(" Duration:".length()).trim());
                tasks.add(new Task(taskName, duration, tasks));
            }
        }
        return tasks;
    }

    public int[][] getAdjacencyMatrix() {
        int size = projects.size();
        int[][] adjacencyMatrix = new int[size][size];

        // Initialize the matrix with zeros
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adjacencyMatrix[i][j] = 0;
            }
        }

        // Populate the matrix based on task dependencies
        for (int i = 0; i < size; i++) {
            Project project = projects.get(i);
            List<Task> tasks = project.getTasks();
            for (Task task : tasks) {
                // Assuming tasks are numbered from 1 to n
                int taskIndex = Integer.parseInt(task.getName()) - 1;
                adjacencyMatrix[i][taskIndex] = 1;
            }
        }

        return adjacencyMatrix;
    }
}
