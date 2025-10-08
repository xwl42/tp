package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LabAttendanceListTest {
    private Lab[] labs;

    @BeforeEach
    public void setUpLabs() {
        this.labs = new Lab[LabAttendanceList.NUMBER_OF_LABS];
        for (int i = 0; i < labs.length; i++) {
            labs[i] = new LabAttendanceStub();
        }
    }

    @Test
    public void constructor_default_success() {
        LabAttendanceList labAttendanceList = new LabAttendanceList(labs);
        for (int i = 0; i < labs.length; i++) {
            assertFalse(labs[i].hasAttended());
        }
    }

    @Test
    public void markLab_validIndex_success() {
        LabAttendanceList labAttendanceList = new LabAttendanceList(labs);

        assertFalse(labs[0].hasAttended());
        labAttendanceList.markLabAsAttended(0);
        assertTrue(labs[0].hasAttended());

        assertFalse(labs[5].hasAttended());
        labAttendanceList.markLabAsAttended(5);
        assertTrue(labs[5].hasAttended());
    }

    /* TODO invalid test cases */
}
