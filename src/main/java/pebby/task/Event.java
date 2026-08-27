package pebby.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** Represents a task that takes place between a supplied start and end time. */
public class Event extends Task {
    /** The date on which this event begins. */
    protected LocalDate from;
    /** The date on which this event ends. */
    protected LocalDate to;

    /** Creates an event using start and end dates in {@code MMM dd yyyy} format. */
    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDate.parse(from, DateTimeFormatter.ofPattern("MMM dd yyyy"));
        this.to = LocalDate.parse(to, DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }

    /** Returns the event's start time text. */
    public String getFrom() {
        return from.toString();
    }

    /** Returns the event's end time text. */
    public String getTo() {
        return to.toString();
    }

    /** Returns the user-facing representation of this event. */
    @Override
    public String toString() {
        return "[E] " + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
