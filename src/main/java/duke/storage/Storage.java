package duke.storage;

import duke.data.exceptions.DukeInitializeException;
import duke.data.task.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Storage {
    private static final String DIRECTORY_PATH = new File("data").getAbsolutePath();


    /**
     * Returns an ArrayList of tasks processed from Duke's storage.
     *
     * @param dataPath String containing data path of file to be loaded
     * @return ArrayList containing Tasks from Duke's storage.
     * @throws DukeInitializeException If the data directory doesn't exist, or when the creation
     *                                 of tasks.txt fails.
     */
    public ArrayList<Task> load (String dataPath) throws DukeInitializeException {
        final String filePath = DIRECTORY_PATH + dataPath;
        if (Files.exists(Path.of(DIRECTORY_PATH))) {
            // Data directory exists
            File file = new File(filePath);
            ArrayList<String> data = new ArrayList<>();
            try {
                Scanner sc = new Scanner(file);
                while (sc.hasNext()) {
                    String dataString = sc.nextLine();
                    data.add(dataString);
                }
            } catch (IOException e) {
                throw new DukeInitializeException();
            }

            TaskListDecoder taskListDecoder = new TaskListDecoder();

            return taskListDecoder.decodeTaskList(data);
        } else {
            // Data directory does not exist. Initialize data directory.
            try {
                createDataFile();
            } catch (IOException e) {
                throw new DukeInitializeException();
            }
            return new ArrayList<>();
        }
    }

    /**
     * Creates the .txt file to store tasks.
     *
     * @throws IOException If the creation of the .txt file fails.
     */
    private void createDataFile () throws IOException {
        File file = new File(Storage.DIRECTORY_PATH);
        boolean dirCreated = file.mkdir();
        if (dirCreated) {
            file = new File(Storage.DIRECTORY_PATH + "/tasks.txt");
            file.createNewFile();
        }
    }

    /**
     * Saves the ArrayList of tasks in a String format into Duke's storage.
     *
     * @param taskList ArrayList of tasks to be saved.
     * @throws IOException If there is an error while writing to Duke's storage.
     */
    public void save (ArrayList<Task> taskList, String dataPath) throws IOException {
        final String filePath = DIRECTORY_PATH + dataPath;
        List<String> encodedTaskList = TaskListEncoder.encodeTaskList(taskList);
        Files.write(Path.of(filePath), encodedTaskList);
    }
}
