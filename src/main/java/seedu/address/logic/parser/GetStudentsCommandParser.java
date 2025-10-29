package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.GetStudentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new {@code GetStudentsCommand} object
 */
public class GetStudentsCommandParser implements Parser<GetStudentsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code GetStudentsCommand}
     * @param args raw argument string following the command word
     * @return {@code GetStudentsCommand} with the parsed tutor {@link Name}
     * @throws ParseException if the input has a non-empty preamble, lacks {@code n/}, or has an empty name
     */
    @Override
    public GetStudentsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, GetStudentsCommand.MESSAGE_USAGE));
        }

        String rawName = argMultimap.getValue(PREFIX_NAME).orElse("").trim();
        if (rawName.isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, GetStudentsCommand.MESSAGE_USAGE));
        }

        Name tutorName;
        try {
            tutorName = new Name(rawName);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
        return new GetStudentsCommand(tutorName);
    }
}
