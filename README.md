# User Guide

## Quick Start
1. Ensure you have Java 11 or above installed in your Computer.
1. Download the latest release from [here](https://github.com/durianpancakes/ip/releases/tag/A-Release).
1. Place the file into a folder.
1. With your computer's terminal, navigate to the `ip.jar` file and run the app.
1. Refer to the features below for details of the commands available in Duke.

## Features

### Viewing help: `help`

Shows a message containing all possible commands accepted by Duke

Format: `help`

<a id="add"></a>
### Adding a Todo: `todo`

Adds a Todo to the task list

Format: `todo DESCRIPTION`

Examples:
* `todo eat dinner`
* `todo take dog out for a run`

Expected outcome:
* `Added successfully: [T][✘]eat dinner`
* `Added successfully: [T][✘]take dog out for a run`

### Adding a Deadline: `deadline`

Adds a Deadline to the task list

Format: `deadline DESCRIPTION /by DATE_TIME<yyyy-MM-dd HHmm>`

Example:
* `deadline Finish homework /by 2020-09-21 1800`

Expected outcome:
* `Added successfully: [D][✘]Finish homework (by: Sep 21 2020 06:00 PM)`

### Adding an Event: `event`

Adds an Event to the task list

Format: `event DESCRIPTION /at FROM_DATE_TIME<yyyy-MM-dd HHmm> /to TO_DATE_TIME<yyyy-MM-dd HHmm>`

Example:
* `event Math exam /at 2020-10-22 0900 /to 2020-10-22 1000`

Expected outcome:
* `Added successfully: [E][✘]Math exam (at: Oct 22 2020 09:00 AM to Oct 22 2020 10:00 AM)`

### Listing all tasks: `list`
Shows a list of all tasks in the task list

Format: `list`

Expected outcome:
* `Here are the tasks in your list:`  
  `1. [D][✘]Finish homework (by: Sep 21 2020 06:00 PM)`  
  `2. [E][✘]Math exam (at: Oct 22 2020 09:00 AM to Oct 22 2020 10:00 AM)`

### Listing all tasks on a given day: `list`

Shows a list of all tasks happening on a given day in the task list

Format: `list DATE`

Examples:
* `list 2020-10-30`
* `list 2020-09-21`

Expected outcomes:
* `List is empty!` (because there are no tasks matching '2020-10-30')
* `Here are the tasks in your list:`  
  `1. [D][✘]Finish homework (by: Sep 21 2020 06:00 PM)`  
  
### Locating tasks by name: `find`

Find tasks whose name contain any of the given keywords.

Format: `find KEYWORD`

Examples:
* `find dinner`
* `find homework`

Expected outcomes:
* `List is empty!` (because there are no tasks matching the keyword 'dinner')
* `Here are the tasks in your list:`  
  `1. [D][✘]Finish homework (by: Sep 21 2020 06:00 PM)`

### Deleting a task: `delete`

Delete the task of a given index

Format: `delete INDEX`

Examples:
* `delete 2`
* `delete 13`

Expected outcomes:
* `Noted, I have deleted this task`  
  `[E][✘]Math exam (at: Oct 22 2020 09:00 AM to Oct 22 2020 10:00 AM)` (index given is valid)
* `Did you give me the wrong task number?` (because the index given is out of bounds)

### Editing a task's done status: `done`

Changes a task's done status to done

Format: `done INDEX`

Examples: 
* `done 1`
* `done 13`

Expected outcomes:
* `Nice! I've marked this task as done:`  
  `[D][✓]Finish homework (by: Sep 21 2020 06:00 PM)` (index given is valid)
* `Did you give me the wrong task number?` (because the index given is out of bounds)

### Exiting the program: `bye`

Exits the program

Format: `bye`

Expected outcome:
* `Tasks saved successfully.`  
  `Bye! See you again soon :)`

### Saving the data

Duke saves all data automatically after any command that changes the data. There is no need to save manually.

## Command summary

Action | Format, Examples
-------|-----------------
help|`help`
list|`list` `list DATE` e.g., `list 2020-09-21`
todo|`todo DESCRIPTION` e.g., `todo eat dinner`
deadline|`deadline DESCRIPTION /by DATE_TIME` e.g., `deadline Finish homework /by 2020-09-21 1800`
event|`event DESCRIPTION /at FROM_DATE_TIME /to TO_DATE_TIME` e.g., `event Math exam /at 2020-10-22 0900 /to 2020-10-22 1000`
find|`find KEYWORD` e.g., `find dinner` `find assignment`
delete|`delete INDEX` e.g., `delete 13`
done|`done INDEX` e.g., `done 13`
bye|`bye`
