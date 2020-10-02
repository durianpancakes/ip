package duke.data.task;

import duke.data.exceptions.DukeInputException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.ui.UserInterface;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Contains all methods required for the management of a static ArrayList of Tasks.
 */
public class TaskList {
    private static ArrayList<Task> taskList;

    public TaskList () {
    }

    public TaskList (ArrayList<Task> inputTaskList) {
        taskList = inputTaskList;
    }

    /**
     * Prints the taskList using the UserInterface.printTaskList(...) method.
     */
    public void list () {
        UserInterface userInterface = new UserInterface();
        userInterface.printTaskList(taskList);
    }

    /**
     * Writes the taskList into tasks.txt.
     */
    public void save () {
        Storage storage = new Storage();
        UserInterface userInterface = new UserInterface();
        try {
            storage.save(taskList, "/tasks.txt");
        } catch (IOException e) {
            userInterface.printSaveError();
        }
    }

    /**
     * Returns an ArrayList of tasks containing tasks that matches a keyword.
     *
     * @param keyword String containing the keyword given by the user.
     * @return ArrayList of tasks containing tasks that matches the keyword.
     */
    public ArrayList<Task> findTasks (String keyword) {
        ArrayList<Task> resultList = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getDescription().contains(keyword)) {
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
    public void addTodo (String commandArgs) throws DukeInputException {
        UserInterface userInterface = new UserInterface();
        if (commandArgs.equals("")) {
            throw new DukeInputException();
        }
        Todo todo = new Todo(commandArgs);
        taskList.add(todo);
        save();
        userInterface.printAddSuccessMsg(todo, taskList.size());
    }


    /**
     * Add a Deadline into the taskList.
     *
     * @param commandArgs String input from the user.
     * @throws DukeInputException If the user leaves the description empty or omits the /by separator.
     */
    public void addDeadline (String commandArgs) throws DukeInputException {
        UserInterface userInterface = new UserInterface();
        if (commandArgs.equals("")) {
            throw new DukeInputException();
        }
        try {
            Parser parser = new Parser();
            Deadline deadline = parser.parseDeadlineInput(commandArgs);
            taskList.add(deadline);
            save();
            userInterface.printAddSuccessMsg(deadline, taskList.size());
        } catch (StringIndexOutOfBoundsException e) {
            userInterface.printToUser("Did you forget /by?");
        }
    }

    /**
     * Add an Event into the taskList.
     *
     * @param commandArgs String input from user.
     * @throws DukeInputException If the user leaves the description empty or omits the /at separator.
     */
    public void addEvent (String commandArgs) throws DukeInputException {
        UserInterface userInterface = new UserInterface();
        if (commandArgs.equals("")) {
            throw new DukeInputException();
        }
        try {
            Parser parser = new Parser();
            Event event = parser.parseEventInput(commandArgs);
            taskList.add(event);
            save();
            userInterface.printAddSuccessMsg(event, taskList.size());
        } catch (StringIndexOutOfBoundsException e) {
            userInterface.printToUser("Did you forget /at?");
        }
    }

    /**
     * Set a valid task's status.
     *
     * @param itemNum Integer index provided by user.
     * @param isDone  boolean for the Task.
     * @throws DukeInputException If the index provided is out of the ArrayList's size.
     */
    public void setTaskStatus (int itemNum, boolean isDone) throws DukeInputException {
        UserInterface userInterface = new UserInterface();
        if (itemNum > 0 && itemNum <= taskList.size()) {
            int itemIdx = itemNum - 1;
            Task task = taskList.get(itemIdx);

            task.setDone(isDone);
            save();
            userInterface.printSetTaskMsg(task);
        } else {
            userInterface.printIndexErrorMessage();
            throw new DukeInputException();
        }
    }

    /**
     * Delete a valid Task from the taskList.
     *
     * @param itemNum Integer index provided by user.
     * @throws DukeInputException If the index provided is out of the ArrayList's size.
     */
    public void deleteTask (int itemNum) throws DukeInputException {
        UserInterface userInterface = new UserInterface();

        if (itemNum > 0 && itemNum <= taskList.size()) {
            int itemIdx = itemNum - 1;
            Task task = taskList.get(itemIdx);
            taskList.remove(itemIdx);
            save();
            userInterface.printDeleteSuccessMsg(task, taskList.size());
        } else {
            userInterface.printIndexErrorMessage();
            throw new DukeInputException();
        }
    }

    /**
     * Returns an ArrayList of tasks containing tasks occurring on a given LocalDate.
     *
     * @param date LocalDate containing the date to be matched.
     * @return ArrayList of tasks containing tasks occurring on a given LocalDate.
     */
    public ArrayList<Task> tasksOnDate (LocalDate date) {
        ArrayList<Task> resultList = new ArrayList<>();

        for (Task task : taskList) {
            if (task instanceof Deadline) {
                LocalDate deadlineDate = ((Deadline) task).getBy().toLocalDate();
                if (deadlineDate.isEqual(date)) {
                    resultList.add(task);
                }
            }
            if (task instanceof Event) {
                LocalDate eventDate = ((Event) task).getAtStart().toLocalDate();
                if (eventDate.isEqual(date)) {
                    resultList.add(task);
                }
            }
        }

        return resultList;
    }
}