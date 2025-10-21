package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTuitionClasses.MONDAY_CLASS;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class JsonAdaptedTuitionClassTest {

    private static final String INVALID_DAY = "Funday";
    private static final String INVALID_TIME = "H25";

    private static final String VALID_DAY = MONDAY_CLASS.getDay().toString();
    private static final String VALID_TIME = MONDAY_CLASS.getTime().toString();
    private static final String VALID_TUTOR_ID = MONDAY_CLASS.getTutorId().getValue();
    private static final List<String> VALID_STUDENT_IDS = MONDAY_CLASS.getStudentIds().stream()
            .map(id -> id.getValue())
            .collect(Collectors.toList());

    @Test
    public void toModelType_validClassDetails_returnsTuitionClass() throws Exception {
        JsonAdaptedTuitionClass tuitionClass = new JsonAdaptedTuitionClass(MONDAY_CLASS);
        assertEquals(MONDAY_CLASS, tuitionClass.toModelType());
    }

    @Test
    public void toModelType_invalidDay_throwsIllegalValueException() {
        JsonAdaptedTuitionClass tuitionClass =
                new JsonAdaptedTuitionClass(INVALID_DAY, VALID_TIME,
                        VALID_TUTOR_ID, VALID_STUDENT_IDS);
        assertThrows(IllegalValueException.class, tuitionClass::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedTuitionClass tuitionClass =
                new JsonAdaptedTuitionClass(VALID_DAY, INVALID_TIME,
                        VALID_TUTOR_ID, VALID_STUDENT_IDS);
        assertThrows(IllegalValueException.class, tuitionClass::toModelType);
    }
}
