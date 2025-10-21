package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import seedu.address.model.Timeslots;

public class SampleDataUtilTest {

    @Test
    public void getSampleTimeslots_nonEmpty() {
        Timeslots sample = SampleDataUtil.getSampleTimeslots();
        assertFalse(sample.getTimeslotList().isEmpty());
    }
}
