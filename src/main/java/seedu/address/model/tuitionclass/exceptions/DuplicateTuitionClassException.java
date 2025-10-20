package seedu.address.model.tuitionclass.exceptions;

/**
 * Signals that the operation will result in duplicate Tuition Classes.
 * Duplicate Tuition Classes are considered those with the same day and time.
 */
public class DuplicateTuitionClassException extends RuntimeException {

    /**
     * Constructs a {@code DuplicateTuitionClassException} with a default error message.
     */
    public DuplicateTuitionClassException() {
        super("Operation would result in duplicate tuition classes (same day and time)");
    }
}
