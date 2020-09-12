package duke;

import duke.task.DukeIOException;
import duke.task.TaskHelper;

import java.io.IOException;
import java.util.Scanner;

public class Duke {
    private static boolean exitFlag;

    public static void main(String[] args) {
        printIntroMsg();

        // Initialize TaskHelper
        TaskHelper taskHelper = TaskHelper.getInstance();

        try {
            TaskHelper.initializeTasks();
            taskHelper.printAllTasks();
        } catch (IOException | DukeIOException e) {
            System.out.println("There was an error initializing Duke");
        }

        String userCommand;
        Scanner in = new Scanner(System.in);

        while(!exitFlag){
            userCommand = in.nextLine();
            final String[] split = userCommand.trim().split("\\s+", 2);
            final String[] commandTypeAndParams = split.length == 2 ? split : new String[]{split[0], ""};
            final String commandType = commandTypeAndParams[0];
            final String commandArgs = commandTypeAndParams[1];

            switch(commandType) {
            case "bye":
                exitFlag = true;
                try {
                    TaskHelper.saveTasks();
                } catch (IOException | DukeIOException e) {
                    System.out.println("Unfortunately, there was a problem saving your tasks.");
                }
                break;
            case "list":
                listTasks();
                break;
            case "done":
                if(!commandArgs.isEmpty()){
                    try {
                        int itemNum = Integer.parseInt(commandArgs);
                        taskHelper.setTaskStatus(itemNum, true);
                    } catch (NumberFormatException e){
                        printMsg("Please enter a valid item number!");
                    }
                }
                break;
            case "todo":
                try {
                    taskHelper.addTodo(commandArgs);
                } catch (DukeInputException e) {
                    printMsg(e.error);
                }
                break;
            case "event":
                try {
                    taskHelper.addEvent(commandArgs);
                } catch (DukeInputException e) {
                    printMsg(e.error);
                }
                break;
            case "deadline":
                try {
                    taskHelper.addDeadline(commandArgs);
                } catch (DukeInputException e) {
                    printMsg(e.error);
                }
                break;
            case "delete":
                if(!commandArgs.isEmpty()){
                    try {
                        int itemNum = Integer.parseInt(commandArgs);
                        taskHelper.deleteTask(itemNum);
                    } catch (NumberFormatException e){
                        printMsg("Please enter a valid item number!");
                    }
                }
                break;
            default:
                printHorizontalLine();
                System.out.println("I didn't understand that command.");
                printHelpMsg();
                printEmptyLine();
                printHorizontalLine();
                break;
            }
        }

        printByeMsg();
    }

    private static void printHorizontalLine(){
        System.out.println("-----------------------------------------------");
    }

    private static void printEmptyLine(){
        System.out.println();
    }

    private static void printIntroMsg() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        System.out.println("Hello from\n" + logo);

        printHorizontalLine();
        System.out.println("Hello! I'm Duke");
        System.out.println("What can I do for you?");
        printHelpMsg();
        printEmptyLine();
    }

    private static void printHelpMsg() {
        System.out.println("The following commands are available:");
        System.out.println("1. list");
        System.out.println("2. todo <description>");
        System.out.println("3. event <description> /at <date time>");
        System.out.println("4. deadline <description> /by <date time>");
        System.out.println("5. done <item number>");
        System.out.println("6. delete <item number>");
    }

    private static void printByeMsg() {
        printHorizontalLine();
        System.out.println("Bye. Hope to see you again soon!");
        printEmptyLine();
        printHorizontalLine();
    }

    private static void printMsg(String message) {
        printHorizontalLine();
        System.out.println(message);
        printEmptyLine();
        printHorizontalLine();
    }

    private static void listTasks() {
        printHorizontalLine();
        TaskHelper taskHelper = TaskHelper.getInstance();
        taskHelper.printAllTasks();
        printEmptyLine();
        printHorizontalLine();
    }
}