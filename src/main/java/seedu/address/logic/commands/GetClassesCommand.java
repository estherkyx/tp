package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Tutor;
import seedu.address.model.tuitionclass.Time;
import seedu.address.model.tuitionclass.TuitionClass;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Lists all tuition classes or those belonging to a specific tutor.
 */
public class GetClassesCommand extends Command {
    public static final String COMMAND_WORD = "getClasses";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tuition classes or those of a specific tutor.\n"
            + "Parameters: [n/TUTOR NAME]\n"
            + "Example 1: " + COMMAND_WORD + "\n"
            + "Example 2: " + COMMAND_WORD + " n/Roy Balakrishnan";

    public static final String MESSAGE_TUTOR_NOT_FOUND = "Tutor with name '%1$s' not found.";
    public static final String MESSAGE_NO_CLASSES_FOUND = "No classes found for tutor '%1$s'.";

    private final Name tutorName;
    private PersonId tutorId;

    /**
     * Constructor for no-argument version to list all tuition classes
     */
    public GetClassesCommand() {
        this.tutorName = null;
    }

    /**
     * Constructor if tutor name is provided
     */
    public GetClassesCommand(Name tutorName) {
        requireNonNull(tutorName);
        this.tutorName = tutorName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Always start from all classes
        List<TuitionClass> allClasses = model.getTuitionClassList();

        if (tutorName == null) {
            // Case 1: List all classes
            if (allClasses.isEmpty()) {
                return new CommandResult("There are no tuition classes in the system.");
            }

            StringBuilder classListBuilder = new StringBuilder("All Tuition Classes:\n");
            int index = 1;
            for (TuitionClass tc : allClasses) {
                String time = timeEnumToTimeString(tc.getTime());
                classListBuilder.append(String.format("%d. %s %s\n", index++, tc.getDay(), time));
            }
            return new CommandResult(classListBuilder.toString().trim());
        }

        // Case 2: List classes for a specific tutor
        // Find the tutor in the system
        List<Person> personList = model.getFilteredPersonList();
        Person tutor = personList.stream()
                .filter(p -> p.getName().equals(tutorName))
                .findFirst()
                .orElse(null);

        if (!(tutor instanceof Tutor)) {
            throw new CommandException(String.format(MESSAGE_TUTOR_NOT_FOUND, tutorName));
        }

        tutorId = tutor.getId();

        // Filter classes by tutor ID
        List<TuitionClass> tutorClasses = allClasses.stream()
                .filter(tuitionClass -> tutorId.equals(tuitionClass.getTutorId()))
                .toList();

        if (tutorClasses.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_CLASSES_FOUND, tutorName));
        }

        StringBuilder result = new StringBuilder(String.format("Classes taught by %s:\n", tutorName));
        int index = 1;
        for (TuitionClass tc : tutorClasses) {
            String time = timeEnumToTimeString(tc.getTime());
            result.append(String.format("%d. %s %s\n", index++, tc.getDay(), time));
        }
        return new CommandResult(result.toString().trim());
    }

    private String timeEnumToTimeString(Time time) {
        String timeString = time.toString();
        return timeString.substring(1) + "00";
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof GetClassesCommand
                && ((GetClassesCommand) other).tutorName.equals(tutorName));
    }
}
