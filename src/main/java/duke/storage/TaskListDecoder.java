package duke.storage;

import duke.data.task.Task;
import duke.parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class TaskListDecoder {
    /**
     * Returns an ArrayList of tasks decoded from an encoded ArrayList of strings.
     *
     * @param encodedTaskList ArrayList of strings.
     * @return ArrayList of tasks containing the decoded Tasks.
     */
    public ArrayList<Task> decodeTaskList (List<String> encodedTaskList) {
        final ArrayList<Task> decodedTasks = new ArrayList<>();
        for (String encodedTask : encodedTaskList) {
            decodedTasks.add(decodeTaskFromString(encodedTask));
        }
        return decodedTasks;
    }

    /**
     * Returns a Task decoded from an encoded String.
     *
     * @param encodedTask encoded String of a Task.
     * @return Task converted from String.
     */
    private Task decodeTaskFromString (String encodedTask) {
        Parser parser = new Parser();

        return parser.parseTaskFromString(encodedTask);
    }
}
