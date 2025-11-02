package seedu.address.ui;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    private Function<PersonId, Optional<Person>> personLookup;
    private Function<Person, List<TuitionClass>> tuitionClassLookup;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        this(personList, null, null);
    }

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList} and lookup functions.
     */
    public PersonListPanel(ObservableList<Person> personList, Function<PersonId, Optional<Person>> personLookup,
                          Function<Person, List<TuitionClass>> tuitionClassLookup) {
        super(FXML);
        this.personLookup = personLookup;
        this.tuitionClassLookup = tuitionClassLookup;
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        // Add listener to refresh all cells when any person in the list changes
        // This ensures that relationship displays are updated in real-time
        personList.addListener((javafx.collections.ListChangeListener<Person>) change -> {
            while (change.next()) {
                if (change.wasUpdated() || change.wasReplaced() || change.wasAdded()) {
                    personListView.refresh();
                }
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1, personLookup, tuitionClassLookup).getRoot());
            }
        }
    }

}
