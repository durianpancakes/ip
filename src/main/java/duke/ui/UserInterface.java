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
    private static final String DIVIDER = "---------------------------------------";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static Scanner in = null;
    private static PrintStream out = null;

    public UserInterface() {
        this(System.in, System.out);
    }

    public UserInterface(InputStream in, PrintStream out) {
        UserInterface.in = new Scanner(in);
        UserInterface.out = out;
    }

    /**
     * Displays a message upon successful adding of a Task into the taskList
     *
     * @param task
     * @param totalTasks
     */
    public static void printAddSuccessMsg(Task task, int totalTasks) {
        boolean containsOneTask = totalTasks == 1;

        out.println(DIVIDER);
        out.println(Messages.MESSAGE_ADD_TASK_SUCCESSFUL + task);
        if(containsOneTask){
            out.println("You have " + totalTasks + " task in the list");
        } else {
            out.println("You have " + totalTasks + " tasks in the list");
        }
        TaskList.list();
        out.print(LINE_SEPARATOR);
        out.println(DIVIDER);
    }

    /**
     * Displays a message upon successful deletion of a Task from a taskList
     *
     * @param task the Task that is deleted
     * @param totalTasks total number of tasks in the current taskList
     */
    public static void printDeleteSuccessMsg(Task task, int totalTasks) {
        boolean containsOneTask = totalTasks == 1;

        out.println(DIVIDER);
        out.println(Messages.MESSAGE_DELETE_TASK_SUCCESSFUL);
        out.println(task);
        if(containsOneTask){
            System.out.println("You have " + totalTasks + " task in the list");
        } else if(totalTasks == 0) {
            System.out.println(Messages.MESSAGE_LIST_EMPTY);
        } else {
            System.out.println("You have " + totalTasks + " tasks in the list");
        }
        TaskList.list();
        out.print(LINE_SEPARATOR);
        out.println(DIVIDER);
    }

    /**
     * Displays a message upon successful setting of a Task's isDone.
     *
     * @param task the Task that is set
     */
    public static void printSetTaskMsg(Task task) {
        out.println(DIVIDER);
        if (task.getDone()) {
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(task);
        } else {
            System.out.println("I've marked this task as not done:");
            System.out.println(task);
        }
        out.print(LINE_SEPARATOR);
        out.println(DIVIDER);
    }

    /**
     * Displays an ArrayList of Tasks.
     *
     * @param taskList ArrayList<Task> containing Tasks.
     */
    public static void printTaskList(ArrayList<Task> taskList) {
        out.println(DIVIDER);
        if (taskList.isEmpty()) {
            System.out.println("You have not added any tasks yet!");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < taskList.size(); i++) {
                int itemIdx = i + 1;
                Task task = taskList.get(i);
                out.println(itemIdx + ". " + task);
            }
        }
        out.print(LINE_SEPARATOR);
        out.println(DIVIDER);
    }

    /**
     * Method to read user input from Scanner.
     *
     * @return boolean to determine if Duke continues running.
     */
    public boolean getUserCommand() {
        out.println("What would you like me to do?");
        String userInput = in.nextLine();
        try {
            return Parser.parseCommand(userInput);
        } catch (DukeInputException e) {
            out.println(DIVIDER);
            out.println(Messages.MESSAGE_INVALID_INPUT);
            printHelpMessage();
            out.print(LINE_SEPARATOR);
            out.println(DIVIDER);
        }
        return false;
    }

    /**
     * Displays a welcome message.
     */
    public void printWelcomeMessage() {
        out.println(Messages.MESSAGE_LOGO);
        out.println(Messages.MESSAGE_WELCOME);
        printHelpMessage();
    }

    /**
     * Displays a list of commands available to the user.
     */
    public void printHelpMessage() {
        out.println("The following commands are available:");
        out.println("1. list");
        out.println("2. list DATE <yyyy-MM-dd>");
        out.println("3. todo DESCRIPTION");
        out.println("4. event DESCRIPTION /at DATE_TIME <yyyy-MM-dd HHmm> /to DATE_TIME <yyyy-MM-dd HHmm>");
        out.println("5. deadline DESCRIPTION /by <yyyy-MM-dd HHmm>");
        out.println("6. done ITEM_NUMBER");
        out.println("7. delete ITEM_NUMBER");
        out.println("8. find KEYWORD");
    }

    /**
     * Displays an error message upon failed initialization.
     */
    public static void printInitError() {
        out.println(DIVIDER);
        out.println(Messages.MESSAGE_INIT_FAILED);
        out.println(DIVIDER);
    }

    /**
     * Displays an error message upon an invalid input while adding a Task.
     */
    public static void printAddError() {
        out.println(DIVIDER);
        out.println(Messages.MESSAGE_INCOMPLETE_INPUT);
        out.println(DIVIDER);
    }

    /**
     * Displays a message to the user
     *
     * @param string
     */
    public static void printToUser(String string) {
        out.println(string);
    }

    /**
     * Displays a message upon the successful saving of the taskList.
     */
    public static void printSaveSuccess() {
        out.println(Messages.MESSAGE_SAVE_SUCCESSFUL);
    }

    /**
     * Displays an error message upon the failure to save the taskList.
     */
    public static void printSaveError() {
        out.println(DIVIDER);
        out.println(Messages.MESSAGE_SAVE_FAILED);
        out.println(DIVIDER);
    }

    public void printByeMessage() {
        out.println(Messages.MESSAGE_GOODBYE);
    }
}
