package seedu.address.testutil;

import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;
import seedu.address.model.tuitionclass.TuitionClass;

/**
 * A utility class to help with building TuitionClass objects.
 */
public class TuitionClassBuilder {

    public static final Day DEFAULT_DAY = Day.MONDAY;
    public static final Time DEFAULT_TIME = Time.H12;

    private Day day;
    private Time time;

    /**
     * Creates a {@code TuitionClassBuilder} with the default details.
     */
    public TuitionClassBuilder() {
        day = DEFAULT_DAY;
        time = DEFAULT_TIME;
    }

    /**
     * Initializes the TuitionClassBuilder with the data of {@code classToCopy}.
     */
    public TuitionClassBuilder(TuitionClass classToCopy) {
        day = classToCopy.getDay();
        time = classToCopy.getTime();
    }

    /**
     * Sets the {@code Day} of the {@code TuitionClass} that we are building.
     */
    public TuitionClassBuilder withDay(Day day) {
        this.day = day;
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code TuitionClass} that we are building.
     */
    public TuitionClassBuilder withTime(Time time) {
        this.time = time;
        return this;
    }

    public TuitionClass build() {
        return new TuitionClass(day, time);
    }
}
