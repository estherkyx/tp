---
layout: page
title: User Guide
---

<div class="intro-para"><b>TutorFlow</b> is a desktop app for tuition centre managers. It helps you keep track of students, parents, tutors, and classes using simple type-and-press-Enter commands. For those comfortable with a command line, TutorFlow can get your contact management tasks done quickly and easily.</div><span class="short-break"></span>

* Table of Contents
  {:toc}

<div style="page-break-after: always;"></div>
<div class="print-tight"></div>

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

6. Refer to the [Features](#features) below for details of each command.


<div style="page-break-after: always;"></div>
<div class="print-tight"></div>


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

<!-- <div class="print-tight"></div> -->

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

<div style="page-break-after: always;"></div>
<div class="print-tight"></div>

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

Displays all persons in the address book. If you add a category, it shows all persons belonging to that category.

Format: `list [c/*CATEGORY]`

* Without `c/*CATEGORY`: shows all persons in the system.
* With `c/*CATEGORY`: shows all persons belonging to the specified category `tutor`, `student` or `parent`.

Examples:
* `list` shows all persons.
* `list c/tutor` shows all tutors.

• [Back to Command Summary](#command-summary)

<div style="page-break-after: always;"></div>

<div class="print-tight"></div>
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
  e.g. `hans yap` will return `Hans Bo`, `Yap`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>

![result for 'find alex david'](images/findAlexDavidResult.png)

• [Back to Command Summary](#command-summary)

<div class="print-tight-double"></div>
<div class="print-tight-double"></div>

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

<div style="page-break-after: always;"></div>
<div class="print-tight"></div>

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

Displays the parent linked to the specified student.

Format: `getParent n/STUDENT_NAME`
* The student name must **exactly match** a student in TutorFlow (case-sensitive).
* The student identified must have the `student` category.

Examples:
* `getParent n/John Doe` shows the parent of student John Doe.

• [Back to Command Summary](#command-summary)

#### Finding all students of a tutor: `getStudents`

Displays all students linked to the specified tutor.

Format: `getStudents n/TUTOR_NAME`
* The tutor name must **exactly match** a tutor in TutorFlow (case-sensitive).
* The tutor identified must have the `tutor` category.

Examples:
* `getStudents n/Roy Balakrishnan` shows all students of tutor Roy Balakrishnan.

• [Back to Command Summary](#command-summary)

<div style="page-break-after: always;"></div>
<div class="print-tight"></div>

### Class Management

#### Creating a class: `createClass`

Creates a new class. This must be done before linking a tutor or students to it.

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

Format: `linkClass d/*DAY ti/*TIME n/TUTOR_NAME or STUDENT_NAME [c/*CATEGORY]`

* `NAME` must **exactly match** a student or a tutor in TutorFlow (case-sensitive).
* The class identified by `DAY` and `TIME` must already exist (created using [`createClass`](#creating-a-class-createclass)).
* If there is a student and a tutor with the exact same name, the optional category parameter can be used to differentiate them. Otherwise, the person who is higher in the list will be linked.

The `linkClass` command works differently for students and tutors. Here’s what you need to know:

**Linking a Student to a Class**

Each student can be only be enrolled in one class at a time.

<table class="indent-table">
  <tr>
    <th>Scenario</th>
    <th>Result</th>
  </tr>
  <tr>
    <td>The student is <b>not</b> in any class</td>
    <td><b>Success!</b> The student is added to the class.</td>
  </tr>
  <tr>
    <td>The student is <b>already</b> in a class</td>
    <td><b>Error.</b> The app will tell you the student is already linked. Use <a href="#removing-a-person-from-a-class-unlinkclass"><code>unlinkClass</code></a> to remove the current class.</td>
  </tr>
</table>

<div style="page-break-after: always;"></div>
<div class="print-tight"></div>
<div class="print-tight"></div>
<div class="print-tight-double"></div>

**Assigning a Tutor to a Class**

Each class can only have one tutor, but a tutor can teach multiple classes.

<table class="indent-table">
  <tr>
    <th>Scenario</th>
    <th>Result</th>
  </tr>
  <tr>
    <td>The class has <b>no tutor</b></td>
    <td><b>Success!</b> The tutor is assigned to the class.</td>
  </tr>
  <tr>
    <td>The class <b>already has a tutor</b></td>
    <td><b>Error.</b> The app will tell you to use <a href="#removing-a-person-from-a-class-unlinkclass"><code>unlinkClass</code></a> to remove the current tutor first.</td>
  </tr>
  <tr>
    <td>The tutor is <b>already teaching</b> that class</td>
    <td><b>Error.</b> The app will tell you the tutor is already assigned.</td>
  </tr>
</table>

Examples:
* `linkClass d/MONDAY ti/H16 n/Roy Balakrishnan` links tutor Roy Balakrishnan to the Monday 4:00 PM class.
* `linkClass d/SATURDAY ti/H12 n/Alice Pauline c/student` links student Alice Pauline to the Saturday 12:00 PM class.

• [Back to Command Summary](#command-summary)

#### Removing a person from a class: `unlinkClass`

Removes a person from an existing class.

Format: `unlinkClass d/*DAY ti/*TIME n/TUTOR_NAME or STUDENT_NAME [c/*CATEGORY]`

* `NAME` must **exactly match** a student or tutor currently linked to the class (case-sensitive).
* The class identified by `DAY` and `TIME` must already exist (created using [`createClass`](#creating-a-class-createclass)).
* If there is a student and a tutor with the exact same name, the optional category parameter can be used to differentiate them. Otherwise, the person who is higher in the list will be unlinked.

Examples:
* `unlinkClass d/MONDAY ti/H16 n/Roy Balakrishnan` removes tutor Roy Balakrishnan from the Monday 4:00 PM class.
* `unlinkClass d/SATURDAY ti/H12 n/Alice Pauline c/student` removes student Alice Pauline from the Saturday 12:00 PM class.

• [Back to Command Summary](#command-summary)

<div style="page-break-after: always;"></div>
<div class="print-tight"></div>
<div class="print-tight-double"></div>

#### Viewing class details: `getClassDetails`

Shows the tutor (if any) and students (if any) linked to the specific class.

Format: `getClassDetails d/*DAY ti/*TIME`

* The class identified by `DAY` and `TIME` must already exist (created using [`createClass`](#creating-a-class-createclass)).
* To add people to a class, see [`linkClass`](#linking-a-person-to-a-class-linkclass).

Examples:
* `getClassDetails d/MONDAY ti/H16` displays the tutor and students in the Monday 4:00 PM class.

• [Back to Command Summary](#command-summary)

#### Listing classes: `getClasses`

Shows all existing classes. If you add a tutor's name, it displays only classes linked to that tutor.

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

<div style="page-break-after: always;"></div>
<div class="print-tight"></div>

## FAQ

**Q**: How do I transfer my TutorFlow data to another computer?<br>
**A**: Install TutorFlow on your new computer (refer [here](#quick-start)). After running the app once, replace the `addressbook.json` data file with your backup.


## Known issues

1. **TutorFlow opens off-screen** if previously moved to a secondary screen.<br>Solution: Delete `preferences.json` before running the app again.
2. **Help Window will not reappear** if previously minimised.<br>Solution: Manually restore the minimised window.

<div style="page-break-after: always;"></div>
<div class="print-tight"></div>

## Command Summary

<table class="indent-table command-summary-table">
  <tr>
    <th>Action</th>
    <th>Format, Examples</th>
  </tr>
  <tr>
    <td><a href="#adding-a-person-add">Add</a></td>
    <td><code>add c/*CATEGORY n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​</code><br>e.g., <code>add c/parent n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/J2 t/Trial lesson</code></td>
  </tr>
  <tr>
    <td><a href="#listing-all-persons--list">List</a></td>
    <td><code>list [c/*CATEGORY]</code> <br>e.g., <code>list c/student</code></td>
  </tr>
  <tr>
    <td><a href="#editing-a-person--edit">Edit</a></td>
    <td><code>edit INDEX [c/*CATEGORY] [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​</code><br>e.g., <code>edit 2 n/James Lee e/jameslee@example.com</code></td>
  </tr>
  <tr>
    <td><a href="#locating-persons-by-name-find">Find</a></td>
    <td><code>find *KEYWORD...</code><br>e.g., <code>find James Jake</code></td>
  </tr>
  <tr>
    <td><a href="#deleting-a-person--delete">Delete</a></td>
    <td><code>delete INDEX</code><br>e.g., <code>delete 3</code></td>
  </tr>
  <tr>
    <td><a href="#linking-a-student-to-a-parent--linkparent">Link Parent</a></td>
    <td><code>linkParent n/STUDENT_NAME n/PARENT_NAME</code><br>e.g., <code>linkParent n/Alice Pauline n/Fiona Kunz</code></td>
  </tr>
  <tr>
    <td><a href="#finding-a-students-parent-getparent">Get Parent</a></td>
    <td><code>getParent n/STUDENT_NAME</code><br>e.g., <code>getParent n/John Doe</code></td>
  </tr>
  <tr>
    <td><a href="#finding-all-students-of-a-tutor-getstudents">Get Students</a></td>
    <td><code>getStudents n/TUTOR_NAME</code><br>e.g., <code>getStudents n/Roy Balakrishnan</code></td>
  </tr>
  <tr>
    <td><a href="#creating-a-class-createclass">Create Class</a></td>
    <td><code>createClass d/*DAY ti/*TIME</code><br>e.g., <code>createClass d/MONDAY ti/H16</code></td>
  </tr>
  <tr>
    <td><a href="#linking-a-person-to-a-class-linkclass">Link Class</a></td>
    <td><code>linkClass d/*DAY ti/*TIME n/NAME [c/*CATEGORY]</code><br>e.g., <code>linkClass d/MONDAY ti/H16 n/Roy Balakrishnan</code></td>
  </tr>
  <tr>
    <td><a href="#removing-a-person-from-a-class-unlinkclass">Unlink Class</a></td>
    <td><code>unlinkClass d/*DAY ti/*TIME n/NAME [c/*CATEGORY]</code><br>e.g., <code>unlinkClass d/MONDAY ti/H16 n/Alice Pauline c/student</code></td>
  </tr>
  <tr>
    <td><a href="#viewing-class-details-getclassdetails">Get Class Details</a></td>
    <td><code>getClassDetails d/*DAY ti/*TIME</code><br>e.g., <code>getClassDetails d/MONDAY ti/H16</code></td>
  </tr>
  <tr>
    <td><a href="#listing-classes-getclasses">Get Classes</a></td>
    <td><code>getClasses [n/TUTOR_NAME]</code><br>e.g., <code>getClasses n/Roy Balakrishnan</code></td>
  </tr>
  <tr>
    <td><a href="#clearing-all-entries--clear">Clear</a></td>
    <td><code>clear</code></td>
  </tr>
  <tr>
    <td><a href="#viewing-help--help">Help</a></td>
    <td><code>help</code></td>
  </tr>
  <tr>
    <td><a href="#exiting-the-program--exit">Exit</a></td>
    <td><code>exit</code></td>
  </tr>
</table>
