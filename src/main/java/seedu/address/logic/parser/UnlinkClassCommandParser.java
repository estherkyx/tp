package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.UnlinkClassCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Category;
import seedu.address.model.person.Name;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;

/**
 * Parses input arguments and creates a new UnlinkClass Command object
 */
public class UnlinkClassCommandParser implements Parser<UnlinkClassCommand> {
    @Override
    public UnlinkClassCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_DAY, PREFIX_TIME, PREFIX_NAME, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_TIME, PREFIX_NAME)
                || !argMultimap.getPreamble().trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlinkClassCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DAY, PREFIX_TIME, PREFIX_NAME, PREFIX_CATEGORY);

        try {
            Day day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get());
            Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
            Name studentName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            Optional<String> opt = argMultimap.getValue(PREFIX_CATEGORY);
            if (opt.isEmpty()) {
                return new UnlinkClassCommand(day, time, studentName);
            }
            Category category = ParserUtil.parseCategory(opt.get());
            return new UnlinkClassCommand(day, time, studentName, category);
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
