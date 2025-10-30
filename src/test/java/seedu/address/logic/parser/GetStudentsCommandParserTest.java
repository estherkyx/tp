package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GetStudentsCommand;
import seedu.address.model.person.Name;

public class GetStudentsCommandParserTest {

    private final GetStudentsCommandParser parser = new GetStudentsCommandParser();
    private final String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            GetStudentsCommand.MESSAGE_USAGE);

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, " n/Aaron Tan",
                new GetStudentsCommand(new Name("Aaron Tan")));

        // leading and trailing whitespaces
        assertParseSuccess(parser, "    n/Becky Lim   ",
                new GetStudentsCommand(new Name("Becky Lim")));
    }

    @Test
    public void parse_missingPrefix_failure() {
        assertParseFailure(parser, " Aaron Tan", expectedMessage);
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_emptyName_failure() {
        assertParseFailure(parser, " n/   ", expectedMessage);
    }

    @Test
    public void parse_withPreamble_failure() {
        assertParseFailure(parser, " hello n/Aaron", expectedMessage);
    }
}
