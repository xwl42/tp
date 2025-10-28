## **Features**

<box type="info">

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the [parameters](#parameters) to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

<br><br>

> [!TIP]
> For any command using the INDEX parameter,
> you can mark multiple students at once using a range (e.g. `1:5`).
> note: This does not apply to the EXERCISE_INDEX or LAB_INDEX parameters
 
### **Data – Modifying**

#### **Part 1: Person Data**
- Add
- Edit
- Delete
- Clear
- Undo

#### **Part 2: Tracking milestones like labs, exercises and exams**

---

## Mark a student's lab attendance: `marka`

You can use this command to record whether selected students attended or were absent for a particular lab.  

**Format:**
```
marka INDEX l/LAB_NUMBER s/STATUS
```

**Examples:**
- `marka 1 l/1 s/y` — Marks Lab 1 as *attended* for the 1st student.
- `marka 1:5 l/1 s/n` — Marks Lab 1 as *not attended* for students 1 through 5.

> [!WARNING]
> Attempting to re-mark a lab with the same status will trigger a message indicating it was already marked.

---

## Mark a student's exercise for completion: `marke`

Use this command to record or update whether selected students have completed a particular exercise.  

**Format:**
```
marke INDEX... ei/EXERCISE_INDEX s/STATUS
```

**Examples:**
- `marke 1 ei/1 s/y` — Marks Exercise 1 as *done* for the 1st student.
- `marke 2:5 ei/3 s/n` — Marks Exercise 3 as *not done* for students 2 through 5.

> [!WARNING]
> If an exercise is already marked with the same status, the command will show a message indicating it was already marked.

---

## Record a student's score for an exam: `grade`

This command allows you to assign or edit exam grades for selected students.  

**Format:**
```
grade INDEX... en/EXAM_NAME s/SCORE
```

**Examples:**
- `grade 1 en/Midterm s/87.5` — Records a score of 87.5 for the Midterm exam for the 1st student.
- `grade 2:4 en/Final s/90` — Assigns a score of 90 for the Final exam to students 2 through 4.

> [!TIP]
> Scores will automatically be rounded down to one decimal place.  

> [!WARNING]
> The exam name must be one of the valid exams defined in the system.  
> The score must be a valid number (e.g., `85`, `92.5`). Non-numeric inputs will result in an error.

---

#### **Part 3: AddressBook Level Changes**
- Set-week

---

#### **Part 4: Timeslots**
- block-timeslot
- unblock-timeslot
- add-consultation
- get-timeslots
- get-consultations

---

## **Visualisation**
- Find
- Filter
- Sort
- List

---

## **Miscellaneous**
- Exit
- Help

---

## **Non-command Feature**
- Save
- Edit data file

---

#### Parameters

| **Parameter**      | **Description** | **Prefix** | **Constraint** |
|---------------------|------------------|-------------|----------------|
| **STUDENT_INDEX**   | Index or range of student(s) in the displayed list. | *(no prefix — written before other parameters)* | Must be a whole number greater than 0, or a range in the format `X:Y`. |
| **EXERCISE_INDEX**  | The specific exercise number to mark. | `ei/` | Must be between 0–9 (inclusive). |
| **STATUS**          | Indicates completion or attendance status (`y` = yes, `n` = no). | `s/` | Must be either `y` or `n`. |
| **LAB_NUMBER**      | The specific lab session to mark attendance for. | `l/` | Must be between 1–10 (inclusive). |
| **EXAM_NAME**       | Name of the exam to record or update a grade for. | `en/` | Must be one of: `pe1`, `midterm`, `pe2`, or `final`. |
| **SCORE**           | Numeric grade assigned for the exam. | `sc/` | Must be a number; up to one decimal place. |
