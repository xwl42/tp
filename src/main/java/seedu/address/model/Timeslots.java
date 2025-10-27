package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.timeslot.Timeslot;

/**
 * Wraps all data at the timeslots level.
 * Duplicates are not allowed (by .equals comparison).
 */
public class Timeslots implements ReadOnlyTimeslots {

    private final ObservableList<Timeslot> times = FXCollections.observableArrayList();

    public Timeslots() {}

    /**
     * Creates a Timeslots using the Timeslots in the {@code toBeCopied}
     */
    public Timeslots(ReadOnlyTimeslots toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the timeslot list with {@code timeslots}.
     * {@code timeslots} must not contain duplicate timeslots.
     */
    public void setTimeslots(List<Timeslot> timeslots) {
        requireNonNull(timeslots);
        times.setAll(timeslots);
    }

    /**
     * Resets the existing data of this {@code Timeslots} with {@code newData}.
     */
    public void resetData(ReadOnlyTimeslots newData) {
        requireNonNull(newData);
        setTimeslots(newData.getTimeslotList());
    }

    //// timeslot-level operations

    /**
     * Adds a timeslot to this Timeslots collection.
     *
     * @param t timeslot to add; must not be null.
     * @throws IllegalArgumentException if the new timeslot overlaps an existing one.
     */
    public void addTimeslot(Timeslot t) {
        requireNonNull(t);
        // Prevent overlapping timeslots: new timeslot must not intersect any existing timeslot.
        for (Timeslot existing : times) {
            if (overlaps(existing, t)) {
                throw new IllegalArgumentException("Timeslot overlaps existing timeslot: " + existing);
            }
        }
        times.add(t);
    }

    /**
     * Two timeslots overlap if their intervals intersect (end > start and start < end).
     */
    private static boolean overlaps(Timeslot a, Timeslot b) {
        return a.getEnd().isAfter(b.getStart()) && a.getStart().isBefore(b.getEnd());
    }

    /**
     * Checks whether the given timeslot is present in this Timeslots collection.
     *
     * @param t timeslot to check; must not be null.
     * @return true if present, false otherwise.
     */
    public boolean hasTimeslot(Timeslot t) {
        requireNonNull(t);
        return times.contains(t);
    }

    public void removeTimeslot(Timeslot key) {
        times.remove(key);
    }

    /**
     * Clears all timeslots in this collection while preserving the same ObservableList instance.
     */
    public void clear() {
        times.clear();
    }

    //// util methods

    @Override
    public ObservableList<Timeslot> getTimeslotList() {
        return FXCollections.unmodifiableObservableList(times);
    }

    @Override
    public String toString() {
        return "Timeslots" + times.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Timeslots)) {
            return false;
        }
        Timeslots otherTimeslots = (Timeslots) other;
        return times.equals(otherTimeslots.times);
    }

    @Override
    public int hashCode() {
        return Objects.hash(times);
    }
}
