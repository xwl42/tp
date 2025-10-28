## **Sections**

### **Data – Modifying**

#### **Part 1: Person Data**
- Add
- Edit
- Delete
- Clear
- Undo

#### **Part 2: Lab / Exercise / Grade**
## Mark a student's lab attendance: `marka`

Marks the attendance status of one or more students for a specific lab session.

This command allows tutors to record whether selected students attended or were absent for a particular lab.  
It supports marking individual students or a range of students at once.

**Format:**
```
marka INDEX... l/LAB_NUMBER s/STATUS
```

**Examples:**
- `marka 1 l/1 s/y` — Marks Lab 1 as *attended* for the 1st student.
- `marka 1:5 l/1 s/n` — Marks Lab 1 as *not attended* for students 1 through 5.

> 💡 **Tip:**  
> Use `s/y` for *attended* and `s/n` for *not attended*.  
> You can mark multiple students at once using a range (e.g. `1:5`).

> ⚠️ **Warning:**  
> Lab indexes must be within 1–10 inclusive.  
> Attempting to re-mark a lab with the same status will trigger a message indicating it was already marked.


---

## Mark a student's exercise for completion: `marke`

Marks the completion status of an exercise for one or more students.

This command allows tutors to record or update whether specific students have completed a particular exercise.  
It supports marking a single student or a range of students simultaneously.

**Format:**
```
marke INDEX... ei/EXERCISE_INDEX s/STATUS
```

**Examples:**
- `marke 1 ei/1 s/y` — Marks Exercise 1 as *done* for the 1st student.
- `marke 2:5 ei/3 s/n` — Marks Exercise 3 as *not done* for students 2 through 5.

> 💡 **Tip:**  
> Use `s/y` for *done* and `s/n` for *not done*.  
> You can also re-run the command to overwrite a previous mark.

> ⚠️ **Warning:**  
> Exercise indexes must be within 0–9 inclusive.  
> If an exercise is already marked with the same status, the command will show a message indicating it was already marked.

---

## Record a student's score for an exam: `grade`

Records or updates the grade of one or more students for a specific exam.

This command allows tutors to assign or edit exam grades for selected students.  
It supports entering grades for a single student or a range of students at once.

**Format:**
```
grade INDEX... en/EXAM_NAME s/SCORE
```

**Examples:**
- `grade 1 en/Midterm s/87.5` — Records a score of 87.5 for the Midterm exam for the 1st student.
- `grade 2:4 en/Final s/90` — Assigns a score of 90 for the Final exam to students 2 through 4.

> 💡 **Tip:**  
> Scores will automatically be rounded down to one decimal place.  
> You can update an existing score simply by reusing the same command with a new value.

> ⚠️ **Warning:**  
> The exam name must be one of the valid exams defined in the system.  
> The score must be a valid number (e.g., `85`, `92.5`). Non-numeric inputs will result in an error.

#### **Part 3: AddressBook Level Changes**
- Set-week

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

| **Parameter**            | **Prefix** | **Constraint** |
|---------------------------|-------------|----------------|
| Student ID                | `i/`        |                |
| Name                      | `n/`        |                |
| Phone                     | `p/`        |                |
| Email                     | `e/`        |                |
| Tag                       | `t/`        |                |
| Exercise Index            | `ei/`       |                |
| Status                    | `s/`        |                |
| GitHub Username           | `g/`        |                |
| Lab Number                | `l/`        |                |
| Exam Name                 | `en/`       |                |
| Score                     | `sc/`       |                |
| Sort Criterion             | `c/`        |                |
| Timeslot Start Time       | `ts/`       |                |
| Timeslot End Time         | `te/`       |                |
