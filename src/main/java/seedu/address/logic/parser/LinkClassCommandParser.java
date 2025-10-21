package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.stream.Stream;

import seedu.address.logic.commands.LinkClassCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;

/**
 * Parses input arguments and creates a new LinkClassCommand object
 */
public class LinkClassCommandParser implements Parser<LinkClassCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LinkClassCommand
     * and returns a LinkClassCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LinkClassCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_TIME, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_TIME, PREFIX_NAME)
                || !argMultimap.getPreamble().trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkClassCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DAY, PREFIX_TIME, PREFIX_NAME);

        try {
            Day day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get());
            Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
            Name studentName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            return new LinkClassCommand(day, time, studentName);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
