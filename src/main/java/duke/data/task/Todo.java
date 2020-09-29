package duke.data.task;

/**
 * Subclass of Task
 */
public class Todo extends Task {
    /**
     * Constructs a Todo that is not done.
     *
     * @param taskTitle String containing the description of the Todo.
     */
    public Todo (String taskTitle) {
        super(taskTitle);
    }

    /**
     * Constructs a Todo with a specified done status.
     *
     * @param taskTitle String containing the description of the Todo.
     * @param isDone    boolean containing the done status of the Todo.
     */
    public Todo (String taskTitle, boolean isDone) {
        super(taskTitle);
        this.isDone = isDone;
    }

    /**
     * Returns a String containing the type icon of the Todo.
     *
     * @return String containing the type icon of the Todo.
     */
    @Override
    public String getTypeIcon () {
        return "[T]";
    }

    /**
     * Returns the String representation of a Todo.
     *
     * @return String containing the representation of a Todo.
     */
    @Override
    public String toString () {
        return super.toString();
    }
}
