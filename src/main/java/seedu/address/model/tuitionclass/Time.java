package seedu.address.model.tuitionclass;

import static java.util.Objects.requireNonNull;

/**
 * Represents the time slot for a tuition class.
 * Times are represented in 24-hour format (e.g., H12 for 12:00, H14 for 14:00).
 */
public enum Time {
    H12, H14, H16, H18, H20; // Represents 12:00, 14:00, 16:00, 18:00, 20:00

    public static final String MESSAGE_CONSTRAINTS = "Time must be one of the following: H12, H14, H16, H18, H20";

    /**
     * Returns the {@code Time} enum corresponding to the given string, case-insensitively.
     * @throws IllegalArgumentException if the given string is not a valid time.
     */
    public static Time fromString(String timeString) {
        requireNonNull(timeString);
        for (Time time : Time.values()) {
            if (time.name().equalsIgnoreCase(timeString.trim())) {
                return time;
            }
        }
        throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns a user-friendly string representation of the time (e.g., "1200").
     */
    public String toDisplayString() {
        return this.name().substring(1) + "00";
    }

    @Override
    public String toString() {
        return name(); // e.g., "H12"
    }
}
