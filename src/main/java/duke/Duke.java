package duke;

import duke.data.exception.DukeInitializeException;
import duke.data.task.TaskList;
import duke.storage.Storage;
import duke.ui.UserInterface;

public class Duke {

    public static void main(String[] args) {
        boolean exitFlag = false;
        UserInterface ui = new UserInterface();
        Storage storage = new Storage();
        try {
            TaskList taskList = new TaskList(Storage.load());
        } catch (DukeInitializeException e) {
            UserInterface.printInitError();
        }

        ui.printWelcomeMessage();

        while(!exitFlag) {
            exitFlag = ui.getUserCommand();
        }

        ui.printByeMessage();
    }

//
//
//    private static void listTasks() {
//        printHorizontalLine();
//        TaskHelper taskHelper = TaskHelper.getInstance();
//        taskHelper.printAllTasks();
//        printEmptyLine();
//        printHorizontalLine();
//    }
}