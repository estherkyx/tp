package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Stable identity
    private final PersonId id;

    // Category
    private final Category category;

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Category category, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(category, name, phone, email, address, tags);
        this.id = PersonId.newId();
        this.category = category;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    /**
     * Overloaded constructor to explicitly provide a {@link PersonId}.
     */
    public Person(PersonId id, Category category, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(id, category, name, phone, email, address, tags);
        this.id = id;
        this.category = category;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    public PersonId getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same identity fields.
     * Identity is defined as matching category and name.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().toString().trim().replaceAll("\\s+", " ")
                .equalsIgnoreCase(getName().toString().trim().replaceAll("\\s+", " "));

    }

    /**
     * Returns ID of the parent for the specified student.
     * This is a default implementation indicating that this person does not have an
     * associated parent. This method is intended to be overridden by subclass {@link Student}.
     *
     * @return PersonId of associated parent.
     */
    public PersonId getParentId() {
        // Default implementation for non-students
        return null;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return category.equals(otherPerson.category)
                && name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(category, name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", id)
                .add("category", category)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
