package duke.task;

public class Event extends Task {
    protected String at;

    public Event(String description, String at) {
        super(description);
        this.at = at;
    }

    public Event(String description, String at, boolean isDone) {
        super(description);
        this.at = at;
        this.isDone = isDone;
    }

    @Override
    public String getTypeIcon(){
        return "[E]";
    }

    @Override
    public String toString(){
        return super.toString() + " (at: " + at + ")";
    }
}
