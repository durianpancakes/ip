package duke.storage;

import duke.data.exception.DukeInitializeException;
import duke.data.task.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Storage {
    private static final String DIRECTORY_PATH = new File("data").getAbsolutePath();
    private static final String FILE_PATH = DIRECTORY_PATH + "/tasks.txt";

    public static ArrayList<Task> load() throws DukeInitializeException {
        if(Files.exists(Path.of(DIRECTORY_PATH))){
            // Data directory exists
            File file = new File(FILE_PATH);
            ArrayList<String> data = new ArrayList<>();
            try {
                Scanner sc = new Scanner(file);
                while(sc.hasNext()) {
                    String dataString = sc.nextLine();
                    data.add(dataString);
                }
            } catch (IOException e){
                throw new DukeInitializeException();
            }

            return TaskListDecoder.decodeTaskList(data);
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

    private static void createDataFile() throws IOException {
        File file = new File(Storage.DIRECTORY_PATH);
        boolean dirCreated = file.mkdir();
        if(dirCreated) {
            file = new File(Storage.DIRECTORY_PATH + "/tasks.txt");
            file.createNewFile();
        }
    }

    public void save(ArrayList<Task> taskList) throws IOException {
        List<String> encodedTaskList = TaskListEncoder.encodeTaskList(taskList);
        Files.write(Path.of(FILE_PATH), encodedTaskList);
    }
}
