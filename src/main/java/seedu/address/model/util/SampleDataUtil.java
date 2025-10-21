package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
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
import seedu.address.model.Timeslots;
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
     */
    public static Timeslots getSampleTimeslots() {
        Timeslots sample = new Timeslots();
        // Sample timeslots (ISO_LOCAL_DATE_TIME)
        // 2023-10-01 09:00 - 10:00
        sample.addTimeslot(new Timeslot(LocalDateTime.of(2023, 10, 1, 9, 0),
                LocalDateTime.of(2023, 10, 1, 10, 0)));
        // 2023-10-01 09:30 - 11:00 (overlaps previous)
        sample.addTimeslot(new Timeslot(LocalDateTime.of(2023, 10, 1, 9, 30),
                LocalDateTime.of(2023, 10, 1, 11, 0)));
        // 2023-10-02 14:00 - 15:00 (separate)
        sample.addTimeslot(new Timeslot(LocalDateTime.of(2023, 10, 2, 14, 0),
                LocalDateTime.of(2023, 10, 2, 15, 0)));
        return sample;
    }
}
