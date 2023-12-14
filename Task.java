package application;

import java.util.List;

public class Task {

    private String name;
    private int duration;
    private List<Task> dependencies;

    public Task(String name, int duration, List<Task> dependencies) {
        this.name = name;
        this.duration = duration;
        this.dependencies = dependencies;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Task> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Task> dependencies) {
        this.dependencies = dependencies;
    }

    // Other methods, if needed

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n");
        sb.append("Duration: ").append(duration).append("\n");
        if (dependencies != null && !dependencies.isEmpty()) {
            sb.append("Dependencies: ").append(dependencies).append("\n");
        }
        return sb.toString();
    }
}
