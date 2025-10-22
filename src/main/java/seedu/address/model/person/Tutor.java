package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Tutor in the address book.
 */
public class Tutor extends Person {

    public Tutor(Category category, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(category, name, phone, email, address, tags);
    }

    public Tutor(PersonId id, Category category, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(id, category, name, phone, email, address, tags);
    }
}
