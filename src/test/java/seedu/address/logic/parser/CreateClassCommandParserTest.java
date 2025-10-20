package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CreateClassCommand;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;

public class CreateClassCommandParserTest {

    private final CreateClassCommandParser parser = new CreateClassCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Day expectedDay = Day.TUESDAY;
        Time expectedTime = Time.H16;

        // with whitespace
        assertParseSuccess(parser, " d/tuesday ti/h16", new CreateClassCommand(expectedDay, expectedTime));

        // with different case
        assertParseSuccess(parser, " d/TUESDAY ti/H16", new CreateClassCommand(expectedDay, expectedTime));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateClassCommand.MESSAGE_USAGE);

        // missing day prefix
        assertParseFailure(parser, " ti/h14", expectedMessage);

        // missing time prefix
        assertParseFailure(parser, " d/monday", expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, "monday h14", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid day
        assertParseFailure(parser, " d/someday ti/h14", Day.MESSAGE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, " d/monday ti/h13", Time.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_preambleNotEmpty_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateClassCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "some preamble d/monday ti/h14", expectedMessage);
    }
}
