package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Category;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonFactory;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.tag.Tag;
import seedu.address.model.tuitionclass.ClassId;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_CATEGORY + "*CATEGORY] "
            + "[" + PREFIX_NAME + "*NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CATEGORY + "student "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_CATEGORY_CHANGED_WARNING =
            "\nWarning: Category was changed. All relationships (parent/child links, classes) have been removed.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor, model);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        if (editedPerson instanceof Student) {
            Student editedStudent = (Student) editedPerson;
            if (editedStudent.getParentId() != null) {
                Optional<Person> parentOpt = model.getAddressBook().getPersonList().stream()
                    .filter(p -> p.getId().equals(editedStudent.getParentId())).findFirst();
                if (parentOpt.isPresent() && parentOpt.get() instanceof Parent) {
                    Parent parent = (Parent) parentOpt.get();
                    model.setPerson(parentOpt.get(), parent);
                }
            }
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        String success = String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));
        boolean hasChangedCategory = !personToEdit.getCategory().equals(editedPerson.getCategory());
        return new CommandResult(success + (hasChangedCategory ? MESSAGE_CATEGORY_CHANGED_WARNING : ""));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor,
                                            Model model) {
        assert personToEdit != null;

        Category updatedCategory = editPersonDescriptor.getCategory().orElse(personToEdit.getCategory());
        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        // Preserve the Person subclass and relationship data by using PersonFactory with the existing ID
        Person editedPerson = PersonFactory.createPerson(personToEdit.getId(), updatedCategory, updatedName,
                updatedPhone, updatedEmail, updatedAddress, updatedTags);

        // Preserve relationship data for specific subclasses
        if (personToEdit instanceof Student && editedPerson instanceof Student) {
            Student originalStudent = (Student) personToEdit;
            Student editedStudent = (Student) editedPerson;

            // Preserve parent and tutor relationships
            if (originalStudent.getParentId() != null) {
                editedStudent.setParentId(originalStudent.getParentId());
            }

        } else if (personToEdit instanceof Parent && editedPerson instanceof Parent) {
            Parent originalParent = (Parent) personToEdit;
            Parent editedParent = (Parent) editedPerson;

            // Preserve children relationships
            for (PersonId childId : originalParent.getChildrenIds()) {
                editedParent.addChildId(childId);
            }
        } else if (personToEdit instanceof Tutor && editedPerson instanceof Tutor) {
            Tutor originalTutor = (Tutor) personToEdit;
            Tutor editedTutor = (Tutor) editedPerson;

        } else { // when personToEdit and editedPerson are not the same type
            cleanupOldRelationships(personToEdit, model);
        }

        return editedPerson;
    }

    /**
     * Cleans up old relationships when a person's category changes.
     * Removes the person from their old relationships and updates related persons.
     */
    private static void cleanupOldRelationships(Person personToEdit, Model model) {
        List<Person> allPersons = model.getAddressBook().getPersonList();
        List<TuitionClass> allClasses = model.getTuitionClassList();

        if (personToEdit instanceof Student) {
            Student student = (Student) personToEdit;

            // Remove from parent's children list
            if (student.getParentId() != null) {
                Optional<Person> parentOpt = allPersons.stream()
                    .filter(p -> p.getId().equals(student.getParentId()))
                    .findFirst();
                if (parentOpt.isPresent() && parentOpt.get() instanceof Parent) {
                    Parent parent = (Parent) parentOpt.get();
                    parent.removeChildId(student.getId());
                    model.setPerson(parentOpt.get(), parent);
                }
            }

            // Remove from classes
            if (student.getClassId().isPresent()) {
                ClassId classId = student.getClassId().get();
                TuitionClass tuitionClass = allClasses.stream()
                        .filter(c -> c.getClassId().equals(classId))
                        .findFirst().get();
                tuitionClass.removeStudentId(student.getId());
                model.setTuitionClass(tuitionClass, tuitionClass);
            }

        } else if (personToEdit instanceof Parent) {
            Parent parent = (Parent) personToEdit;

            // Remove from all children's parent references
            for (PersonId childId : parent.getChildrenIds()) {
                Optional<Person> childOpt = allPersons.stream()
                    .filter(p -> p.getId().equals(childId))
                    .findFirst();
                if (childOpt.isPresent() && childOpt.get() instanceof Student) {
                    Student child = (Student) childOpt.get();
                    if (child.getParentId() != null && child.getParentId().equals(parent.getId())) {
                        child.clearParent();
                        model.setPerson(childOpt.get(), child);
                    }
                }
            }

        } else if (personToEdit instanceof Tutor) {
            Tutor tutor = (Tutor) personToEdit;

            // remove tutor from classes
            List<TuitionClass> tutorClasses = allClasses.stream()
                    .filter(c -> tutor.getId().equals(c.getTutorId())).toList();
            if (!tutorClasses.isEmpty()) {
                for (TuitionClass tc : tutorClasses) {
                    tc.removeTutorId();
                    model.setTuitionClass(tc, tc);
                }
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Category category;
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setCategory(toCopy.category);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(category, name, phone, email, address, tags);
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public Optional<Category> getCategory() {
            return Optional.ofNullable(category);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(category, otherEditPersonDescriptor.category)
                    && Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("category", category)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .toString();
        }
    }
}
