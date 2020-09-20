package duke.data.task;

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

    public String getAt() {
        return at;
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
