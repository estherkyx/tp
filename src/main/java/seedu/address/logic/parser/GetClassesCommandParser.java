package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.GetClassesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new {@code GetClassesCommand} object
 */
public class GetClassesCommandParser implements Parser<GetClassesCommand> {

    @Override
    public GetClassesCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);

        if (argMultimap.getValue(PREFIX_NAME).isEmpty()) {
            // No name specified, list all classes
            return new GetClassesCommand();
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, GetClassesCommand.MESSAGE_USAGE));
        }

        String rawName = argMultimap.getValue(PREFIX_NAME).orElse("").trim();
        if (rawName.isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, GetClassesCommand.MESSAGE_USAGE));
        }

        Name tutorName = new Name(rawName);
        return new GetClassesCommand(tutorName);
    }
}
