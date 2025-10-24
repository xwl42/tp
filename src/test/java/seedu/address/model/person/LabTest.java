package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LabTest {

    @Test
    public void constructor_default_isAttendedFalse() {
        Lab labAttendance = new Lab(1, 1);
        assertFalse(labAttendance.isAttended());
    }

    @Test
    public void markAsAttended_unmarkedLab_isAttendedTrue() {
        Lab labAttendance = new Lab(1, 1);

        labAttendance.markAsAttended();
        assertTrue(labAttendance.isAttended());
    }

    @Test
    public void markAsAttended_markedLab_throwIllegalStateException() {
        Lab labAttendance = new Lab(1, 1);

        labAttendance.markAsAttended();
        assertTrue(labAttendance.isAttended());

        assertThrows(IllegalStateException.class, labAttendance::markAsAttended);
    }

    @Test
    public void equals() {
        Lab labAttendance1 = new Lab(1, 1);
        Lab labAttendance2 = new Lab(1, 1);
        Lab labAttendance3 = new Lab(2, 1);

        // Same object
        assertEquals(labAttendance1, labAttendance1);

        // Object with same lab data fields
        assertEquals(labAttendance1, labAttendance2);

        // Object with different lab number field
        assertNotEquals(labAttendance1, labAttendance3);

        // Object with same lab isAttended field
        labAttendance1.markAsAttended();
        assertNotEquals(labAttendance1, labAttendance2);

        // Object with same lab data fields
        labAttendance2.markAsAttended();
        assertEquals(labAttendance1, labAttendance2);

        assertNotEquals(labAttendance1, null);
    }

    @Test
    public void toString_default_success() {
        Lab lab = new Lab(1, 1);
        assertEquals("L1: N", lab.toString());

        lab.markAsAttended();
        assertEquals("L1: Y", lab.toString());
    }
}
