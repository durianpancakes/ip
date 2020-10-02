package duke.ui;

import duke.common.Messages;
import duke.data.exceptions.DukeInputException;
import duke.data.task.Task;
import duke.data.task.TaskList;
import duke.parser.Parser;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    public static final String DIVIDER = "---------------------------------------";
    public static final String LINE_SEPARATOR = System.lineSeparator();

    private static Scanner in = null;
    private static PrintStream out = null;

    /**
     * Default constructor for a UserInterface.
     */
    public UserInterface () {
        this(System.in, System.out);
    }

    /**
     * Constructor for a UserInterface given an InputStream and PrintStream.
     *
     * @param in InputStream
     * @param out PrintStream
     */
    public UserInterface (InputStream in, PrintStream out) {
        UserInterface.in = new Scanner(in);
        UserInterface.out = out;
    }

    /**
     * Displays a message upon successful adding of a Task into the taskList.
     *
     * @param task Task added
     * @param totalTasks Number of tasks in the taskList after the addition of the Task
     */
    public void printAddSuccessMsg (Task task, int totalTasks) {
        TaskList taskList = new TaskList();
        boolean containsOneTask = totalTasks == 1;

        out.println(Messages.MESSAGE_ADD_TASK_SUCCESSFUL + task);
        if (containsOneTask) {
            out.println("You have " + totalTasks + " task in the list");
        } else {
            out.println("You have " + totalTasks + " tasks in the list");
        }
        taskList.list();
    }

    /**
     * Displays a message upon successful deletion of a Task from a taskList.
     *
     * @param task       the Task that is deleted
     * @param totalTasks total number of tasks in the current taskList
     */
    public void printDeleteSuccessMsg (Task task, int totalTasks) {
        boolean containsOneTask = totalTasks == 1;
        boolean emptyList = totalTasks == 0;
        TaskList taskList = new TaskList();

        out.println(Messages.MESSAGE_DELETE_TASK_SUCCESSFUL);
        out.println(task);
        if (containsOneTask) {
            System.out.println("You have " + totalTasks + " task in the list");
        } else if (emptyList) {
            System.out.println(Messages.MESSAGE_LIST_EMPTY);
        } else {
            System.out.println("You have " + totalTasks + " tasks in the list");
        }
        taskList.list();
    }

    /**
     * Displays a message upon successful setting of a Task's isDone.
     *
     * @param task the Task that is set
     */
    public void printSetTaskMsg (Task task) {
        if (task.getDone()) {
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(task);
        } else {
            System.out.println("I've marked this task as not done:");
            System.out.println(task);
        }
    }

    /**
     * Displays an ArrayList of Tasks.
     *
     * @param taskList ArrayList<Task> containing Tasks.
     */
    public void printTaskList (ArrayList<Task> taskList) {
        if (taskList.isEmpty()) {
            System.out.println(Messages.MESSAGE_LIST_EMPTY);
        } else {
            System.out.println(Messages.MESSAGE_TASK_LIST_HEADER);
            for (int i = 0; i < taskList.size(); i++) {
                int itemIdx = i + 1;
                Task task = taskList.get(i);
                out.println(itemIdx + ". " + task);
            }
        }
    }

    /**
     * Method to read user input from Scanner.
     *
     * @return boolean to determine if Duke continues running
     */
    public boolean getUserCommand () {
        printToUser(Messages.MESSAGE_INPUT_PROMPT);
        String userInput = in.nextLine();
        Parser parser = new Parser();
        try {
            return parser.parseCommand(userInput);
        } catch (DukeInputException e) {
            printToUser(Messages.MESSAGE_INVALID_INPUT);
            printHelpMessage();
        }
        return false;
    }

    /**
     * Displays a welcome message.
     */
    public void printWelcomeMessage () {
        printToUser(Messages.MESSAGE_LOGO);
        printToUser(Messages.MESSAGE_WELCOME);
        printHelpMessage();
    }

    /**
     * Displays a list of commands available to the user.
     */
    public void printHelpMessage () {
        printToUser("The following commands are available:");
        printToUser("1. list");
        printToUser("2. list DATE <yyyy-MM-dd>");
        printToUser("3. todo DESCRIPTION");
        printToUser("4. event DESCRIPTION /at DATE_TIME <yyyy-MM-dd HHmm> /to DATE_TIME <yyyy-MM-dd HHmm>");
        printToUser("5. deadline DESCRIPTION /by <yyyy-MM-dd HHmm>");
        printToUser("6. done ITEM_NUMBER");
        printToUser("7. delete ITEM_NUMBER");
        printToUser("8. find KEYWORD");
    }

    /**
     * Displays a message to the user.
     *
     * @param string
     */
    public void printToUser (String string) {
        out.println(string);
    }

    /**
     * Displays a farewell message upon exit of Duke.
     */
    public void printByeMessage () {
        printToUser(Messages.MESSAGE_GOODBYE);
    }

    // ERRORS:

    /**
     * Displays an error message upon an invalid input while adding a Task.
     */
    public void printAddError () {
        printToUser(Messages.MESSAGE_INCOMPLETE_INPUT);
    }

    /**
     * Displays an error message upon the failure to save the taskList.
     */
    public void printSaveError () {
        printToUser(Messages.MESSAGE_SAVE_FAILED);
    }

    /**
     * Displays an error message upon an invalid task index given.
     */
    public void printIndexErrorMessage () {
        printToUser(Messages.MESSAGE_INVALID_INDEX);
    }

    /**
     * Displays an error message upon an invalid data input given.
     */
    public void printDateInputErrorMessage () {
        printToUser(Messages.MESSAGE_INVALID_DATE);
    }

    /**
     * Displays an error message upon an incomplete Event given.
     */
    public void printIncompleteEventError () {
        printToUser(Messages.MESSAGE_INCOMPLETE_EVENT);
    }

    /**
     * Displays an error message upon an incomplete Deadline given.
     */
    public void printIncompleteDeadlineError() {
        printToUser(Messages.MESSAGE_INCOMPLETE_DEADLINE);
    }

    /**
     * Displays an error message upon failed initialization.
     */
    public void printInitError () {
        printToUser(Messages.MESSAGE_INIT_FAILED);
    }
}
