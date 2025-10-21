---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# LambdaLab User Guide

LambdaLab is a desktop app for **CS2030S Teaching Assistants** to **manage student records**, optimised for use via a 
Command Line Interface (CLI) while still having an intuitive Graphical User Interface (GUI). If you are a fast typer, 
LambdaLab can help you track student information, lab attendance, and exercise submissions even faster than traditional 
spreadsheets or GUI apps.

## Table of Contents

1. [Quick start](#quick-start)
2. [Features](#features)
    1. [Viewing help : `help`](#viewing-help--help)
    2. [Adding a student : `add`](#adding-a-student-add)
    3. [Listing all students : `list`](#listing-all-students--list)
    4. [Editing a student : `edit`](#editing-a-student--edit)
    5. [Marking Lab Attendance : `marka`](#marking-lab-attendance-marka)
    6. [Marking Exercise Status : `marke`](#marking-exercise-status-marke)
    7. [Locating students by name : `find`](#locating-students-by-name-find)
    8. [Sorting students:`sort`](#sorting-the-students-sort)
    9. [Deleting a student : `delete`](#deleting-a-student--delete)
    10. [Clearing all entries : `clear`](#clearing-all-entries--clear)
    11. [Undoing the last command : `undo`](#undoing-the-last-command--undo)
    12. [Exiting the program : `exit`](#exiting-the-program--exit)
    13. [Saving the data](#saving-the-data)
    14. [Editing the data file](#editing-the-data-file)
    15. [Archiving data files (coming in v2.0)](#archiving-data-files-coming-in-v20)
3. [FAQ](#faq)
4. [Known issues](#known-issues)
5. [Command summary](#command-summary)

---

<page-nav-print />


## Quick start

1. Ensure you have **Java 17** or above installed in your Computer.<br>
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
>   * [for Linus users](https://se-education.org/guides/tutorials/javaInstallationLinux.html)
> * After installation, restart your terminal and verify the version again

2. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-T09-3/tp/releases).

3. Copy the `.jar` file to the folder you want to use as the _home folder_ for your LambdaLab.

4. Open a command terminal, `cd` into the folder you put the `.jar` file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note that the app contains some sample data and the layout 
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

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
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

### Viewing help : `help`

Opens a Help window that provides a link to the User Guide.

It also gives a brief explanation of each command.

![help message](images/helpMessage2.png)

Format: `help`


### Adding a student: `add`

Adds a student to the address book.

Format: `add i/STUDENTID n/NAME p/PHONE e/EMAIL g/GITHUB_USERNAME [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A student can have any number of tags (including 0)
</box>

Examples:
* Compulsory fields only: `add i/A1234567X n/John Doe p/98765432 e/johnd@example.com g/JohnDoe`
* Optional fields included: `add i/A1234567X n/John Doe p/98765432 e/johnd@example.com g/JohnDoe t/modelStudent`
* Fields in different order: `add g/JohnDoe i/A1234567X  p/98765432 t/modelStudent n/John Doe e/johnd@example.com`

Error Messages:

* Missing fields: \
  `Invalid command format! 
add: Adds a student to the address book. Parameters: i/STUDENTID n/NAME p/PHONE e/EMAIL g/GITHUB_USERNAME [t/TAG]...
Example: add i/A1234567X n/John Doe p/98765432 e/johnd@example.com g/JohnDoe t/friends t/owesMoney`
* Duplicate Identifier (Student ID): \
    `This student already exists in the address book`

### Listing all students : `list`

Shows a list of all students and their information.

Format: `list`

### Editing a student : `edit`

Edits an existing student in the address book.

Format: `edit INDEX [i/STUDENT ID] [n/NAME] [p/PHONE] [e/EMAIL] [g/GITHUB USERNAME] [t/TAG]…​`

* Edits the student at the specified `INDEX`. 
The index refers to the index number shown in the displayed student list. 
The index **must be a positive integer** 1, 2, 3, …​
* You must provide at least 1 of the optional fields.
* Existing values will be updated to the input values.
  
<box type="warning" seamless>

**Caution:** 
When editing tags, the existing tags of the student will be removed 
i.e adding of tags is not cumulative.

You can remove all the student’s tags by typing `t/` without
specifying any tags after it.

</box>

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st student to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd student to be `Betsy Crower` and clears all existing tags.

### Marking Lab Attendance: `marka`

Marks the lab attendance of an existing student in the address book.

Format: `marka INDEX l/LABNUMBER`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* `LABNUMBER` represents the lab session to mark attendance for. It **must be between 1 and 10 (inclusive)**.
* Attendance can only be **marked once per lab**.

Example:
* `marka 2 l/7` marks Lab 7 of the second student as attended.

### Marking Exercise Status: `marke`

Marks the exercise status of an existing student in the address book.

Format: `marke INDEX ei/EXERCISENUMBER s/STATUSLETTER`

* Edits the student at the specified `INDEX`. The index refers to the index number shown in the displayed student list. The index **must be a positive integer** 1, 2, 3, …​
* `EXERCISE` represents the lab session to mark attendance for. It **must be between 0 and 9 (inclusive)**.
* `STATUS` represents the status to mark the exercise with. It **must be a letter chosen from the following**:

| Letter  | Status Name | Meaning                              |
|---------|-------------|--------------------------------------|
| `D`/`d` | Done        | The exercise is completed.           |
| `N`/`n` | Not Done    | The exercise has not been completed. |
| `O`/`o` | Overdue     | The exercise is overdue or late.     |
* Each exercise only has **one status**.

Example:
* `marke 2 ei/7 s/d` marks exercise 7 of the second student as done.

### Locating students by name: `find`

Finds students whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Students matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Sorting the students: `sort`

Sorts the student by a specified criterion.

Format: `sort c/SORTCRITERION`

* `SORTCRITERION` determines how the student list should be sorted. It must be one of the following:
  * `name` Sorts students by their name (alphabetically)
  * `id` Sorts students by their Student Id
  * `lab` Sorts students by their Lab Attendance Rate (Highest to lowest)
  * `ex` Sorts students by their progress in their exercises (Highest to lowest)

<box type="tip" seamless>

**Tip:** The criterion is case-insensitive!
</box>

Examples:
`sort c/name` sorts the students by their name.
`sorts c/lab` sorts the students by their lab attendance rate.

### Deleting a student : `delete`

Deletes the specified student from the address book.

Format: `delete INDEX`

* Deletes the student at the specified `INDEX`. The index refers to the index number shown in the **displayed** student list. The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd student in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st student in the results of the `find` command.

Error Messages:

* Missing fields & Non-positive index: \
  `Invalid command format! 
    delete: Deletes the student identified by the index number used in the displayed student list.
    Parameters: INDEX (must be a positive integer)
    Example: delete 1`
*  Index out of range: \
  `The student index provided is invalid`

### Clearing all entries : `clear`

Clears **all** entries from the address book, leaving it completely empty.

Format: `clear`

<box type="warning" seamless>

**Caution:**
This command will remove **all** entries from the address book. If mistakenly performed, type `undo` **immediately**
before using another data-modifying command.
</box>

### Undoing the last command : `undo`

Reverses the **most recent** command that modified student data in LambdaLab.

Format: `undo`

* Only commands that change student data can be undone (e.g., `add`, `delete`, `edit`, `marka`, `marke`, `clear`)
* Commands that do not modify data cannot be undone (e.g., `help`, `list`, `find`, `exit`)
* It undos and can only undo the **very last** command that modified data
* If there is no command to undo, an error message will be displayed

<box type="warning" seamless>

**Caution:**
This command only undoes the most recent data-modifying command. You cannot undo multiple data-modifying commands or skip 
back to earlier changes. 
</box>

<box type="tip" seamless>

**Tip:** Use `undo` immediately after making a mistake to quickly restore your previous data state.
</box>

Examples:
* `delete 2` followed by `undo` restores the deleted student back to the list
* `edit 1 n/Wrong Name` followed by `undo` reverts the student's name to its original value
* `add n/John Doe p/12345678 e/john@u.nus.edu a/College Avenue` followed by `undo` removes the newly added student
* `delete 2` followed by `list` followed by `undo` still restores the deleted student back to the list
* `delete 1` followed by `edit 1 n/Wrong Name` followed by 2 consecutive `undo`s only reverts the student's name 
to its original value, but cannot restore the deleted student back to the list

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**:
1. Locate your data file on your current computer:
    - Navigate to the folder where your LambdaLab `.jar` file is located
    - Look for a folder named `data`
    - Inside, you'll find a file called `addressbook.json` - this contains all your student records
2. Install LambdaLab on your new computer:
    - Download the `.jar` file and place it in your desired folder
    - Run it once to generate the initial file structure
3. Transfer your data:
    - Copy the `addressbook.json` file from your old computer
    - On your new computer, navigate to `[JAR file location]/data/`
    - Replace the empty `addressbook.json` file with your copied file
4. Restart LambdaLab on your new computer to see all your student data
<box type="tip" seamless>
**Tip:** You can also backup your data regularly by copying the `addressbook.json` file to a secure location (e.g., cloud storage, USB drive).
</box>

**Q**: Can I track multiple tutorial groups in LambdaLab?<br>
**A**: Currently, LambdaLab is designed to manage one tutorial group per data file. If you teach multiple tutorial 
groups, you can maintain separate `.jar` files in different folders, each with its own data file.

**Q**: What happens if I accidentally mark the wrong lab attendance or exercise status?<br>
**A**: You can use the `undo` command immediately after the mistake to reverse it. Alternatively, use the `edit` 
command or the respective `marka`/`marke` commands to update the information manually.

**Q**: How do I keep track of which labs and exercises are current for CS2030S?<br>
**A**: LambdaLab allows you to mark labs 1-10 and exercises 0-9. You'll need to refer to the CS2030S course schedule 
to determine which labs and exercises are currently active. We recommend keeping a note of the current week's lab 
and exercise numbers.

**Q**: Can I export my student data to a spreadsheet?<br>
**A**: Currently, data is stored in JSON format in the `data/addressbook.json` file. Advanced users can manually convert
this to a spreadsheet format using external tools, but direct export functionality will be coming in v2.0.

**Q**: What should I do if a student's information changes (e.g., github username or email)?<br>
**A**: Use the `edit` command with the student's index number to update their information. For example:
`edit 1 g/newUserName e/newemail@u.nus.edu`

**Q**: Why can't I undo the `list` or `find` command?<br>
**A**: The `undo` command only works for commands that modify student data. Commands like `list`, `find`, and `help` 
only display information without changing any data, so there's nothing to undo.

**Q**: How do I add GitHub usernames for students who don't have one yet?<br>
**A**: All students must have a GitHub username when first added to LambdaLab. If a student doesn't have one yet, 
you can use a placeholder (e.g., "pending") and update it later using the `edit` command once they create their account.

**Q**: Can I mark attendance for a lab session that hasn't happened yet?<br>
**A**: Yes, LambdaLab allows you to mark any lab from 1-10. However, we recommend marking attendance only for completed 
lab sessions to maintain accurate records.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [i/STUDENT ID] [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [g/GITHUB USERNAME] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
**Mark Attendance** | `marka INDEX l/LABNUMBER` <br> e.g. `marka 2 l/7`
**Mark Exercise** | `marke INDEX ei/EXERCISENUMBER s/STATUSLETTER` <br> e.g. `marke 2 ei/7 s/d`
**Sort**    | `sort`
**Undo** | `undo`
**Exit**   | `exit`


