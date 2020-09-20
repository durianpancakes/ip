package duke.parser;

import duke.data.exception.DukeInputException;
import duke.data.task.*;
import duke.storage.Storage;
import duke.ui.UserInterface;

public class Parser {
    public static Task parseTaskFromString(String encodedTask) {
        final String[] data = encodedTask.trim().split("\\|", 3);

        switch(data[0]) {
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

    public static Deadline parseDeadlineInput(String commandArgs) {
        final String matchByPrefix = "/by";
        final int indexOfByPrefix = commandArgs.indexOf(matchByPrefix);
        String description = commandArgs.substring(0, indexOfByPrefix).trim();
        String by = commandArgs.substring(indexOfByPrefix).replace("/by", "").trim();
        if(by.equals("")) {
            UserInterface.printToUser("Deadline by cannot be empty!");
        }
        return new Deadline(description, by);
    }

    public static Event parseEventInput(String commandArgs) {
        final String matchByPrefix = "/at";
        final int indexOfByPrefix = commandArgs.indexOf(matchByPrefix);
        String description = commandArgs.substring(0, indexOfByPrefix).trim();
        String at = commandArgs.substring(indexOfByPrefix).replace("/at", "").trim();
        if(at.equals("")) {
            UserInterface.printToUser("Event at cannot be empty!");
        }
        return new Event(description, at);
    }

    private static Todo parseTodo(String[] data) {
        boolean isDone;
        String description;
        isDone = Boolean.parseBoolean(data[1]);
        description = data[2];

        return new Todo(description, isDone);
    }

    private static Event parseEvent(String[] data) {
        boolean isDone;
        String description;
        isDone = Boolean.parseBoolean(data[1]);
        String[] eventInfo = data[2].trim().split("\\|", 2);
        description = eventInfo[0];
        String at = eventInfo[1];
        return new Event(description, at, isDone);
    }

    private static Deadline parseDeadline(String[] data) {
        boolean isDone;
        String description;
        isDone = Boolean.parseBoolean(data[1]);
        String[] deadlineInfo = data[2].trim().split("\\|", 2);
        description = deadlineInfo[0];
        String by = deadlineInfo[1];
        return new Deadline(description, by, isDone);
    }

    public static boolean parseCommand(String userInput) throws DukeInputException {
        final String[] split = userInput.trim().split("\\s+", 2);
        final String[] commandTypeAndParams = split.length == 2 ? split : new String[]{split[0], ""};
        final String commandType = commandTypeAndParams[0];
        final String commandArgs = commandTypeAndParams[1];

        switch(commandType) {
        case "bye":
            TaskList.save();
            return true;
        case "list":
            TaskList.list();
            break;
        case "done":
            if(!commandArgs.isEmpty()){
                try {
                    int itemNum = Integer.parseInt(commandArgs);
                    TaskList.setTaskStatus(itemNum, true);
                } catch (NumberFormatException e){
                    throw new DukeInputException();
                }
            }
            break;
        case "todo":
            try {
                TaskList.addTodo(commandArgs);
                return false;
            } catch (DukeInputException e) {
                UserInterface.printAddError();
            }
            break;
        case "event":
            try {
                TaskList.addEvent(commandArgs);
            } catch (DukeInputException e) {
                UserInterface.printAddError();
            }
            break;
        case "deadline":
            try {
                TaskList.addDeadline(commandArgs);
            } catch (DukeInputException e) {
                UserInterface.printAddError();
            }
            break;
        case "delete":
            if(!commandArgs.isEmpty()){
                try {
                    int itemNum = Integer.parseInt(commandArgs);
                    TaskList.deleteTask(itemNum);
                } catch (NumberFormatException e){
                    throw new DukeInputException();
                }
            }
            break;
        default:
            throw new DukeInputException();
        }
        return false;
    }
}
