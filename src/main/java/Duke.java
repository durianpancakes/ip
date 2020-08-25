import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static ArrayList<String> tasks = new ArrayList<>();

    public static void main(String[] args) {
        boolean exitFlag = false;

        printIntroMsg();

        String line;
        Scanner in = new Scanner(System.in);

        while(!exitFlag){
            line = in.nextLine();
            switch(line){
            case "bye":
                exitFlag = true;
                break;
            case "list":
                listTasks();
                break;
            default:
                addTask(line);
            }
        }

        printByeMsg();
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
        printEmptyLine();
        printHorizontalLine();
    }

    private static void addTask(String task){
        printHorizontalLine();
        tasks.add(task);
        System.out.println("added: " + task);
        printEmptyLine();
        printHorizontalLine();
    }

    private static void listTasks(){
        printHorizontalLine();
        for(int i = 0; i < tasks.size(); i++){
            int taskIdx = i + 1;
            System.out.println(taskIdx + ". " + tasks.get(i));
        }
        printEmptyLine();
        printHorizontalLine();
    }

    private static void echo(String message){
        printHorizontalLine();
        System.out.println(message);
        printEmptyLine();
        printHorizontalLine();
    }

    private static void printByeMsg(){
        printHorizontalLine();
        System.out.println("Bye. Hope to see you again soon!");
        printEmptyLine();
        printHorizontalLine();
    }

    private static void printHorizontalLine(){
        System.out.println("-----------------------------------------------");
    }

    private static void printEmptyLine(){
        System.out.println();
    }
}
