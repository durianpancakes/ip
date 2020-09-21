package duke.storage;

import duke.data.task.Task;
import duke.data.task.TaskList;
import duke.parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class TaskListDecoder {
    public static ArrayList<Task> decodeTaskList(List<String> encodedTaskList) {
        final ArrayList<Task> decodedTasks = new ArrayList<>();
        for(String encodedTask : encodedTaskList) {
            decodedTasks.add(decodeTaskFromString(encodedTask));
        }
        return decodedTasks;
    }

    private static Task decodeTaskFromString (String encodedTask) {
        return Parser.parseTaskFromString(encodedTask);
    }
}
