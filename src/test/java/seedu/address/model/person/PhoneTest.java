package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 8 numbers
        assertFalse(Phone.isValidPhone("911")); // less than 8 numbers
        assertFalse(Phone.isValidPhone("1234567")); // exactly 7 numbers
        assertFalse(Phone.isValidPhone("123456789")); // exactly 9 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits
        assertFalse(Phone.isValidPhone("124293842033123")); // long phone numbers
        assertFalse(Phone.isValidPhone("12345678")); // starts with 1 (invalid Singaporean number)
        assertFalse(Phone.isValidPhone("22345678")); // starts with 2 (invalid Singaporean number)
        assertFalse(Phone.isValidPhone("42345678")); // starts with 4 (invalid Singaporean number)
        assertFalse(Phone.isValidPhone("52345678")); // starts with 5 (invalid Singaporean number)
        assertFalse(Phone.isValidPhone("72345678")); // starts with 7 (invalid Singaporean number)

        // valid phone numbers (Singaporean: starts with 3, 6, 8, or 9)
        assertTrue(Phone.isValidPhone("93121534")); // starts with 9
        assertTrue(Phone.isValidPhone("31234567")); // starts with 3
        assertTrue(Phone.isValidPhone("61234567")); // starts with 6
        assertTrue(Phone.isValidPhone("81234567")); // starts with 8
    }

    @Test
    public void equals() {
        Phone phone = new Phone("93121534");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("93121534")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("98765432")));
    }
}
