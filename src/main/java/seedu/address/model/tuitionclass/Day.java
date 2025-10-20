package seedu.address.model.tuitionclass;

import static java.util.Objects.requireNonNull;

/**
 * Represents the day of the week for a tuition class.
 */
public enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    public static final String MESSAGE_CONSTRAINTS = "Day must be a valid day of the week (e.g., MONDAY, Tuesday).";

    /**
     * Returns the {@code Day} enum corresponding to the given string, case-insensitively.
     * @throws IllegalArgumentException if the given string is not a valid day.
     */
    public static Day fromString(String dayString) {
        requireNonNull(dayString);
        for (Day day : Day.values()) {
            if (day.name().equalsIgnoreCase(dayString.trim())) {
                return day;
            }
        }
        throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
    }

    @Override
    public String toString() {
        // Returns the name in a user-friendly format (e.g., "Monday")
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}