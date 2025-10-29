---
layout: default.md
title: "User Guide"
pageNav: 3
---

# LambdaLab User Guide

LambdaLab is a desktop app for **CS2030S Teaching Assistants** to maintain student records, mark lab attendance and exercises,
and manage consultation timeslots. It is optimised for use via a
Command Line Interface (CLI) while still having an intuitive Graphical User Interface (GUI). If you are a fast typer,
LambdaLab can help you track student information, lab attendance, and exercise submissions even faster than traditional
spreadsheets or GUI apps.

---

# Table of Contents
1. [Quick start](#quick-start)
2. [Features](#eatures)

<div style="margin-left: 20px; line-height: 1.2;">

2.1. [Data-modifying commands](#data-modifying-commands)

<div style="margin-left: 20px; line-height: 1.2;">

2.1.1. [On student data](#on-student-data)
- [Add](#add)
- [Edit](#edit)
- [Delete](#delete)
- [Clear](#clear)

2.1.2. [On lab/exercise/grade](#on-labexercisegrade)
- [Marka](#mark-a-students-lab-attendance-marka)
- [Marke](#mark-a-students-exercise-for-completion-marke)
- [Grade](#record-a-students-score-for-an-exam-grade)

2.1.3. [On timeslot/consultation](#on-timeslotconsultation)
- [Block-timeslot](#blocking-a-timeslot-block-timeslot)
- [Unblock-timeslot](#unblocking-a-timeslot-unblock-timeslot)
- [Add-consultation](#adding-a-consultation-add-consultation)

</div>

2.2. [Data-visualisation commands](#data-visualisation-commands)

<div style="margin-left: 20px; line-height: 1.2;">

2.2.1. [On student data](#on-student-data-1)
- [List](#list)
- [Find](#find)
- [Filter](#filter)
- [Sort](#sort)

2.2.2. [On timeslot/consultation](#on-timeslotconsultation-1)
- [Get-timeslots](#retrieving-merged-timeslot-ranges-get-timeslots)
- [Get-consultations](#retrieving-consultations-only-get-consultations)

</div>

2.3. [Miscellaneous commands](#miscellaneous-commands)
- [Help](#help)
- [Undo](#undo)
- [Set-week](#set-week)
- [Exit](#exit)

2.4. [Non-command features](#non-command-features)
- [Save](#save)
- [Edit data file](#edit-data-file)

</div>

3. [Troubleshooting](#troubleshooting)

<div style="margin-left: 20px; line-height: 1.2;">

3.1. [Frequently asked questions](#frequently-asked-questions)

3.2. [Known Issues](#known-issues)

</div>

4. [Summary](#summary)

<div style="margin-left: 20px; line-height: 1.2;">

4.1. [Command summary](#command-summary)

4.2. [Parameter summary](#parameter-summary)

</div>

---

# Quick Start

1. Ensure you have **Java 17** or above installed on your computer.<br>
> **Checking your Java version:**
> * Open a command terminal
> * Type `java -version` and press Enter
> * If Java is installed, you'll see the version number (e.g., `java version "17.0.1"`)
> * The first number should be 17 or higher
>
> **If Java is not installed or the version is below 17:**
> * Download and install Java 17 by following the guide:
>   * [for Windows users](https://se-education.org/guides/tutorials/javaInstallationWindows.html)
>   * [for Mac users](https://se-education.org/guides/tutorials/javaInstallationMac.html)
>   * [for Linux users](https://se-education.org/guides/tutorials/javaInstallationLinux.html)
> * After installation, restart your terminal and verify the version again

2. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-T09-3/tp/releases).

3. Copy the `.jar` file to the folder you want to use as the _home folder_ for your LambdaLab.

4. Open a command terminal, `cd` into the folder you put the `.jar` file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI should appear in a few seconds, as shown by the image below. Note that the app contains some sample data and the layout
   is explained in coloured boxes.<br>
   ![Ui](images/Ui.png)

5. Type your command in the command box and press Enter to execute it. <br>
   Some example commands you can try:
    * `help` : Shows the help window that explains the command usage.
    * `list` : Lists all students' records.
    * `add i/A1234567X n/John Doe p/98765432 e/johnd@example.com g/JohnDoe t/ModelStudent`: Adds a student named `John Doe` to the record.
    * `delete 3` : Deletes the 3rd student's record shown in the current list.
    * `clear` : Deletes all students' records.
    * `exit` : Exits the app.

6. Refer to the [Features](#command-features) below for details of each command.

---

# Features

<box type="info">

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the [parameters](#parameter-summary) to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* Accepted datetime formats for command parameters (i.e. START_DATETIME & END_DATETIME):
    * ISO_LOCAL_DATE_TIME: `2023-10-01T09:00:00`
    * Human-friendly: `d MMM uuuu, HH:mm` (e.g. `4 Oct 2025, 10:00`) or `d MMM uuuu HH:mm` (e.g. `4 Oct 2025 10:00`)

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

<br><br>

<box type="tip">

**Tip:** For any command using the INDEX parameter, you can mark multiple students at once using a range (e.g. `1:5`). 
This does not apply to the EXERCISE_INDEX or LAB_INDEX parameters
</box>

## Data-modifying commands

### On student data

#### Adding a student: `add`

You can use this command to add a new student to LambdaLab.

**Format:**
```
add i/STUDENTID n/NAME p/PHONE e/EMAIL g/GITHUB_USERNAME [t/TAG]…​
```

**Examples:**
- `add i/A1234567X n/John Doe p/98765432 e/johnd@example.com g/JohnDoe` — Adds a student with student ID `A1234567X`, 
name `John Doe`, phone number `98765432`, email `johnd@example.com`, and GitHub username `JohnDoe`.
- `add i/A1234567X n/John Doe p/98765432 e/johnd@example.com g/JohnDoe t/modelStudent` — Adds the same student but with 
 an optional tag `modelStudent` included.
- `add g/JohnDoe i/A1234567X p/98765432 t/modelStudent n/John Doe e/johnd@example.com` — Adds the same student with 
parameters in different order.

<box type="tip">

**Tip:** A student can have zero or more tags. 
</box>

<box type="warning">

**Caution:** Each student must have a unique student ID. Attempting to add a student with an existing student ID
will result in an error: "This student already exists in LambdaLab". However, students can have the same name/phone number/email/github username 
as long as their student IDs are different.
</box>

<br>

#### Editing a student: `edit`

You can use this command to edit an existing student's information in LambdaLab.

**Format:**
```
edit INDEX [i/STUDENT ID] [n/NAME] [p/PHONE] [e/EMAIL] [g/GITHUB USERNAME] [t/TAG]…​
```

**Examples:**
- `edit 1 p/91234567 e/johndoe@example.com` — Edits the phone number and email address of the 1st student to be `91234567` and `johndoe@example.com` respectively.
- `edit 2 n/Betsy Crower t/` — Edits the name of the 2nd student to be `Betsy Crower` and clears all existing tags.

<box type="tip">

**Tip:** You must provide at least 1 of the parameters in square brackets. Existing values of that parameter will be
updated your the input values.
</box>

<box type="warning">

**Caution:** When editing tags, the existing tags of the student will be removed (i.e., adding of tags is not cumulative). 
You can remove all the student's tags by typing `t/` without specifying any tags after it.
</box>

<br>

#### Deleting a student: `delete`

You can use this command to delete a specified student from LambdaLab.

**Format:**
```
delete INDEX
```

**Examples:**
- `list` followed by `delete 2` — Deletes the 2nd student in the LambdaLab.
- `find Betsy` followed by `delete 1` — Deletes the 1st student in the results of the `find` command.

<box type="tip">

**Tip:** The index refers to the index number shown in the displayed student list. The index must be a positive integer. 
</box>

<box type="warning">

**Caution:** Missing fields or a non-positive index will cause an error: "Invalid command format! delete: 
Deletes the student identified by the index number used in the displayed student list. Parameters: 
INDEX (must be a positive integer) Example: delete 1".
</box>

<br>

#### Clearing all entries: `clear`

You can use this command to clear all entries from LambdaLab, leaving it completely empty.

**Format:**
```
clear
```

<box type="warning">

**Caution:** If you have mistakenly performed this command, type `undo` **immediately** before using another data-modifying command.
</box>

---

### On lab/exercise/grade

#### Mark a student's lab attendance: `marka`

You can use this command to record whether selected students attended or were absent for a particular lab.

**Format:**
```
marka INDEX l/LAB_NUMBER s/STATUS
```

**Examples:**
- `marka 1 l/1 s/y` — Marks Lab 1 as *attended* for the 1st student.
- `marka 1:5 l/1 s/n` — Marks Lab 1 as *not attended* for students 1 through 5.

<box type="warning">

**Caution:** Attempting to re-mark a lab with the same status will trigger a message indicating it was already marked.
</box>

<br>

#### Mark a student's exercise for completion: `marke`

You can use this command to record or update whether selected students have completed a particular exercise.

**Format:**
```
marke INDEX ei/EXERCISE_INDEX s/STATUS
```

**Examples:**
- `marke 1 ei/1 s/y` — Marks Exercise 1 as *done* for the 1st student.
- `marke 2:5 ei/3 s/n` — Marks Exercise 3 as *not done* for students 2 through 5.

<box type="warning">

**Caution:** If an exercise is already marked with the same status, the command will show a message indicating it was already marked.
</box>

<br>

#### Record a student's score for an exam: `grade`

You can use this command to assign or edit exam grades for selected students.

**Format:**
```
grade INDEX... en/EXAM_NAME s/SCORE
```

**Examples:**
- `grade 1 en/Midterm s/87.5` — Records a score of 87.5 for the Midterm exam for the 1st student.
- `grade 2:4 en/Final s/90` — Assigns a score of 90 for the Final exam to students 2 through 4.

<box type="tip">

**Tip:** Scores will automatically be rounded down to one decimal place.  
</box>

<box type="warning">

**Caution:** The exam name must be one of the valid exams defined in the system. Additionally, the score must be a valid number (e.g., `85`, `92.5`) that is no greater than the maximum score of the exam you intend to mark.
</box>

| **Valid Exam Name** | **Maximum score** |
|----------------------|-------------------|
| `pe1`               | 40                |
| `midterm`           | 60                |
| `pe2`               | 40                |
| `final`             | 100               |

### On timeslot/consultation

This section contains commands for managing unavailable times (blocked timeslots) and consultations. Timeslots can be used to mark regular events (e.g. classes), while consultation timeslots are specifically for CS2030S consultation sessions. These commands are designed to help users schedule and organize their consultation times more effectively.

<box type="tip">

**Tip:** Use `get-timeslots` to see your full schedule and `get-consultations` for an uncluttered view of your consultation schedule.

</box>

<box type="warning">
Note on overlaps: LambdaLab prevents overlapping timeslots. If you try to add a timeslot that partially or fully overlaps an existing timeslot, the command will be rejected with an error ("A timeslot at the same time already exists."). This safeguard applies to both generic timeslots (block-timeslot) and consultations (add-consultation).
</box>

<br><br>

#### Blocking a timeslot: `block-timeslot`

You can use this command to add a timeslot to the application's timeslot store.

**Format:**
```
block-timeslot ts/START_DATETIME te/END_DATETIME`
```

Examples:
* Blocking out a timeslot on 4 October 2025 from 10am to 1pm: \
    * `block-timeslot ts/2025-10-04T10:00:00 te/2025-10-04T13:00:00`
    * `block-timeslot ts/4 Oct 2025, 10:00 te/4 Oct 2025, 13:00`
    * `block-timeslot ts/4 Oct 2025 10:00 te/4 Oct 2025 13:00`

<br><br>

#### Unblocking a timeslot: `unblock-timeslot`

You can use this command to remove or trim stored timeslots that overlap the specified datetime range.

**Format:**
```
unblock-timeslot ts/START_DATETIME te/END_DATETIME
```

Examples:
* Unblocking a timeslot on 4 October 2025 from 10am to 1pm: \
    * `unblock-timeslot ts/2025-10-04T10:00:00 te/2025-10-04T13:00:00`
    * `unblock-timeslot ts/4 Oct 2025, 10:00 te/4 Oct 2025, 13:00`
    * `unblock-timeslot ts/4 Oct 2025 10:00 te/4 Oct 2025 13:00`

<box type="tip">

**Tip:** You can use `get-timeslots` to get the correctly formatted datetimes for the timeslot you want to unblock.
</box>

<box type="warning">

**Caution:** The command will remove exact matches, trim edges, or split stored timeslots that contain the unblock range.

* If the unblock range is strictly inside a stored timeslot, the stored timeslot is split into two (before and after the unblock range).
* If the unblock range overlaps one end of a stored timeslot, the stored timeslot is trimmed accordingly.
  </box>

<br>

#### Adding a consultation: `add-consultation`

You can use this command to add a consultation timeslot associated with a student's name.

**Format:**
```
add-consultation ts/START_DATETIME te/END_DATETIME n/STUDENT_NAME
```

Examples:
* Adding a consultation timeslot with John Doe on 4 October 2025 from 10am to 1pm: \
    * `add-consultation ts/2025-10-04T10:00:00 te/2025-10-04T13:00:00 n/John Doe`
    * `add-consultation ts/4 Oct 2025, 10:00 te/4 Oct 2025, 13:00 n/John Doe`
    * `add-consultation ts/4 Oct 2025 10:00 te/4 Oct 2025 13:00 n/John Doe`

<box type="tip">

**Tip:** The Timetable window renders consultations with a distinct appearance (star icon and student name shown under the time label) for easier identification.

</box>

<box type="warning">

**Caution:** The application prevents overlapping timeslots. If you try to add a timeslot that partially or fully overlaps an existing timeslot, the command will be rejected with an error ("A timeslot at the same time already exists."). This safeguard applies to both generic timeslots (block-timeslot) and consultations (add-consultation).
</box>

<br>

#### Clearing all timeslots: `clear-timeslots`

You can use this command to remove all stored timeslots (does not affect student records).

**Format:**
```
clear-timeslots
```

<box type="warning">

**Caution:**
This will permanently remove all stored timeslots. There is no multi-step undo for timeslot clearing;
use immediately after a mistaken action if your environment supports undo of other operations.
</box>

---

## Data-visualisation commands

### On student data

#### List

*Content to be added*

#### Find

*Content to be added*

#### Filter

*Content to be added*

#### Sort

*Content to be added*

### On timeslot/consultation

#### Retrieving merged timeslot ranges: `get-timeslots`

You can use this command to display all timeslot ranges derived from stored timeslots. This allows the user to keep track of unavailable times for easier scheduling of consultations with students.

**Format:**
```
get-timeslots
```

<box type="tip">

**Tip:** The UI can display these timeslot ranges in the Timetable window (when available). Note that the Timetable view only shows timeslots between 08:00 and 23:00.
![Timetable window](images/timetableWindow.png)

</box>

<br>

#### Retrieving consultations only: `get-consultations`

You can use this command to display all consultation timeslot ranges derived from stored consultation entries (ignores generic blocked timeslots). You can use it when you want a quick overview of scheduled consultations (student-facing times) without other blocked times.

**Format:**
```
get-consultations
```

## Miscellaneous commands

### Help

*Content to be added*

### Undo

*Content to be added*

### Set-week

*Content to be added*

### Exit

*Content to be added*

---

## Non-command features

### Save

*Content to be added*

### Edit data file

*Content to be added*

---

# Troubleshooting

## Frequently asked questions

*Content to be added*

## Known Issues

*Content to be added*

---

# Summary

## Command Summary
*Content to be added*

## Parameter Summary

| **Parameter**      | **Description** | **Prefix** | **Constraint**                                                               |
|---------------------|------------------|-------------|------------------------------------------------------------------------------|
| **STUDENT_INDEX**   | Index or range of student(s) in the displayed list. | *(no prefix — written before other parameters)* | Must be a whole number greater than 0, or a range in the format `X:Y`.       |
| **EXERCISE_INDEX**  | Specific exercise number to mark. | `ei/` | Must be between 0–9 (inclusive).                                             |
| **STATUS**          | Completion or attendance status (`y` = yes, `n` = no). | `s/` | Must be either `y` or `n`.                                                   |
| **LAB_NUMBER**      | Specific lab session to mark attendance for. | `l/` | Must be between 1–10 (inclusive).                                            |
| **EXAM_NAME**       | Name of the exam to record or update a grade for. | `en/` | Must be one of: `pe1`, `midterm`, `pe2`, or `final`.                         |
| **SCORE**           | Numeric grade assigned for the exam. | `sc/` | Must be a number; up to one decimal place.                                   |
| **START_DATETIME**   | Starting datetime of the timeslot | `ts/` | Must be in ISO_LOCAL_DATE_TIME or human-friendly format (specified in notes) |
| **END_DATETIME**   | Ending datetime of the timeslot | `ts/` | Must be in ISO_LOCAL_DATE_TIME or human-friendly format (specified in notes) |
| **STUDENT_NAME**          | Name of student in consultation | `n/` |  |
