/** Represents a task that takes place between a supplied start and end time. */
public class Event extends Task{
    protected String from;
    protected String to;

    public Event(String description, String from, String to){
        super(description);
        this.from = from;
        this.to = to;
    }

    /** Returns the event's start time text. */
    public String getFrom() {
        return from;
    }

    /** Returns the event's end time text. */
    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E] " + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
