package duke.data.task;

import duke.data.exception.DukeInputException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.ui.UserInterface;

import java.io.IOException;
import java.util.ArrayList;

import static duke.parser.Parser.parseEventInput;

public class TaskList {
    private static TaskList INSTANCE = null;
    private static ArrayList<Task> taskList;

    public TaskList () {
        taskList = new ArrayList<>();
    }

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
            UserInterface.printSaveSuccess();
        } catch (IOException e) {
            UserInterface.printSaveError();
        }
    }

    public static ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> resultList = new ArrayList<>();
        for(Task task : taskList) {
            if(task.getDescription().contains(keyword)) {
                resultList.add(task);
            }
        }
        return resultList;
    }

    public static void addTodo(String commandArgs) throws DukeInputException {
        if(commandArgs.equals("")){
            throw new DukeInputException();
        }
        Todo todo = new Todo(commandArgs);
        taskList.add(todo);
        UserInterface.printAddSuccessMsg(todo, taskList.size());
    }


    public static void addDeadline(String commandArgs) throws DukeInputException {
        if(commandArgs.equals("")){
            throw new DukeInputException();
        }
        try {
            Deadline deadline = Parser.parseDeadlineInput(commandArgs);
            taskList.add(deadline);
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

            UserInterface.printDeleteSuccessMsg(task, taskList.size());
        } else {
            throw new DukeInputException();
        }
    }
}