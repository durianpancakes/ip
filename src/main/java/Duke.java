import java.util.Scanner;

public class Duke {
    private static boolean exitFlag;

    public static void main(String[] args) {
        printIntroMsg();

        // Initialize TaskHelper
        TaskHelper taskHelper = TaskHelper.getInstance();

        String line;
        Scanner in = new Scanner(System.in);

        while(!exitFlag){
            line = in.nextLine();
            String[] stringArray = line.trim().split(" ");

            switch(stringArray[0].toLowerCase()){
            case "bye":
                exitFlag = true;
                break;
            case "list":
                listTasks();
                break;
            case "done":
                try{
                    if(stringArray.length != 1){
                        int itemNum = Integer.parseInt(stringArray[1]);
                        taskHelper.setTaskStatus(itemNum, true);
                    } else {
                        printHorizontalLine();
                        System.out.println("Please enter an item number!");
                        printEmptyLine();
                        printHorizontalLine();
                    }
                } catch (NumberFormatException e) {
                    taskHelper.addTask(new Task(line));
                }
                break;
            default:
                taskHelper.addTask(new Task(line));
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

    private static void printIntroMsg(){
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        System.out.println("Hello from\n" + logo);

        printHorizontalLine();
        System.out.println("Hello! I'm Duke");
        System.out.println("What can I do for you?");
        System.out.println("The following commands are available:");
        System.out.println("1. list");
        System.out.println("2. done <item number>");
        System.out.println("Inputs without commands will be recognized as new events");
        printEmptyLine();
    }

    private static void printByeMsg(){
        printHorizontalLine();
        System.out.println("Bye. Hope to see you again soon!");
        printEmptyLine();
        printHorizontalLine();
    }

    private static void listTasks(){
        printHorizontalLine();
        TaskHelper taskHelper = TaskHelper.getInstance();
        taskHelper.printAllTasks();
        printEmptyLine();
        printHorizontalLine();
    }
}