package duke.data.task;

import duke.data.exception.DukeInputException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.ui.UserInterface;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

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

    /**
     * Prints the taskList using the UserInterface.printTaskList(ArrayList<Task>) method
     */
    public static void list() {
        UserInterface.printTaskList(taskList);
    }

    /**
     * Writes the taskList into data.txt.
     */
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

    /**
     * Add a Todo into the taskList.
     *
     * @param commandArgs String input from user.
     * @throws DukeInputException If the user leaves the description empty.
     */
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

    /**
     * Add an Event into the taskList
     *
     * @param commandArgs String input from user
     * @throws DukeInputException If the user leaves the description empty or omits the /at separator.
     */
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

    /**
     * Set a valid task's status
     *
     * @param itemNum Integer index provided by user
     * @param isDone boolean for the Task
     * @throws DukeInputException If the index provided is out of the ArrayList's size
     */
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

    /**
     * Delete a valid Task from the taskList
     *
     * @param itemNum Integer index provided by user
     * @throws DukeInputException If the index provided is out of the ArrayList's size
     */
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

    public static ArrayList<Task> tasksOnDate(LocalDate date) {
        ArrayList<Task> resultList = new ArrayList<>();

        for(Task task : taskList) {
            if(task instanceof Deadline) {
                LocalDate deadlineDate = ((Deadline) task).getBy().toLocalDate();
                if (deadlineDate.isEqual(date)) {
                    resultList.add(task);
                }
            }
            if(task instanceof Event) {
                LocalDate eventDate = ((Event) task).getAtStart().toLocalDate();
                if (eventDate.isEqual(date)) {
                    resultList.add(task);
                }
            }
        }

        return resultList;
    }
}