package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord.toLowerCase()) {
        case "add":
            return new AddCommandParser().parse(arguments);

        case "edit":
            return new EditCommandParser().parse(arguments);

        case "delete":
            return new DeleteCommandParser().parse(arguments);

        case "clear":
            return new ClearCommand();

        case "find":
            return new FindCommandParser().parse(arguments);

        case "list":
            return new ListCommandParser().parse(arguments);

        case "getstudents":
            return new GetStudentsCommandParser().parse(arguments);

        case "getparent":
            return new GetParentCommandParser().parse(arguments);

        case "exit":
            return new ExitCommand();

        case "help":
            return new HelpCommand();

        case "linkparent":
            return new LinkParentCommandParser().parse(arguments);

        case "createclass":
            return new CreateClassCommandParser().parse(arguments);

        case "linkclass":
            return new LinkClassCommandParser().parse(arguments);

        case "unlinkclass":
            return new UnlinkClassCommandParser().parse(arguments);

        case "getclasses":
            return new GetClassesCommandParser().parse(arguments);

        case "getclassdetails":
            return new GetClassDetailsCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
