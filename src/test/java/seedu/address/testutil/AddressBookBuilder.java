package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Category;
import seedu.address.model.person.Person;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        addressBook.addPerson(person);
        return this;
    }

    /**
     * Adds a new {@code TuitionClass} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withTuitionClass(TuitionClass tuitionClass) {
        addressBook.addTuitionClass(tuitionClass);
        return this;
    }


    /**
     * Adds contacts that are not in the specified {@code Category} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withoutCategory(Category toRemove) {
        addressBook = new AddressBook();
        for (Person p : getTypicalAddressBook().getPersonList()) {
            if (p.getCategory() != toRemove) {
                addressBook.addPerson(p);
            }
        }
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
