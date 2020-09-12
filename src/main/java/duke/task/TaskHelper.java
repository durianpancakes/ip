package duke.task;

import duke.Duke;
import duke.DukeInputException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskHelper {
    private static TaskHelper INSTANCE = null;
    private static final ArrayList<Task> taskList = new ArrayList<>();

    public static TaskHelper getInstance(){
        if (INSTANCE == null) {
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

    public void printAllTasks() {
        if (taskList.size() == 0) {
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
        } else {
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
                } else {
                    Event event = new Event(description, at);
                    taskList.add(event);
                    printAddMsg(event);
                }
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
        } else {
            printHorizontalLine();
            System.out.println("Invalid task number given!");
            printEmptyLine();
            printHorizontalLine();
        }
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
            } else if(taskList.size() == 0) {
                System.out.println("You have no task in the list currently");
            } else {
                System.out.println("You have " + taskList.size() + " tasks in the list");
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