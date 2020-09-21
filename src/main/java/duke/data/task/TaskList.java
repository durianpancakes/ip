package duke.data.task;

import duke.data.exception.DukeInputException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.ui.UserInterface;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static duke.parser.Parser.parseEventInput;

public class TaskList {
    private static ArrayList<Task> taskList;

    public TaskList (ArrayList<Task> taskList) {
        TaskList.taskList = taskList;
    }

    public static void list() {
        UserInterface.printTaskList(taskList);
    }

    public static void save() {
        Storage storage = new Storage();
        try {
            storage.save(taskList);
        } catch (IOException e) {
            UserInterface.printSaveError();
        }
    }

    public static void addTodo(String commandArgs) throws DukeInputException {
        if(commandArgs.equals("")){
            throw new DukeInputException();
        }
        Todo todo = new Todo(commandArgs);
        taskList.add(todo);
        save();
        UserInterface.printAddSuccessMsg(todo, taskList.size());
    }


    public static void addDeadline(String commandArgs) throws DukeInputException {
        if(commandArgs.equals("")){
            throw new DukeInputException();
        }
        try {
            Deadline deadline = Parser.parseDeadlineInput(commandArgs);
            taskList.add(deadline);
            save();
            UserInterface.printAddSuccessMsg(deadline, taskList.size());
        } catch (StringIndexOutOfBoundsException e) {
            UserInterface.printToUser("Did you forget /by?");
        }
    }

    public static void addEvent(String commandArgs) throws DukeInputException {
        if(commandArgs.equals("")){
            throw new DukeInputException();
        }
        try {
            Event event = parseEventInput(commandArgs);
            taskList.add(event);
            save();
            UserInterface.printAddSuccessMsg(event, taskList.size());
        } catch (StringIndexOutOfBoundsException e) {
            UserInterface.printToUser("Did you forget /at?");
        }
    }

    public static void setTaskStatus(int itemNum, boolean isDone) throws DukeInputException {
        if (itemNum > 0 && itemNum <= taskList.size()) {
            int itemIdx = itemNum - 1;
            Task task = taskList.get(itemIdx);
            task.setDone(isDone);
            save();
            UserInterface.printSetTaskMsg(task);
        } else {
            throw new DukeInputException();
        }
    }

    public static void deleteTask(int itemNum) throws DukeInputException {
        if (itemNum > 0 && itemNum <= taskList.size()) {
            int itemIdx = itemNum - 1;
            Task task = taskList.get(itemIdx);
            taskList.remove(itemIdx);
            save();
            UserInterface.printDeleteSuccessMsg(task, taskList.size());
        } else {
            throw new DukeInputException();
        }
    }
}