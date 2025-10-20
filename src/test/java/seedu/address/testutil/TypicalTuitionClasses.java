package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * A utility class containing a list of {@code TuitionClass} objects to be used in tests.
 */
public class TypicalTuitionClasses {

    public static final TuitionClass MONDAY_CLASS = new TuitionClass(
            Day.MONDAY,
            Time.H12,
            50,
            GEORGE.getId(), // Tutor
            Stream.of(ALICE.getId(), BENSON.getId()).collect(Collectors.toSet()) // Students
    );
}
