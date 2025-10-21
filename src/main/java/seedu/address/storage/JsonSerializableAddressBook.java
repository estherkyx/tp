package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_CLASS = "Tuition classes list contains duplicate class(es).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedTuitionClass> tuitionClasses = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("tuitionClasses") List<JsonAdaptedTuitionClass> tuitionClasses) {
        this.persons.addAll(persons);
        if (tuitionClasses != null) {
            this.tuitionClasses.addAll(tuitionClasses);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).toList());
        tuitionClasses.addAll(source.getTuitionClassList().stream()
                .map(JsonAdaptedTuitionClass::new).toList());
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }

        for (JsonAdaptedTuitionClass jsonAdaptedClass : tuitionClasses) {
            TuitionClass tuitionClass = jsonAdaptedClass.toModelType();
            if (addressBook.hasTuitionClass(tuitionClass)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CLASS);
            }
            addressBook.addTuitionClass(tuitionClass);
        }
        return addressBook;
    }
}
