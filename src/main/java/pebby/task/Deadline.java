package pebby.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/** Represents a task that must be completed by a supplied deadline. */
public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT_DATE_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd uuuu", Locale.ENGLISH);
    private final LocalDate by;

    public Deadline(String description, String by) {
        this(description, parseDate(by));
    }

    /** Creates a deadline using an already validated date. */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Converts a date argument in {@code yyyy-MM-dd} format into a LocalDate.
     *
     * @throws IllegalArgumentException if the argument is not a real date in the expected format
     */
    public static LocalDate parseDate(String dateText) {
        try {
            return LocalDate.parse(dateText);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(
                    "Please use a valid date in yyyy-MM-dd format, for example 2019-12-02.");
        }
    }

    /** Returns the deadline in ISO format so it can be saved and loaded reliably. */
    public String getBy() {
        return by.toString();
    }

    /** Returns whether this deadline falls on the specified date. */
    public boolean isOn(LocalDate date) {
        return by.equals(date);
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + by.format(OUTPUT_DATE_FORMAT) + ")";
    }
}
