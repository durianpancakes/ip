package duke.data.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, LocalDateTime by, boolean isDone) {
        super(description);
        this.by = by;
        this.isDone = isDone;
    }

    public LocalDateTime getBy() {
        return this.by;
    }

    @Override
    public String getTypeIcon(){
        return "[D]";
    }

    @Override
    public String toString(){
        return super.toString()
                + " (by: "
                + by.format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a"))
                + ")";
    }
}
