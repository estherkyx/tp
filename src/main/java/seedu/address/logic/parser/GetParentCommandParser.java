package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.GetParentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new {@code GetParentCommand} object
 */
public class GetParentCommandParser implements Parser<GetParentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code GetParentCommand}
     * @param args raw argument string following the command word
     * @return {@code GetParentCommand} with the parsed student {@link Name}
     * @throws ParseException if the input has a non-empty preamble, lacks {@code n/}, or has an empty name
     */
    @Override
    public GetParentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, GetParentCommand.MESSAGE_USAGE));
        }

        String rawName = argMultimap.getValue(PREFIX_NAME).orElse("").trim();
        if (rawName.isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, GetParentCommand.MESSAGE_USAGE));
        }

        Name studentName;
        try {
            studentName = new Name(rawName);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
        return new GetParentCommand(studentName);
    }
}
