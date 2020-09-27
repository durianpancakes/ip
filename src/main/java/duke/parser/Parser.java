package duke.parser;

import duke.data.exceptions.DukeInitializeException;
import duke.data.exceptions.DukeInputException;
import duke.data.task.*;
import duke.storage.Storage;
import duke.ui.UserInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Parser {
    /**
     * Returns a Task input from tasks.txt.
     *
     * @param encodedTask String from tasks.txt.
     * @return Task
     */
    public Task parseTaskFromString (String encodedTask) {
        final String[] data = encodedTask.trim().split("\\|", 3);

        switch (data[0]) {
            case "[T]":
                return parseTodo(data);
            case "[D]":
                return parseDeadline(data);
            case "[E]":
                return parseEvent(data);
            default:
                return null;
        }
    }

    /**
     * Returns a Deadline input from user.
     *
     * @param commandArgs String input from user.
     * @return Deadline.
     * @throws DukeInputException If user leaves /by of Deadline empty, or when date input of /by is invalid.
     */
    public Deadline parseDeadlineInput (String commandArgs) throws DukeInputException {
        UserInterface userInterface = new UserInterface();
        final String matchByPrefix = "/by";
        final int indexOfByPrefix = commandArgs.indexOf(matchByPrefix);
        String description = commandArgs.substring(0, indexOfByPrefix).trim();
        String byString = commandArgs.substring(indexOfByPrefix).replace("/by", "").trim();
        if (byString.equals("")) {
            userInterface.printToUser("ERROR: Deadline by cannot be empty!");
            throw new DukeInputException();
        }
        LocalDateTime by;
        try {
            by = parseLocalDateTime(byString);
        } catch (DateTimeParseException e) {
            userInterface.printToUser("ERROR: Date input is invalid!");
            throw new DukeInputException();
        }
        return new Deadline(description, by);
    }

    /**
     * Returns an Event input by user.
     *
     * @param commandArgs String input from user.
     * @return Event.
     * @throws DukeInputException If user leaves /at of Event empty.
     */
    public Event parseEventInput (String commandArgs) throws DukeInputException {
        UserInterface userInterface = new UserInterface();
        final String matchByPrefix = "/at";
        final int indexOfByPrefix = commandArgs.indexOf(matchByPrefix);
        String description = commandArgs.substring(0, indexOfByPrefix).trim();
        String at = commandArgs.substring(indexOfByPrefix).replace(matchByPrefix, "").trim();
        if (at.equals("")) {
            userInterface.printToUser("ERROR: Event at cannot be empty!");
            throw new DukeInputException();
        }
        LocalDateTime atStart, atEnd;
        final String matchToPrefix = "/to";
        String[] startString = at.replace(matchToPrefix, "|").split("\\|");
        try {
            atStart = parseLocalDateTime(startString[0].trim());
            atEnd = parseLocalDateTime(startString[1].trim());
        } catch (DateTimeParseException e) {
            userInterface.printToUser("ERROR: Date input is invalid!");
            throw new DukeInputException();
        }
        return new Event(description, atStart, atEnd);
    }

    private LocalDateTime parseLocalDateTime (String localDateTimeString) {
        System.out.println(localDateTimeString);
        return LocalDateTime.parse(localDateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    /**
     * Returns a Todo parsed from tasks.txt.
     *
     * @param data String array containing Todo data.
     * @return Todo.
     */
    private Todo parseTodo (String[] data) {
        boolean isDone;
        String description;
        isDone = Boolean.parseBoolean(data[1]);
        description = data[2];

        return new Todo(description, isDone);
    }

    /**
     * Returns an Event parsed from tasks.txt.
     *
     * @param data String array containing Event data.
     * @return Event.
     */
    private Event parseEvent (String[] data) {
        boolean isDone;
        String description;
        isDone = Boolean.parseBoolean(data[1]);
        String[] eventInfo = data[2].trim().split("\\|", 3);
        description = eventInfo[0];
        LocalDateTime start = LocalDateTime.parse(eventInfo[1]);
        LocalDateTime end = LocalDateTime.parse(eventInfo[2]);

        return new Event(description, start, end, isDone);
    }

    /**
     * Returns a Deadline parsed from tasks.txt.
     *
     * @param data String array containing Deadline data.
     * @return Deadline.
     */
    private Deadline parseDeadline (String[] data) {
        boolean isDone;
        String description;
        isDone = Boolean.parseBoolean(data[1]);
        String[] deadlineInfo = data[2].trim().split("\\|", 2);
        description = deadlineInfo[0];
        String byString = deadlineInfo[1];
        return new Deadline(description, LocalDateTime.parse(byString), isDone);
    }

    /**
     * Returns a boolean to determine if Duke should continue running.
     *
     * @param userInput User input from UserInterface class.
     * @return boolean exitFlag.
     * @throws DukeInputException If input from user is invalid.
     */
    public boolean parseCommand (String userInput) throws DukeInputException {
        UserInterface userInterface = new UserInterface();
        Storage storage = new Storage();
        TaskList taskList;
        try {
            taskList = new TaskList(storage.load("/tasks.txt"));
            final String[] split = userInput.trim().split("\\s+", 2);
            final String[] commandTypeAndParams = split.length == 2 ? split : new String[] {split[0], ""};
            final String commandType = commandTypeAndParams[0];
            final String commandArgs = commandTypeAndParams[1];

            switch (commandType) {
                case "bye":
                    taskList.save();
                    return true;
                case "list":
                    if (!commandArgs.isEmpty()) {
                        try {
                            userInterface.printTaskList(taskList.tasksOnDate(LocalDate.parse(commandArgs)));
                        } catch (DateTimeParseException e) {
                            userInterface.printToUser("ERROR: Invalid date given!");
                            throw new DukeInputException();
                        }
                    } else {
                        taskList.list();
                    }
                    break;
                case "done":
                    if (!commandArgs.isEmpty()) {
                        try {
                            int itemNum = Integer.parseInt(commandArgs);
                            taskList.setTaskStatus(itemNum, true);
                        } catch (NumberFormatException e) {
                            throw new DukeInputException();
                        }
                    }
                    break;
                case "todo":
                    try {
                        taskList.addTodo(commandArgs);
                        return false;
                    } catch (DukeInputException e) {
                        userInterface.printAddError();
                    }
                    break;
                case "event":
                    try {
                        taskList.addEvent(commandArgs);
                    } catch (DukeInputException e) {
                        userInterface.printAddError();
                    }
                    break;
                case "deadline":
                    try {
                        taskList.addDeadline(commandArgs);
                    } catch (DukeInputException e) {
                        userInterface.printAddError();
                    }
                    break;
                case "delete":
                    if (!commandArgs.isEmpty()) {
                        try {
                            int itemNum = Integer.parseInt(commandArgs);
                            taskList.deleteTask(itemNum);
                        } catch (NumberFormatException e) {
                            throw new DukeInputException();
                        }
                    }
                    break;
                case "find":
                    if (!commandArgs.isEmpty()) {
                        userInterface.printTaskList(taskList.findTasks(commandArgs));
                    } else {
                        userInterface.printToUser("ERROR: You need to give me a keyword to find!");
                        throw new DukeInputException();
                    }
                    break;
                default:
                    throw new DukeInputException();
            }
            return false;
        } catch (DukeInitializeException e) {
            userInterface.printInitError();
        }
        return false;
    }
}
