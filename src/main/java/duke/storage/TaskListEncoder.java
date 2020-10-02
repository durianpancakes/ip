package duke.storage;

import duke.data.task.Deadline;
import duke.data.task.Event;
import duke.data.task.Task;
import duke.data.task.Todo;

import java.util.ArrayList;

public class TaskListEncoder {
    /**
     * Returns an ArrayList of strings representation of an ArrayList of tasks.
     *
     * @param taskList ArrayList of tasks
     * @return ArrayList of strings containing the encoded Strings of Tasks
     */
    public static ArrayList<String> encodeTaskList (ArrayList<Task> taskList) {
        final ArrayList<String> encodedTasks = new ArrayList<>();

        for (Task task : taskList) {
            encodedTasks.add(encodeTaskToString(task));
        }
        return encodedTasks;
    }

    /**
     * Returns an encoded String representation of a Task.
     *
     * @param task from the ArrayList
     * @return String converted from Task
     */
    private static String encodeTaskToString (Task task) {
        if (task instanceof Todo) {
            Todo todo = (Todo) task;
            return todo.getTypeIcon() + "|" + todo.getDone() + "|"
                    + todo.getDescription();
        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return deadline.getTypeIcon() + "|" + deadline.getDone() + "|"
                    + deadline.getDescription() + "|" + deadline.getBy();
        } else if (task instanceof Event) {
            Event event = (Event) task;
            return event.getTypeIcon() + "|" + event.getDone() + "|"
                    + event.getDescription() + "|" + event.getAtStart()
                    + "|" + event.getAtEnd();
        }
        return null;
    }
}
