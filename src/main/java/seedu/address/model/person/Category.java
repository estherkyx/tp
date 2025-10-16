package seedu.address.model.person;

/**
 * Enumerates the allowed categories for a {@link Person}.
 */
public enum Category {
    STUDENT,
    TUTOR,
    PARENT;

    /**
     * Parses a category string in a case-insensitive manner.
     * Accepts only "student", "tutor", or "parent" (any case).
     *
     * @throws IllegalArgumentException if the value is not a valid category
     */
    public static Category fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        String normalized = value.trim().toLowerCase();
        switch (normalized) {
        case "student":
            return STUDENT;
        case "tutor":
            return TUTOR;
        case "parent":
            return PARENT;
        default:
            throw new IllegalArgumentException("Invalid category");
        }
    }

    @Override
    public String toString() {
        switch (this) {
        case STUDENT:
            return "student";
        case TUTOR:
            return "tutor";
        case PARENT:
            return "parent";
        default:
            return name().toLowerCase();
        }
    }
}


