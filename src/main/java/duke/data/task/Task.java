package duke.data.task;

public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task that is not done.
     *
     * @param description String containing description of the Task.
     */
    public Task (String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Getter method for the description of the Task.
     *
     * @return String containing the description of the Task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for the description of the Task.
     *
     * @param description String containing the description of the Task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method for the done status of the Task.
     *
     * @return Boolean of the done status of the Task.
     */
    public boolean getDone() {
        return isDone;
    }

    /**
     * Setter method for the done status of the Task.
     *
     * @param isDone Boolean of the done status intended for the Task.
     */
    public void setDone(boolean isDone){
        this.isDone = isDone;
    }

    /**
     * Getter method for the done status icon of the Task.
     *
     * @return String containing the status icon.
     */
    public String getStatusIcon(){
        return "[" + (isDone ? "\u2713" : "\u2718") + "]";
    }

    /**
     * Returns a String containing the type icon of the Task.
     *
     * @return String containing the type icon of the Task.
     */
    public String getTypeIcon() {
        return null;
    }

    /**
     * Returns the String representation of a Task.
     *
     * @return String containing the representation of a Task.
     */
    @Override
    public String toString(){
        return getTypeIcon() + getStatusIcon() + description;
    }
}
