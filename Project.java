package application;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private String name;
    private List<Task> tasks;

    public Project(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        if (tasks == null) {
            tasks = new ArrayList<>(); // Initialize tasks if null
        }
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    // Other methods, if needed

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
