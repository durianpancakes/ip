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
        Storage storage = new Storage();
        try {
            new TaskList(storage.load("/tasks.txt"));
        } catch (DukeInitializeException e) {
            ui.printInitError();
        }

        ui.printWelcomeMessage();

        while (!exitFlag) {
            exitFlag = ui.getUserCommand();
        }

        ui.printByeMessage();
    }
}