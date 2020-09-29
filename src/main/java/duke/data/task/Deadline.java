package duke.data.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Subclass of Task
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    /**
     * Constructs a Deadline that is not done.
     *
     * @param description String containing description of the Deadline.
     * @param by          LocalDateTime of the Deadline.
     */
    public Deadline (String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Constructs a Deadline with a specified done status.
     *
     * @param description String containing description of the Deadline.
     * @param by          LocalDateTime of the Deadline.
     * @param isDone      Boolean containing the done status of the Deadline.
     */
    public Deadline (String description, LocalDateTime by, boolean isDone) {
        super(description);
        this.by = by;
        this.isDone = isDone;
    }

    /**
     * Getter method for the by of the Deadline.
     *
     * @return LocalDateTime by of the Deadline.
     */
    public LocalDateTime getBy () {
        return this.by;
    }

    /**
     * Returns the getTypeIcon() of the Deadline.
     *
     * @return String containing the Type Icon of an Deadline.
     */
    @Override
    public String getTypeIcon () {
        return "[D]";
    }

    /**
     * Returns the String representation of an Deadline.
     *
     * @return String representation of the Deadline.
     */
    @Override
    public String toString () {
        return super.toString()
                + " (by: "
                + by.format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a"))
                + ")";
    }
}
