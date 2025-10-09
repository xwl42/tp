package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LabAttendanceListTest {
    private Lab[] labs;

    @BeforeEach
    public void setUpLabs() {
        this.labs = createLabArray();
    }

    private Lab[] createLabArray() {
        Lab[] newLabs = new Lab[LabAttendanceList.NUMBER_OF_LABS];
        for (int i = 0; i < newLabs.length; i++) {
            newLabs[i] = new LabAttendanceStub();
        }
        return newLabs;
    }


    @Test
    public void constructor_default_success() {
        LabAttendanceList labAttendanceList = new LabAttendanceList(labs);
        for (int i = 0; i < labs.length; i++) {
            assertFalse(labs[i].isAttended());
        }
    }

    @Test
    public void markLab_validIndex_success() {
        LabAttendanceList labAttendanceList = new LabAttendanceList(labs);

        assertFalse(labs[0].isAttended());
        labAttendanceList.markLabAsAttended(0);
        assertTrue(labs[0].isAttended());

        assertFalse(labs[5].isAttended());
        labAttendanceList.markLabAsAttended(5);
        assertTrue(labs[5].isAttended());
    }

    @Test
    public void markLab_invalidIndex_throwsIndexOutOfBoundsException() {
        LabAttendanceList labAttendanceList = new LabAttendanceList(labs);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            labAttendanceList.markLabAsAttended(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            labAttendanceList.markLabAsAttended(10);
        });
    }

    @Test
    public void equals() {
        Lab[] labs1 = createLabArray();
        Lab[] labs2 = createLabArray();
        LabAttendanceList labAttendanceList1 = new LabAttendanceList(labs1);
        LabAttendanceList labAttendanceList2 = new LabAttendanceList(labs2);
        assertEquals(labAttendanceList1, labAttendanceList2);

        labAttendanceList1.markLabAsAttended(2);
        assertNotEquals(labAttendanceList1, labAttendanceList2);
    }
}
