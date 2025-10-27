package seedu.address.model.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.Timeslots;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExerciseTracker;
import seedu.address.model.person.GithubUsername;
import seedu.address.model.person.GradeMap;
import seedu.address.model.person.LabList;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.StudentId;
import seedu.address.model.tag.Tag;
import seedu.address.model.timeslot.ConsultationTimeslot;
import seedu.address.model.timeslot.Timeslot;

/**
 * Contains utility methods for populating {@code AddressBook} and {@code Timeslots} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new StudentId("A1231230B"), new Name("Alex Yeoh"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    getTagSet("friends"),
                    new GithubUsername("AlexYeoh"),
                    new ExerciseTracker(), new LabList(), new GradeMap()),
            new Person(new StudentId("A1231231B"), new Name("Bernice Yu"), new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    getTagSet("colleagues", "friends"),
                    new GithubUsername("BerniceYu"),
                    new ExerciseTracker(), new LabList(), new GradeMap()),
            new Person(new StudentId("A1231232B"), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    getTagSet("neighbours"),
                    new GithubUsername("CharlotteOliveiro"),
                    new ExerciseTracker(), new LabList(), new GradeMap()),
            new Person(new StudentId("A1231233B"), new Name("David Li"), new Phone("91031282"),
                    new Email("lidavid@example.com"),
                    getTagSet("family"),
                    new GithubUsername("DavidLi"),
                    new ExerciseTracker(), new LabList(), new GradeMap()),
            new Person(new StudentId("A1231234B"), new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new Email("irfan@example.com"),
                    getTagSet("classmates"),
                    new GithubUsername("IrfanIbrahim"),
                    new ExerciseTracker(), new LabList(), new GradeMap()),
            new Person(new StudentId("A1231235B"), new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Email("royb@example.com"),
                    getTagSet("colleagues"),
                    new GithubUsername("RoyBalakrishnan"),
                    new ExerciseTracker(), new LabList(), new GradeMap())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a sample Timeslots populated with a few sample Timeslot entries.
     * Uses the current week (Monday..Sunday) as the base so samples stay relevant.
     */
    public static Timeslots getSampleTimeslots() {
        Timeslots sample = new Timeslots();

        // Determine this week's Monday as the base date
        LocalDate weekStart = LocalDate.now().with(DayOfWeek.MONDAY);

        // Monday: separate morning slots and an afternoon slot
        LocalDate monday = weekStart;
        sample.addTimeslot(new Timeslot(monday.atTime(9, 0), monday.atTime(10, 0)));
        sample.addTimeslot(new Timeslot(monday.atTime(10, 30), monday.atTime(11, 0)));
        sample.addTimeslot(new Timeslot(monday.atTime(14, 0), monday.atTime(15, 30)));

        // Tuesday: two separate slots
        LocalDate tuesday = weekStart.plusDays(1);
        sample.addTimeslot(new Timeslot(tuesday.atTime(9, 0), tuesday.atTime(10, 0)));
        sample.addTimeslot(new Timeslot(tuesday.atTime(13, 0), tuesday.atTime(14, 30)));

        // Wednesday: single midday slot
        LocalDate wednesday = weekStart.plusDays(2);
        sample.addTimeslot(new Timeslot(wednesday.atTime(12, 0), wednesday.atTime(13, 0)));

        // Thursday: morning and evening slot
        LocalDate thursday = weekStart.plusDays(3);
        sample.addTimeslot(new Timeslot(thursday.atTime(8, 30), thursday.atTime(10, 0)));
        sample.addTimeslot(new Timeslot(thursday.atTime(18, 0), thursday.atTime(19, 0)));

        // Friday: longer afternoon slot
        LocalDate friday = weekStart.plusDays(4);
        sample.addTimeslot(new Timeslot(friday.atTime(15, 0), friday.atTime(17, 0)));

        // Saturday: morning workshop slot
        LocalDate saturday = weekStart.plusDays(5);
        sample.addTimeslot(new Timeslot(saturday.atTime(10, 0), saturday.atTime(12, 0)));

        // Sunday: block spanning morning into early afternoon
        LocalDate sunday = weekStart.plusDays(6);
        sample.addTimeslot(new Timeslot(sunday.atTime(9, 0), sunday.atTime(13, 0)));

        // Sample consultations (ConsultationTimeslot) placed in the same current week
        // Tuesday 10:00-10:30 with Alice
        sample.addTimeslot(new ConsultationTimeslot(tuesday.atTime(10, 0), tuesday.atTime(10, 30), "Alice"));
        // Thursday 14:00-14:30 with Bob
        sample.addTimeslot(new ConsultationTimeslot(thursday.atTime(14, 0), thursday.atTime(14, 30), "Bob"));

        return sample;
    }
}
