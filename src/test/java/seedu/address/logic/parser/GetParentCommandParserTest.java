package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GetParentCommand;
import seedu.address.model.person.Name;

public class GetParentCommandParserTest {

    private final GetParentCommandParser parser = new GetParentCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, " n/John Doe",
                new GetParentCommand(new Name("John Doe")));

        // leading and trailing whitespaces
        assertParseSuccess(parser, "    n/Carol Lim   ",
                new GetParentCommand(new Name("Carol Lim")));
    }

    @Test
    public void parse_missingPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetParentCommand.MESSAGE_USAGE);

        assertParseFailure(parser, " John Doe", expectedMessage);
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_emptyName_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetParentCommand.MESSAGE_USAGE);

        assertParseFailure(parser, " n/   ", expectedMessage);
    }

    @Test
    public void parse_withPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetParentCommand.MESSAGE_USAGE);

        assertParseFailure(parser, " hello n/John", expectedMessage);
    }
}
