package duke.storage;

import duke.data.task.Deadline;
import duke.data.task.Event;
import duke.data.task.Task;
import duke.data.task.Todo;

import java.util.ArrayList;

public class TaskListEncoder {
    public static ArrayList<String> encodeTaskList(ArrayList<Task> taskList) {
        final ArrayList<String> encodedTasks = new ArrayList<>();

        for(Task task : taskList) {
            encodedTasks.add(encodeTaskToString(task));
        }
        return encodedTasks;
    }

    private static String encodeTaskToString(Task task) {
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
                    + event.getDescription() + "|" + event.getAt();
        }
        return null;
    }
}
