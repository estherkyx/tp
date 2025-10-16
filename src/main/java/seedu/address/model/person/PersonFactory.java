package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Factory class for creating Person objects based on their category.
 */
public class PersonFactory {

    /**
     * Creates a Person object of the appropriate type based on the given category.
     *
     * @param category the category of the person
     * @param name the name of the person
     * @param phone the phone number of the person
     * @param email the email of the person
     * @param address the address of the person
     * @param tags the tags associated with the person
     * @return a Person object of the appropriate type
     */
    public static Person createPerson(Category category, Name name, Phone phone, Email email,
                                    Address address, Set<Tag> tags) {
        switch (category) {
        case STUDENT:
            return new Student(category, name, phone, email, address, tags);
        case PARENT:
            return new Parent(category, name, phone, email, address, tags);
        case TUTOR:
            return new Tutor(category, name, phone, email, address, tags);
        default:
            throw new IllegalArgumentException("Unknown category: " + category);
        }
    }

    /**
     * Creates a Person object of the appropriate type based on the given category with a specific ID.
     *
     * @param id the ID of the person
     * @param category the category of the person
     * @param name the name of the person
     * @param phone the phone number of the person
     * @param email the email of the person
     * @param address the address of the person
     * @param tags the tags associated with the person
     * @return a Person object of the appropriate type
     */
    public static Person createPerson(PersonId id, Category category, Name name, Phone phone,
                                    Email email, Address address, Set<Tag> tags) {
        switch (category) {
        case STUDENT:
            return new Student(id, category, name, phone, email, address, tags);
        case PARENT:
            return new Parent(id, category, name, phone, email, address, tags);
        case TUTOR:
            return new Tutor(id, category, name, phone, email, address, tags);
        default:
            throw new IllegalArgumentException("Unknown category: " + category);
        }
    }
}
