---
layout: page
title: User Guide
---

**TutorFlow** is a desktop app for tuition centre managers. It helps you keep track of students, parents, tutors, and classes using simple type-and-press-Enter commands. For those comfortable with a command line, TutorFlow can get your contact management tasks done quickly and easily.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick Start

1. Install Java `17` or newer on your computer.<br>
   **Mac users:** Follow the guide [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest app file (`.jar`) from [here](https://github.com/AY2526S1-CS2103T-T11-4/tp/releases).

3. Copy the file into a folder where you want your TutorFlow data to be saved.

4. Open a command terminal, navigate to that folder, and run: `java -jar TutorFlow.jar`<br>
   The app window should appear with sample data so you can try things out.<br>
   ![Ui](images/Ui.png)

5. Type a command in the box and press **Enter** to execute it. <br>
   Some example commands you can try:

   * `help` : Opens the help window.

   * `list` : Lists all contacts.

   * `add c/student n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a student named `John Doe` to the address book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Clears all contacts in the address book.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) section below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: How to read command formats**<br>

*  `UPPER_CASE`: Placeholders that you replace with your own information. <br>
  Example: In `n/NAME`, you replace `NAME` with the actual name: `n/John Doe`.

* `[square_brackets]`: Optional parts of a command. You can leave them out if you don't need them.<br>
  Example: `getClasses [n/TUTOR_NAME]` can be `getClasses n/Eric Hanson` or just `getClasses`.

* `*` (asterisk): Case-insensitive inputs. <br>
  Example: `list [c/*CATEGORY]` can be `list c/tutor`, `list c/STUDENT`, or `list c/Parent`. 

* `...`(ellipsis): Multiple inputs are accepted. <br>
  Example: `find *KEYWORD...` can be `find alex` or `find alex bryan david`

* Extra text for commands that don't take inputs (like `help`, `exit`, `clear`) will be ignored. <br>
  Example: `help 123` is treated as `help`.
</div>


### App Basics

#### Viewing help : `help`

Displays a window with a link to the help page.

![help message](images/helpMessage.png)

Format: `help`

• [Back to Command Summary](#command-summary)

#### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

• [Back to Command Summary](#command-summary)

#### Exiting the program : `exit`

Exits the program.

Format: `exit`

• [Back to Command Summary](#command-summary)



### People Management

#### Adding a person: `add`

Adds a person to the address book.

Format: `add c/*CATEGORY n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`
* `CATEGORY` must be one of `tutor`, `student`, `parent`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
You cannot add a duplicate person (i.e. same **category** and **name**)
</div>

Examples:
* `add c/student n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add c/TUTOR n/Betsy Crowe t/GP Paper 1 e/betsycrowe@example.com a/Newgate Prison p/1234567 t/New hire`

• [Back to Command Summary](#command-summary)

#### Listing all persons : `list`

Shows a list of all persons in the address book. If you add a category, it shows all persons belonging to that category.

Format: `list [c/*CATEGORY]`

* Without `c/*CATEGORY`: shows all persons in the system.
* With `c/*CATEGORY`: shows all persons belonging to the specified category `tutor`, `student` or `parent`.

Examples:
* `list` shows all persons.
* `list c/tutor` shows all tutors.

• [Back to Command Summary](#command-summary)

#### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [c/*CATEGORY] [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX` in the currently displayed list. 
* The index must be a **positive number** (e.g. 1, 2, 3, …)
* You must provide at least one field to change (e.g. n/, p/).
* Editing tags will **replace all old tags** with the new ones you provide. To clear all tags, simply type t/ with nothing after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

• [Back to Command Summary](#command-summary)

#### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find *KEYWORD...`

* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned.
  e.g. `hans yap` will return `Hans Bo`, `Yap Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>

  ![result for 'find alex david'](images/findAlexDavidResult.png)

• [Back to Command Summary](#command-summary)

#### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX` in the currently displayed list. 
* The index must be a **positive number** (e.g. 1, 2, 3, …)

Examples:
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.
* `list` followed by `delete 2` deletes the 2nd person in the address book.

![result for 'delete 2'](images/delete2Result.png)

• [Back to Command Summary](#command-summary)

### Relationship Management

#### Linking a student to a parent : `linkParent`

Links an existing student to an existing parent in the address book.

Format: `linkParent n/STUDENT_NAME n/PARENT_NAME`

* Links the student identified by  `STUDENT_NAME` to the parent identified by `PARENT_NAME`.
* The names must **exactly match** a student or a parent in TutorFlow (case-sensitive).
* The person identified as the student must have the `student` category, and the person identified as the parent must have the `parent` category.

Example:
* `linkParent n/Alice Pauline n/Daniel Meier` Links the student 'Alice Pauline' to the parent 'Daniel Meier', assuming both exist in the address book with the correct categories.

• [Back to Command Summary](#command-summary)

#### Finding a student's parent: `getParent`

Displays the parent of a specified student.

Format: `getParent n/STUDENT_NAME`
* The student name must **exactly match** a student in TutorFlow (case-sensitive).
* The student identified must have the `student` category.

Examples:
* `getParent n/John Doe` shows the parent of student John Doe.

• [Back to Command Summary](#command-summary)

#### Finding all students of a tutor: `getStudents`

Displays all students of a specified tutor.

Format: `getStudents n/TUTOR_NAME`
* The tutor name must **exactly match** a tutor in TutorFlow (case-sensitive). 
* The tutor identified must have the `tutor` category.

Examples:
* `getStudents n/Roy Balakrishnan` shows all students of tutor Roy Balakrishnan.

• [Back to Command Summary](#command-summary)

### Class Management

#### Creating a class: `createClass`

Creates a new class in the system. Create this first before linking a tutor and students to it.

Format: `createClass d/*DAY ti/*TIME`

* `DAY` must be a valid day of the week (e.g. Monday, TUESDAY, wednesday). 
* `TIME` must be a supported timeslot `H12`, `H14`, `H16`, `H18`, `H20`, which represents `12:00PM`, `2:00PM`, `4:00PM`, `6:00PM`, `8:00PM` respectively.
* The class is created without linked persons. Link a tutor or student using [`linkClass`](#linking-a-person-to-a-class-linkclass).

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Each timeslot (combination of a day and a time) is unique. You cannot create a class for a timeslot that already exists.
</div>

Examples:
* `createClass d/MONDAY ti/H16` creates a class on Monday at 4:00 PM.
* `createClass d/tuesday ti/h12` creates a class on Tuesday at 12:00 PM.

• [Back to Command Summary](#command-summary)

#### Linking a person to a class: `linkClass`

Links an existing student or tutor to an existing class.

Format: `linkClass d/*DAY ti/*TIME n/NAME`

* `NAME` must **exactly match** a student or a tutor in TutorFlow (case-sensitive). 
* The class identified by `DAY` and `TIME` must already exist (created using [`createClass`](#creating-a-class-createclass)).

The `linkClass` command works differently for students and tutors. Here’s what you need to know:

**Linking a Student to a Class**

Each student can be only be enrolled in one class at a time.

| Scenario                              | Result                                                                                                                                                        |
|---------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| The student is **not** in any class   | **Success!** The student is added to the class.                                                                                                               |
| The student is **already** in a class | **Error.** The app will tell you the student is already linked. Use [`unlinkClass`](#removing-a-person-from-a-class-unlinkclass) to remove the current class. |

**Assigning a Tutor to a Class**

Each class can only have one tutor, but a tutor can teach multiple classes.

| Scenario                                    | Result                                                                                                                                  |
|---------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| The class has **no tutor**                  | **Success!** The tutor is assigned to the class.                                                                                        |
| The class **already has a tutor**           | **Error.** The app will tell you to use [`unlinkClass`](#removing-a-person-from-a-class-unlinkclass) to remove the current tutor first. |
| The tutor is **already teaching** the class | **Error.** The app will tell you the tutor is already assigned.                                                                         |

Examples:
* `linkClass d/MONDAY ti/H16 n/Roy Balakrishnan` links tutor Roy Balakrishnan to the Monday 4:00 PM class.
* `linkClass d/saturday ti/h12 n/Alice Pauline` links student Alice Pauline to the Saturday 12:00 PM class.

• [Back to Command Summary](#command-summary)

#### Removing a person from a class: `unlinkClass`

Removes a person from an existing class.

Format: `unlinkClass d/*DAY ti/*TIME n/NAME`

* `NAME` must **exactly match** a student or tutor currently linked to the class (case-sensitive).
* The class identified by `DAY` and `TIME` must already exist (created using [`createClass`](#creating-a-class-createclass)).

Examples:
* `unlinkClass d/MONDAY ti/H16 n/Roy Balakrishnan` removes tutor Roy Balakrishnan from the Monday 4:00 PM class.
* `unlinkClass d/saturday ti/h12 n/Alice Pauline` removes student Alice Pauline from the Saturday 12:00 PM class.

• [Back to Command Summary](#command-summary)

#### Viewing class details: `getClassDetails`

Shows the tutor (if any) and students (if any) linked to the specific class.

Format: `getClassDetails d/*DAY ti/*TIME`

* The class identified by `DAY` and `TIME` must already exist (created using [`createClass`](#creating-a-class-createclass)).
* To add people to a class, see [`linkClass`](#linking-a-person-to-a-class-linkclass).

Examples:
* `getClassDetails d/MONDAY ti/H16` displays the tutor and students in the Monday 4:00 PM class.
* `getClassDetails d/saturday ti/h12` displays the tutor and students in the Saturday 12:00 PM class.

• [Back to Command Summary](#command-summary)

#### Listing classes: `getClasses`

Shows all existing classes in TutorFlow. If you add a tutor's name, it displays the tutor’s classes.

Format: `getClasses [n/TUTOR_NAME]`

* Without `n/TUTOR_NAME`: shows all existing classes created using [`createClass`](#creating-a-class-createclass).
* With `n/TUTOR_NAME`: shows only classes linked to the specified tutor using [`linkClass`](#linking-a-person-to-a-class-linkclass).
* The tutor name must **exactly match** a tutor in TutorFlow (case-sensitive).

Examples:
* `getClasses` shows all classes.
* `getClasses n/Roy Balakrishnan` shows classes linked to tutor Roy Balakrishnan.

• [Back to Command Summary](#command-summary)

### Data Storage
* TutorFlow data is saved in the hard disk automatically after any command that changes the data. This data is stored in `[TutorFlow.jar location]/data/addressbook.json`
* Advanced users are welcome to update data directly by editing the data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
Manual changes to the file may corrupt TutorFlow's data. Always save a backup of the file before editing it.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my TutorFlow data to another computer?<br>
**A**: Install TutorFlow on your new computer (refer [here](#quick-start)). After running the app once, replace the `addressbook.json` data file with your backup.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **TutorFlow opens off-screen** if previously moved to a secondary screen.<br>Solution: Delete `preferences.json` before running the app again.
2. **Help Window will not reappear** if previously minimised.<br>Solution: Manually restore the minimised window. 

--------------------------------------------------------------------------------------------------------------------

## Command Summary

Action | Format, Examples
--------|------------------
[Add](#adding-a-person-add) | `add c/*CATEGORY n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add c/parent n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/J2 t/Trial lesson`
[List](#listing-all-persons--list) | `list [c/*CATEGORY]` <br> e.g., `list c/student`
[Edit](#editing-a-person--edit) | `edit INDEX [c/*CATEGORY] [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
[Find](#locating-persons-by-name-find) | `find *KEYWORD...`<br> e.g., `find James Jake`
[Delete](#deleting-a-person--delete) | `delete INDEX`<br> e.g., `delete 3`
[Link Parent](#linking-a-student-to-a-parent--linkparent) | `linkParent n/STUDENT_NAME n/PARENT_NAME`<br> e.g., `linkParent n/Alice Pauline n/Fiona Kunz`
[Get Parent](#finding-a-students-parent-getparent) | `getParent n/STUDENT_NAME`<br> e.g., `getParent n/John Doe`
[Get Students](#finding-all-students-of-a-tutor-getstudents) | `getStudents n/TUTOR_NAME`<br> e.g., `getStudents n/Roy Balakrishnan`
[Create Class](#creating-a-class-createclass) | `createClass d/*DAY ti/*TIME`<br> e.g., `createClass d/MONDAY ti/H16`
[Link Class](#linking-a-person-to-a-class-linkclass) | `linkClass d/*DAY ti/*TIME n/NAME`<br> e.g., `linkClass d/MONDAY ti/H16 n/Roy Balakrishnan`
[Unlink Class](#removing-a-person-from-a-class-unlinkclass) | `unlinkClass d/*DAY ti/*TIME n/NAME`<br> e.g., `unlinkClass d/MONDAY ti/H16 n/Alice Pauline`
[Get Class Details](#viewing-class-details-getclassdetails) | `getClassDetails d/*DAY ti/*TIME`<br> e.g., `getClassDetails d/MONDAY ti/H16`
[Get Classes](#listing-classes-getclasses) | `getClasses [n/TUTOR_NAME]`<br> e.g., `getClasses n/Roy Balakrishnan`
[Clear](#clearing-all-entries--clear) | `clear`
[Help](#viewing-help--help) | `help`
[Exit](#exiting-the-program--exit) | `exit`
