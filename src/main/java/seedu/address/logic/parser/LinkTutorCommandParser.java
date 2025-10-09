package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.logic.commands.LinkTutorCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;


/**
 * Parses input arguments and creates a new LinkTutorCommand object
 */
public class LinkTutorCommandParser implements Parser<LinkTutorCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LinkTutorCommand
     * and returns a LinkTutorCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LinkTutorCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (argMultimap.getAllValues(PREFIX_NAME).size() != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkTutorCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkTutorCommand.MESSAGE_USAGE));
        }

        List<String> names = argMultimap.getAllValues(PREFIX_NAME);
        Name studentName = ParserUtil.parseName(names.get(0));
        Name tutorName = ParserUtil.parseName(names.get(1));

        return new LinkTutorCommand(studentName, tutorName);
    }
}
