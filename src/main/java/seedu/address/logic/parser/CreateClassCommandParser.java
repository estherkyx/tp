package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.stream.Stream;

import seedu.address.logic.commands.CreateClassCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;

/**
 * Parses input arguments and creates a new CreateClassCommand object
 */
public class CreateClassCommandParser implements Parser<CreateClassCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateClassCommand
     * and returns a CreateClassCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateClassCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateClassCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DAY, PREFIX_TIME);

        Day day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get());
        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());

        return new CreateClassCommand(day, time);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
