package seedu.address.model.person;

import java.util.Objects;
import java.util.UUID;

/**
 * Immutable identifier for a {@link Person}. Wraps a UUID for each person.
 */
public final class PersonId {

    private final String value;

    private PersonId(String value) {
        this.value = value;
    }

    /**
     * Creates a new {@code PersonId} with a randomly generated UUID value.
     */
    public static PersonId newId() {
        return new PersonId(UUID.randomUUID().toString());
    }

    /**
     * Returns the string form of this identifier.
     */
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonId)) {
            return false;
        }
        PersonId personId = (PersonId) o;
        return Objects.equals(value, personId.value);
    }

    @Override
    public String toString() {
        return value;
    }
}


