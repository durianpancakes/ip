package duke.data.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime atStart;
    protected LocalDateTime atEnd;

    /**
     * Constructs an Event that is not done.
     *
     * @param description String containing the description of the Event.
     * @param start LocalDateTime start time of the Event.
     * @param end LocalDateTime end time of the Event.
     */
    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.atStart = start;
        this.atEnd = end;
    }

    /**
     * Constructs an Event with a specified done status.
     *
     * @param description String containing the description of the Event.
     * @param start LocalDateTime start time of the Event.
     * @param end LocalDateTime end time of the Event.
     * @param isDone Boolean containing the done status of the Event.
     */
    public Event(String description, LocalDateTime start, LocalDateTime end, boolean isDone) {
        super(description);
        this.atStart = start;
        this.atEnd = end;
        this.isDone = isDone;
    }

    /**
     * Getter method for the start time of the Event.
     *
     * @return LocalDateTime containing the start time of the Event.
     */
    public LocalDateTime getAtStart() {
        return atStart;
    }

    /**
     * Getter method for the end time of the Event.
     *
     * @return LocalDateTime containing the end time of the Event.
     */
    public LocalDateTime getAtEnd() {
        return atEnd;
    }

    /**
     * Returns the getTypeIcon() of the Event.
     *
     * @return String containing the Type Icon of an Event.
     */
    @Override
    public String getTypeIcon(){
        return "[E]";
    }

    /**
     * Returns the String representation of an Event.
     *
     * @return String representation of the Event.
     */
    @Override
    public String toString(){
        return super.toString() + " (at: "
                + atStart.format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a"))
                + " to "
                + atEnd.format(DateTimeFormatter.ofPattern("MMM d yyyy hh:mm a"))
                + ")";
    }
}
