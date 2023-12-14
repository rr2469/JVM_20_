package main;

import java.util.ArrayList
import java.util.List

// Task.kt

class Task(
    var name: String,
    var duration: Int,
    var dependencies: List<Task>? = null
) {
    // Other methods, if needed

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Name: ").append(name).append("\n")
        sb.append("Duration: ").append(duration).append("\n")
        if (dependencies != null && !(dependencies!!.isEmpty())) {
            sb.append("Dependencies: ").append(dependencies).append("\n")
        }
        return sb.toString()
    }
}
