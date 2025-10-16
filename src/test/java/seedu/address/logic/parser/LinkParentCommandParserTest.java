package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_PARENT_FIONA;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_STUDENT_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_NAME_FIONA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NAME_ALICE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LinkParentCommand;
import seedu.address.model.person.Name;

public class LinkParentCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkParentCommand.MESSAGE_USAGE);

    private LinkParentCommandParser parser = new LinkParentCommandParser();

    @Test
    public void parse_validArgs_returnsLinkParentCommand() {
        // Expected command
        LinkParentCommand expectedCommand =
                new LinkParentCommand(new Name(VALID_STUDENT_NAME_ALICE), new Name(VALID_PARENT_NAME_FIONA));

        // Command string using constants
        String userInput = NAME_DESC_STUDENT_ALICE + NAME_DESC_PARENT_FIONA;

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // Missing parent name
        assertParseFailure(parser, NAME_DESC_STUDENT_ALICE, MESSAGE_INVALID_FORMAT);

        // Missing student name
        assertParseFailure(parser, NAME_DESC_PARENT_FIONA, MESSAGE_INVALID_FORMAT);

        // No input
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid student name
        assertParseFailure(parser, INVALID_NAME_DESC + NAME_DESC_PARENT_FIONA, Name.MESSAGE_CONSTRAINTS);

        // Invalid parent name
        assertParseFailure(parser, NAME_DESC_STUDENT_ALICE + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // Both invalid
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_preambleNotEmpty_failure() {
        String userInput = "some random text" + NAME_DESC_STUDENT_ALICE + NAME_DESC_PARENT_FIONA;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }
}
