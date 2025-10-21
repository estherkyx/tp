package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Category;

/**
 * Parses input arguments and creates a new {@code ListCommand} object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ListCommand}
     * @param args raw argument string following the command word
     * @return {@code ListCommand} with the parsed category {@link Category}
     * @throws ParseException if the input has a non-empty preamble, lacks {@code n/}, or has an empty name
     */
    @Override
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        String rawName = argMultimap.getValue(PREFIX_CATEGORY).orElse("").trim();
        if (rawName.isEmpty()) {
            return new ListCommand();
        }

        try {
            Category category = Category.fromString(rawName);
            return new ListCommand(category);
        } catch (IllegalArgumentException e) {
            throw new ParseException(ListCommand.MESSAGE_INVALID_CATEGORY);
        }
    }
}
