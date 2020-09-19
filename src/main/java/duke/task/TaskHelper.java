package duke.task;

import duke.DukeIOException;
import duke.DukeInputException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class TaskHelper {
    private static TaskHelper INSTANCE = null;
    private static final ArrayList<Task> taskList = new ArrayList<>();

    public static TaskHelper getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new TaskHelper();
        }
        return INSTANCE;
    }

    public static void initializeTasks() throws DukeIOException, IOException {
        String dataPath = new File("data").getAbsolutePath();

        if(Files.exists(Path.of(dataPath))){
            // Data directory exists
            File file = new File(dataPath + "/tasks.txt");
            Scanner sc = new Scanner(file);

            while(sc.hasNext()) {
                String dataString = sc.nextLine();
                final String[] data = dataString.trim().split("\\|", 3);

                switch(data[0]) {
                case "[T]":
                    initializeTodo(data);
                    break;
                case "[D]":
                    initializeDeadline(data);
                    break;
                case "[E]":
                    initializeEvent(data);
                    break;
                }
            }
        } else {
            // Data directory does not exist. Initialize data directory.
            createDataFile(dataPath);
        }
    }

    private static void createDataFile(String dataPath) throws IOException, DukeIOException {
        File file = new File(dataPath);
        boolean dirCreated = file.mkdir();
        if(dirCreated) {
            file = new File(dataPath + "/tasks.txt");
            file.createNewFile();
        } else {
            throw new DukeIOException();
        }
    }

    private static void initializeEvent(String[] data) {
        boolean isDone;
        String description;
        isDone = Boolean.parseBoolean(data[1]);
        String[] eventInfo = data[2].trim().split("\\|", 2);
        description = eventInfo[0];
        String at = eventInfo[1];
        taskList.add(new Event(description, at, isDone));
    }

    private static void initializeDeadline(String[] data) {
        boolean isDone;
        String description;
        isDone = Boolean.parseBoolean(data[1]);
        String[] deadlineInfo = data[2].trim().split("\\|", 2);
        description = deadlineInfo[0];
        String by = deadlineInfo[1];
        taskList.add(new Deadline(description, by, isDone));
    }

    private static void initializeTodo(String[] data) {
        boolean isDone;
        String description;
        isDone = Boolean.parseBoolean(data[1]);
        description = data[2];
        taskList.add(new Todo(description, isDone));
    }

    public static void saveTasks() throws DukeIOException, IOException {
        String dataPath = new File("data/tasks.txt").getAbsolutePath();
        StringBuilder dataString = new StringBuilder();

        for (Task task : taskList) {
            if (task instanceof Todo) {
                Todo todo = (Todo) task;
                String todoString = todo.getTypeIcon() + "|" + todo.isDone + "|"
                        + todo.description + System.lineSeparator();
                dataString.append(todoString);
            } else if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                String deadlineString = deadline.getTypeIcon() + "|" + deadline.isDone + "|"
                        + deadline.description + "|" + deadline.by + System.lineSeparator();
                dataString.append(deadlineString);
            } else if (task instanceof Event) {
                Event event = (Event) task;
                String eventString = event.getTypeIcon() + "|" + event.isDone + "|"
                        + event.description + "|" + event.at + System.lineSeparator();
                dataString.append(eventString);
            } else {
                throw new DukeIOException();
            }
        }

        FileWriter fw = new FileWriter(dataPath);
        fw.write(String.valueOf(dataString));
        fw.close();
    }

    private static void printHorizontalLine() {
        System.out.println("-----------------------------------------------");
    }

    private static void printEmptyLine() {
        System.out.println();
    }

    public void printAllTasks() {
        if (taskList.isEmpty()) {
            System.out.println("You have not added any tasks yet!");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < taskList.size(); i++) {
                int itemIdx = i + 1;
                Task task = taskList.get(i);
                System.out.println(itemIdx + ". " + task);
            }
        }
    }

    public void addTodo(String commandArgs) throws DukeInputException {
        if(commandArgs.equals("")){
            throw new DukeInputException("Todo description cannot be empty!");
        } else {
            Todo todo = new Todo(commandArgs);
            taskList.add(todo);
            printAddMsg(todo);
        }
    }

    public void addDeadline(String commandArgs) throws DukeInputException {
        if(commandArgs.equals("")){
            throw new DukeInputException("Deadline description cannot be empty!");
        }

        try {
            final String matchByPrefix = "/by";
            final int indexOfByPrefix = commandArgs.indexOf(matchByPrefix);
            String description = commandArgs.substring(0, indexOfByPrefix).trim();
            String by = commandArgs.substring(indexOfByPrefix).replace("/by", "").trim();
            Deadline deadline = new Deadline(description, by);
            taskList.add(deadline);
            printAddMsg(deadline);
        } catch (StringIndexOutOfBoundsException e) {
            throw new DukeInputException("Did you forget /by?");
        }
    }

    public void addEvent(String commandArgs) throws DukeInputException {
        if(commandArgs.equals("")){
            throw new DukeInputException("Event description cannot be empty!");
        } else {
            try {
                final String matchByPrefix = "/at";
                final int indexOfByPrefix = commandArgs.indexOf(matchByPrefix);
                String description = commandArgs.substring(0, indexOfByPrefix).trim();
                String at = commandArgs.substring(indexOfByPrefix).replace("/at", "").trim();
                if(at.equals("")) {
                    throw new DukeInputException("Event at cannot be empty!");
                }
                Event event = new Event(description, at);
                taskList.add(event);
                printAddMsg(event);
            } catch (StringIndexOutOfBoundsException e) {
                throw new DukeInputException("Did you forget /at?");
            }
        }

    }

    private void printAddMsg(Task task) {
        printHorizontalLine();
        System.out.println("added: " + task);
        if(taskList.size() == 1){
            System.out.println("You have " + taskList.size() + " task in the list");
        } else {
            System.out.println("You have " + taskList.size() + " tasks in the list");
        }
        printEmptyLine();
        printHorizontalLine();
    }

    public void setTaskStatus(int itemNum, boolean isDone) {
        if (itemNum > 0 && itemNum <= taskList.size()) {
            int itemIdx = itemNum - 1;
            Task task = taskList.get(itemIdx);

            task.setDone(isDone);

            printSetTaskMsg(task);
        } else {
            printTaskError();
        }
    }

    private void printTaskError() {
        printHorizontalLine();
        System.out.println("Invalid task number given!");
        printEmptyLine();
        printHorizontalLine();
    }

    private void printSetTaskMsg(Task task) {
        printHorizontalLine();
        if (task.isDone) {
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(task);
        } else {
            System.out.println("I've marked this task as not done:");
            System.out.println(task);
        }
        printEmptyLine();
        printHorizontalLine();
    }

    public void deleteTask(int itemNum) {
        if (itemNum > 0 && itemNum <= taskList.size()) {
            int itemIdx = itemNum - 1;
            Task task = taskList.get(itemIdx);
            taskList.remove(itemIdx);

            printHorizontalLine();
            System.out.println("Noted. I've removed this task:");
            System.out.println(task);
            if(taskList.size() == 1){
                System.out.println("You have " + taskList.size() + " task in the list");
            } else if(taskList.isEmpty()) {
                System.out.println("You have no task in the list currently");
            } else {
                System.out.println("You have " + taskList.size() + " tasks in the list");
            }
            printEmptyLine();
            printHorizontalLine();
        } else {
            printTaskError();
        }
    }
}