package duke.parser;

import duke.common.Messages;
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
        String byString = commandArgs.substring(indexOfByPrefix).replace(matchByPrefix, "").trim();
        if (stringIsEmpty(byString)) {
            userInterface.printIncompleteDeadlineError();
            throw new DukeInputException();
        }
        LocalDateTime by;
        try {
            by = parseLocalDateTime(byString);
        } catch (DateTimeParseException e) {
            userInterface.printDateInputErrorMessage();
            throw new DukeInputException();
        }
        return new Deadline(description, by);
    }

    /**
     * Helper method to check if a string is empty
     *
     * @param string String to be checked
     * @return boolean, TRUE if string is empty, FALSE if string is not empty
     */
    private boolean stringIsEmpty (String string) {
        return string.equals("");
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
        if (stringIsEmpty(at)) {
            userInterface.printIncompleteEventError();
            throw new DukeInputException();
        }
        LocalDateTime atStart, atEnd;
        final String matchToPrefix = "/to";
        String[] startString = at.replace(matchToPrefix, "|").split("\\|");
        try {
            atStart = parseLocalDateTime(startString[0].trim());
            atEnd = parseLocalDateTime(startString[1].trim());
        } catch (DateTimeParseException e) {
            userInterface.printDateInputErrorMessage();
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
                    processByeCommand(taskList);
                    return true;
                case "list":
                    processListCommand(userInterface, taskList, commandArgs);
                    break;
                case "done":
                    processDoneCommand(userInterface,taskList, commandArgs);
                    break;
                case "todo":
                    processTodoCommand(userInterface, taskList, commandArgs);
                    break;
                case "event":
                    processEventCommand(userInterface, taskList, commandArgs);
                    break;
                case "deadline":
                    processDeadlineCommand(userInterface, taskList, commandArgs);
                    break;
                case "delete":
                    processDeleteCommand(userInterface, taskList, commandArgs);
                    break;
                case "find":
                    processFindCommand(userInterface, taskList, commandArgs);
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

    /**
     * Processes the Bye command issued by the user.
     *
     * @param taskList TaskList instance to be saved
     */
    public void processByeCommand (TaskList taskList) {
        taskList.save();
    }

    /**
     * Processes the Find command issued by the user.
     *
     * @param userInterface UserInterface instance passed from parseCommand
     * @param taskList TaskList instance passed from parseCommand
     * @param commandArgs String containing the user's command argument to be parsed
     * @throws DukeInputException If the user gives an empty keyword
     */
    public void processFindCommand (UserInterface userInterface, TaskList taskList, String commandArgs) throws DukeInputException {
        if (!commandArgs.isEmpty()) {
            userInterface.printTaskList(taskList.findTasks(commandArgs));
        } else {
            userInterface.printToUser(Messages.MESSAGE_INCOMPLETE_KEYWORD);
            throw new DukeInputException();
        }
    }

    /**
     * Processes the Delete command issued by the user.
     *
     * @param userInterface UserInterface instance passed from parseCommand
     * @param taskList TaskList instance passed from parseCommand
     * @param commandArgs String containing the user's command argument to be parsed
     * @throws DukeInputException If the user gives an invalid integer
     */
    public void processDeleteCommand (UserInterface userInterface, TaskList taskList, String commandArgs) throws DukeInputException {
        if (!commandArgs.isEmpty()) {
            try {
                int itemNum = Integer.parseInt(commandArgs);
                taskList.deleteTask(itemNum);
            } catch (NumberFormatException e) {
                userInterface.printToUser(Messages.MESSAGE_INVALID_INTEGER);
                throw new DukeInputException();
            }
        }
    }

    /**
     * Processes the Deadline command issued by the user.
     *
     * @param userInterface UserInterface instance passed from parseCommand
     * @param taskList TaskList instance passed from parseCommand
     * @param commandArgs String containing the user's command argument to be parsed
     */
    public void processDeadlineCommand (UserInterface userInterface, TaskList taskList, String commandArgs) {
        try {
            taskList.addDeadline(commandArgs);
        } catch (DukeInputException e) {
            userInterface.printAddError();
        }
    }

    /**
     * Processes the Event command issued by the user.
     *
     * @param userInterface UserInterface instance passed from parseCommand
     * @param taskList TaskList instance passed from parseCommand
     * @param commandArgs String containing the user's command argument to be parsed
     */
    public void processEventCommand (UserInterface userInterface, TaskList taskList, String commandArgs) {
        try {
            taskList.addEvent(commandArgs);
        } catch (DukeInputException e) {
            userInterface.printAddError();
        }
    }

    /**
     * Processes the Todo command issued by the user.
     *
     * @param userInterface UserInterface instance passed from parseCommand
     * @param taskList TaskList instance passed from parseCommand
     * @param commandArgs String containing the user's command argument to be parsed
     */
    public void processTodoCommand (UserInterface userInterface, TaskList taskList, String commandArgs) {
        try {
            taskList.addTodo(commandArgs);
        } catch (DukeInputException e) {
            userInterface.printAddError();
        }
    }

    /**
     * Processes the Done command issued by the user.
     *
     * @param userInterface UserInterface instance passed from parseCommand
     * @param taskList TaskList instance passed from parseCommand
     * @param commandArgs String containing the user's command argument to be parsed
     * @throws DukeInputException If the user gives an invalid integer
     */
    public void processDoneCommand (UserInterface userInterface, TaskList taskList, String commandArgs) throws DukeInputException {
        if (!commandArgs.isEmpty()) {
            try {
                int itemNum = Integer.parseInt(commandArgs);
                taskList.setTaskStatus(itemNum, true);
            } catch (NumberFormatException e) {
                userInterface.printToUser(Messages.MESSAGE_INVALID_INTEGER);
                throw new DukeInputException();
            }
        }
    }

    /**
     * Processes the List command issued by the user
     *
     * @param userInterface UserInterface instance passed from parseCommand
     * @param taskList TaskList instance passed from parseCommand
     * @param commandArgs String containing the user's command argument to be parsed
     * @throws DukeInputException If the user gives an invalid date
     */
    public void processListCommand (UserInterface userInterface, TaskList taskList, String commandArgs) throws DukeInputException {
        if (!commandArgs.isEmpty()) {
            try {
                userInterface.printTaskList(taskList.tasksOnDate(LocalDate.parse(commandArgs)));
            } catch (DateTimeParseException e) {
                userInterface.printToUser(Messages.MESSAGE_INVALID_DATE);
                throw new DukeInputException();
            }
        } else {
            taskList.list();
        }
    }
}
