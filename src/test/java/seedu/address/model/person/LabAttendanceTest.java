package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LabAttendanceTest {

    @Test
    public void constructor_default_isAttendedFalse() {
        LabAttendance labAttendance = new LabAttendance();
        assertFalse(labAttendance.isAttended());
    }

    @Test
    public void markAsAttended_unmarkedLab_isAttendedTrue() {
        LabAttendance labAttendance = new LabAttendance();

        labAttendance.markAsAttended();
        assertTrue(labAttendance.isAttended());
    }

    @Test
    public void markAsAttended_markedLab_throwIllegalStateException() {
        LabAttendance labAttendance = new LabAttendance();

        labAttendance.markAsAttended();
        assertTrue(labAttendance.isAttended());

        assertThrows(IllegalStateException.class, labAttendance::markAsAttended);
    }

    @Test
    public void equals() {
        LabAttendance labAttendance1 = new LabAttendance();
        LabAttendance labAttendance2 = new LabAttendance();

        assertEquals(labAttendance1, labAttendance2);

        labAttendance1.markAsAttended();
        assertNotEquals(labAttendance1, labAttendance2);

        labAttendance2.markAsAttended();
        assertEquals(labAttendance1, labAttendance2);
    }
}
