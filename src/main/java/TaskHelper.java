import java.util.ArrayList;

public class TaskHelper {
    private static TaskHelper INSTANCE = null;
    private final ArrayList<Task> taskList = new ArrayList<>();

    public static TaskHelper getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TaskHelper();
        }
        return INSTANCE;
    }

    private static void printHorizontalLine(){
        System.out.println("-----------------------------------------------");
    }

    private static void printEmptyLine(){
        System.out.println();
    }

    public void printAllTasks(){
        for(int i = 0; i < taskList.size(); i++){
            int itemIdx = i + 1;
            Task task = taskList.get(i);
            System.out.println(itemIdx + "."
                    + "[" + task.getStatusIcon() + "]"
                    +  " " + task.getTaskTitle());
        }
    }

    public void addTask(Task task){
        taskList.add(task);
        echo(task.getTaskTitle());
    }

    private static void echo(String command){
        printHorizontalLine();
        System.out.println("added: " + command);
        printEmptyLine();
        printHorizontalLine();
    }

    public void setTaskStatus(int itemNum, boolean isDone){
        if(itemNum > 0 && itemNum <= taskList.size()){
            int itemIdx = itemNum - 1;
            Task task = taskList.get(itemIdx);

            task.setDone(isDone);

            printHorizontalLine();
            if(task.isDone){
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("[" + task.getStatusIcon() + "] "
                        + task.getTaskTitle());
            } else {
                System.out.println("I've marked this task as not done:");
                System.out.println("[" + task.getStatusIcon() + "] "
                        + task.getTaskTitle());
            }
            printEmptyLine();
            printHorizontalLine();
        } else {
            printHorizontalLine();
            System.out.println("Invalid task number given!");
            printEmptyLine();
            printHorizontalLine();
        }
    }
}