package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.logic.commands.LinkParentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

public class LinkParentCommandParser implements Parser<LinkParentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LinkParentCommand
     * and returns a LinkParentCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public LinkParentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        // Ensure that exactly two names are provided
        if (argMultimap.getAllValues(PREFIX_NAME).size() != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkParentCommand.MESSAGE_USAGE));
        }

        // Preamble should be empty
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkParentCommand.MESSAGE_USAGE));
        }

        List<String> names = argMultimap.getAllValues(PREFIX_NAME);
        Name studentName = ParserUtil.parseName(names.get(0));
        Name parentName = ParserUtil.parseName(names.get(1));

        return new LinkParentCommand(studentName, parentName);
    }

}


