package duke;

import duke.data.exceptions.DukeInitializeException;
import duke.data.task.TaskList;
import duke.storage.Storage;
import duke.ui.UserInterface;

public class Duke {
    /**
     * Driver function for Duke
     *
     * @param args
     */
    public static void main (String[] args) {
        boolean exitFlag = false;
        UserInterface ui = new UserInterface();
        new Storage();
        try {
            TaskList taskList = new TaskList(Storage.load());
        } catch (DukeInitializeException e) {
            UserInterface.printInitError();
        }

        ui.printWelcomeMessage();

        while (!exitFlag) {
            exitFlag = ui.getUserCommand();
        }

        ui.printByeMessage();
    }
}