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
        hide(parent);
        hide(children);
        hide(tuitionClass);

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
            hide(parent);
            hide(children);
            return;
        }
        if (person instanceof Student) {
            handleStudentRelationships((Student) person, personLookup);
        } else if (person instanceof Parent) {
            handleParentRelationships((Parent) person, personLookup);
        } else {
            // For tutors or other types
            hide(parent);
            hide(children);
        }
    }

    /**
     * Shows the student's parent if found; hides children for students.
     *
     * @param student the student to render relationships for
     * @param personLookup resolves a {@link PersonId} to an optional {@link Person}
     */
    private void handleStudentRelationships(Student student,
                                            Function<PersonId, Optional<Person>> personLookup) {
        PersonId parentId = student.getParentId();
        if (parentId != null) {
            Optional<Person> parentPerson = personLookup.apply(parentId);
            if (parentPerson.isPresent()) {
                setTextAndShow(parent, "Parent: " + parentPerson.get().getName().fullName);
            } else {
                hide(parent);
            }
        } else {
            hide(parent);
        }
        hide(children);
    }

    /**
     * Shows the parent's children list; hides the parent label for parents.
     *
     * @param parentPerson the parent to render relationships for
     * @param personLookup resolves a {@link PersonId} to an optional {@link Person}
     */
    private void handleParentRelationships(Parent parentPerson,
                                           Function<PersonId, Optional<Person>> personLookup) {
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
            setTextAndShow(children, childrenText.toString());
        } else {
            hide(children);
        }
        hide(parent);
    }

    /**
     * Hides a {@link Label} from view and layout.
     *
     * @param label the label to hide
     */
    private void hide(Label label) {
        label.setVisible(false);
        label.setManaged(false);
    }

    /**
     * Sets label text and shows it.
     *
     * @param label the label to show
     * @param text the text to set
     */
    private void setTextAndShow(Label label, String text) {
        label.setText(text);
        label.setVisible(true);
        label.setManaged(true);
    }

    /**
     * Sets up the tuition class information display.
     */
    private void setupTuitionClassInfo(Function<Person, List<TuitionClass>> tuitionClassLookup) {
        if (tuitionClassLookup == null) {
            hide(tuitionClass);
            return;
        }

        if (person instanceof Student) {
            handleStudentTuitionInfo((Student) person, tuitionClassLookup);
        } else if (person instanceof Tutor) {
            handleTutorTuitionInfo((Tutor) person, tuitionClassLookup);
        } else {
            // For parents or other types
            hide(tuitionClass);
        }
    }

    /**
     * Shows the student's first class if any; otherwise hides the label.
     *
     * @param student the student to render class info for
     * @param tuitionClassLookup resolves a {@link Person} to their classes
     */
    private void handleStudentTuitionInfo(Student student,
                                          Function<Person, List<TuitionClass>> tuitionClassLookup) {
        List<TuitionClass> studentClass = tuitionClassLookup.apply(student);
        if (!studentClass.isEmpty()) {
            setTextAndShow(tuitionClass, "Class: " + studentClass.get(0).toSimpleString());
        } else {
            hide(tuitionClass);
        }
    }

    /**
     * Shows all classes taught by the tutor, joined by comma; hides label if none.
     *
     * @param tutor the tutor to render class info for
     * @param tuitionClassLookup resolves a {@link Person} to their classes
     */
    private void handleTutorTuitionInfo(Tutor tutor,
                                        Function<Person, List<TuitionClass>> tuitionClassLookup) {
        List<TuitionClass> classes = tuitionClassLookup.apply(tutor);
        if (!classes.isEmpty()) {
            String classInfo = classes.stream()
                    .map(TuitionClass::toSimpleString)
                    .collect(Collectors.joining(", "));
            setTextAndShow(tuitionClass, "Classes: " + classInfo);
        } else {
            hide(tuitionClass);
        }
    }
}
