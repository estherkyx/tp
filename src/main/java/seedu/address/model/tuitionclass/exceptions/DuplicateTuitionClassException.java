package seedu.address.model.tuitionclass.exceptions;

public class DuplicateTuitionClassException extends RuntimeException {
    public DuplicateTuitionClassException() {
        super("Operation would result in duplicate tuition classes (same day and time)");
    }
}