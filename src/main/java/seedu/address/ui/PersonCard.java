package seedu.address.ui;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label category;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label parent;
    @FXML
    private Label children;
    @FXML
    private Label tuitionClass;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        this(person, displayedIndex, null, null);
    }

    /**
     * Creates a {@code PersonCode} with the given {@code Person}, index to display, and lookup functions.
     */
    public PersonCard(Person person, int displayedIndex, Function<PersonId, Optional<Person>> personLookup,
                      Function<Person, List<TuitionClass>> tuitionClassLookup) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        category.setText(person.getCategory().toString());
        String categoryType = person.getCategory().toString().toLowerCase();
        switch (categoryType) {
        case "student":
            category.getStyleClass().add("category-student");
            break;
        case "parent":
            category.getStyleClass().add("category-parent");
            break;
        case "tutor":
            category.getStyleClass().add("category-tutor");
            break;
        default:
            category.getStyleClass().add("category-label");
            break;
        }

        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        // Ensure relationship rows don't take space unless shown
        parent.setVisible(false);
        parent.setManaged(false);
        children.setVisible(false);
        children.setManaged(false);
        tuitionClass.setVisible(false);
        tuitionClass.setManaged(false);

        // Set up parent/children information
        setupParentChildrenInfo(personLookup);

        // Set up tuition class information
        setupTuitionClassInfo(tuitionClassLookup);
    }

    /**
     * Sets up the parent and children information display.
     */
    private void setupParentChildrenInfo(Function<PersonId, Optional<Person>> personLookup) {
        if (personLookup == null) {
            parent.setVisible(false);
            parent.setManaged(false);
            children.setVisible(false);
            children.setManaged(false);
            return;
        }
        if (person instanceof Student) {
            Student student = (Student) person;
            PersonId parentId = student.getParentId();
            if (parentId != null) {
                Optional<Person> parentPerson = personLookup.apply(parentId);
                if (parentPerson.isPresent()) {
                    parent.setText("Parent: " + parentPerson.get().getName().fullName);
                    parent.setVisible(true);
                    parent.setManaged(true);
                } else {
                    parent.setVisible(false);
                    parent.setManaged(false);
                }
            } else {
                parent.setVisible(false);
                parent.setManaged(false);
            }
            children.setVisible(false);
            children.setManaged(false);
        } else if (person instanceof Parent) {
            Parent parentPerson = (Parent) person;
            List<PersonId> childrenIds = parentPerson.getChildrenIds().stream().toList();
            if (!childrenIds.isEmpty()) {
                StringBuilder childrenText = new StringBuilder("Children: ");
                for (int i = 0; i < childrenIds.size(); i++) {
                    if (i > 0) {
                        childrenText.append(", ");
                    }
                    Optional<Person> child = personLookup.apply(childrenIds.get(i));
                    if (child.isPresent()) {
                        childrenText.append(child.get().getName().fullName);
                    } else {
                        childrenText.append("Unknown");
                    }
                }
                children.setText(childrenText.toString());
                children.setVisible(true);
                children.setManaged(true);
            } else {
                children.setVisible(false);
                children.setManaged(false);
            }
            parent.setVisible(false);
            parent.setManaged(false);
        } else {
            // For tutors or other types
            parent.setVisible(false);
            parent.setManaged(false);
            children.setVisible(false);
            children.setManaged(false);
        }
    }

    /**
     * Sets up the tuition class information display.
     */
    private void setupTuitionClassInfo(Function<Person, List<TuitionClass>> tuitionClassLookup) {
        if (tuitionClassLookup == null) {
            tuitionClass.setVisible(false);
            tuitionClass.setManaged(false);
            return;
        }

        if (person instanceof Student) {
            Student student = (Student) person;
            Optional<TuitionClass> classOptional = student.findTuitionClass(tuitionClassLookup.apply(student));

            if (classOptional.isPresent()) {
                tuitionClass.setText("Class: " + classOptional.get().toSimpleString());
                tuitionClass.setVisible(true);
                tuitionClass.setManaged(true);
            } else {
                tuitionClass.setVisible(false);
                tuitionClass.setManaged(false);
            }
        } else if (person instanceof Tutor) {
            Tutor tutor = (Tutor) person;
            List<TuitionClass> classes = tuitionClassLookup.apply(tutor);
            if (!classes.isEmpty()) {
                String classInfo = classes.stream()
                        .map(TuitionClass::toSimpleString)
                        .collect(Collectors.joining(", "));
                tuitionClass.setText("Classes: " + classInfo);
                tuitionClass.setVisible(true);
                tuitionClass.setManaged(true);
            } else {
                tuitionClass.setVisible(false);
                tuitionClass.setManaged(false);
            }
        } else {
            // For parents or other types
            tuitionClass.setVisible(false);
            tuitionClass.setManaged(false);
        }
    }
}
