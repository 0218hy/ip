package pebby.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** Represents a task that takes place between a supplied start and end time. */
public class Event extends Task {
    private final LocalDate from;
    private final LocalDate to;

    /** Creates an event using the supplied description, start date, and end date. */
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

    /**
     * Returns whether this event occurs on the specified date, including both endpoints.
     */
    public boolean isOn(LocalDate date) {
        return !date.isBefore(from) && !date.isAfter(to);
    }

    @Override
    public String toString() {
        return "[E] " + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
