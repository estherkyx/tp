package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ListCommand.MESSAGE_MISSING_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Category;

/**
 * Parses input arguments and creates a new {@code ListCommand} object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ListCommand}.
     *
     * @param args raw argument string following the command word
     * @return {@code ListCommand} with the parsed category {@link Category}, if present
     * @throws ParseException if the input has a non-empty preamble or contains an invalid category name
     */
    @Override
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        // user input is "list"
        var opt = argMultimap.getValue(PREFIX_CATEGORY);
        if (opt.isEmpty()) {
            return new ListCommand();
        }

        // user input is "list c/" without a specified category
        String rawName = opt.get().trim();
        if (rawName.isEmpty()) {
            throw new ParseException(MESSAGE_MISSING_CATEGORY);
        }

        try {
            Category category = Category.fromString(rawName);
            return new ListCommand(category);
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(ListCommand.MESSAGE_INVALID_CATEGORY, rawName));
        }
    }
}
