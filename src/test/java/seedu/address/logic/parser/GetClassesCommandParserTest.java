package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GetClassesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Tests for GetClassesCommandParser.
 */
public class GetClassesCommandParserTest {
    private final GetClassesCommandParser parser = new GetClassesCommandParser();

    @Test
    public void parse_noArguments_returnsCommandListAllClasses() throws Exception {
        GetClassesCommand command = parser.parse("");
        assertEquals(new GetClassesCommand(), command);
    }

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, " n/John Doe",
                new GetClassesCommand(new Name("John Doe")));

        // leading and trailing whitespaces
        assertParseSuccess(parser, "    n/Carol Lim   ",
                new GetClassesCommand(new Name("Carol Lim")));
    }

    @Test
    public void parse_emptyName_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetClassesCommand.MESSAGE_USAGE);

        assertParseFailure(parser, " n/   ", expectedMessage);
    }

    @Test
    public void parse_invalidPreamble_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("garbage n/Alice Tan"));
    }

    @Test
    public void parse_duplicateNamePrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("n/Alice Tan n/John Doe"));
    }
}
