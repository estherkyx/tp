package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LinkClassCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;

/**
 * Tests for LinkClassCommandParser.
 */
public class LinkClassCommandParserTest {

    private final LinkClassCommandParser parser = new LinkClassCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String input = " " + CliSyntax.PREFIX_DAY + "Monday " + CliSyntax.PREFIX_TIME + "H12 " + CliSyntax.PREFIX_NAME
                + "Benson Meier";
        LinkClassCommand expected = new LinkClassCommand(Day.MONDAY, Time.H12, new Name("Benson Meier"));
        LinkClassCommand result = parser.parse(input);
        assertEquals(expected, result);
    }

    @Test
    public void parse_missingFields_failure() {
        // missing day
        String input1 = CliSyntax.PREFIX_TIME + "H12 " + CliSyntax.PREFIX_NAME + "Benson Meier";
        assertThrows(ParseException.class, () -> parser.parse(input1));

        // missing name
        String input2 = CliSyntax.PREFIX_DAY + "Monday " + CliSyntax.PREFIX_TIME + "H12 ";
        assertThrows(ParseException.class, () -> parser.parse(input2));
    }

    @Test
    public void parse_invalidValues_failure() {
        // invalid day string
        String input = CliSyntax.PREFIX_DAY + "Funday " + CliSyntax.PREFIX_TIME + "H12 " + CliSyntax.PREFIX_NAME
                + "Benson Meier";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }
}
