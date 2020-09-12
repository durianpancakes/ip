package duke.task;

public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, String by, boolean isDone) {
        super(description);
        this.by = by;
        this.isDone = isDone;
    }

    @Override
    public String getTypeIcon(){
        return "[D]";
    }

    @Override
    public String toString(){
        return super.toString() + " (by: " + by + ")";
    }
}
