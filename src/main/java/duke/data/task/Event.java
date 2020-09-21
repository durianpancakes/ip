package duke.data.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime atStart;
    protected LocalDateTime atEnd;

    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.atStart = start;
        this.atEnd = end;
    }

    public Event(String description, LocalDateTime start, LocalDateTime end, boolean isDone) {
        super(description);
        this.atStart = start;
        this.atEnd = end;
        this.isDone = isDone;
    }

    public LocalDateTime getAtStart() {
        return atStart;
    }

    public LocalDateTime getAtEnd() {
        return atEnd;
    }

    @Override
    public String getTypeIcon(){
        return "[E]";
    }

    @Override
    public String toString(){
        return super.toString() + " (at: "
                + atStart.format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a"))
                + " to "
                + atEnd.format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a"))
                + ")";
    }
}
