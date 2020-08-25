import java.util.Scanner;

public class Duke {
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
            default:
                echo(line);
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
