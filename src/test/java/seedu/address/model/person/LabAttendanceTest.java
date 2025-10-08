package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LabAttendanceTest {

    @Test
    public void constructor_default_hasAttendedFalse() {
        LabAttendance labAttendance = new LabAttendance();
        assertFalse(labAttendance.hasAttended());
    }

    @Test
    public void markAsAttended_unmarkedLab_hasAttendedTrue() {
        LabAttendance labAttendance = new LabAttendance();
        labAttendance.markAsAttended();
        assertTrue(labAttendance.hasAttended());
    }

    /* TODO invalid test cases */
}
