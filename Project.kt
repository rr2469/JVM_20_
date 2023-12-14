package main;

import java.util.ArrayList

class Project(
    var name: String,
    var tasks: MutableList<Task>? = null
) {
    // Ensure tasks are initialized
    init {
        if (tasks == null) {
            tasks = ArrayList()
        }
    }

    // Other methods, if needed

    override fun toString(): String {
        return "Project{" +
                "name='$name', " +
                "tasks=$tasks" +
                '}'
    }
}

