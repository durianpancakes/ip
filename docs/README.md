# User Guide

## Setting up in Intellij

Prerequisites: JDK 11, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project dialog first)
1. Set up the correct JDK version, as follows:
   1. Click `Configure` > `Structure for New Projects` and then `Project Settings` > `Project` > `Project SDK`
   1. If JDK 11 is listed in the drop down, select it. If it is not, click `New...` and select the directory where you installed JDK 11
   1. Click `OK`
1. Import the project into Intellij as follows:
   1. Click `Open or Import`.
   1. Select the project directory, and click `OK`
   1. If there are any further prompts, accept the defaults.
1. After the importing is complete, locate the `src/main/java/Duke.java` file, right-click it, and choose `Run Duke.main()`. If the setup is correct, you should see something like the below:
   ```
   Hello from
    ____        _        
   |  _ \ _   _| | _____ 
   | | | | | | | |/ / _ \
   | |_| | |_| |   <  __/
   |____/ \__,_|_|\_\___|
   ```

## Features

### Viewing help: `help`
Shows a message containing all possible commands accepted by Duke

Format: `help`

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
* `You have not added any tasks yet!` (because there are no tasks matching '2020-10-30')
* `Here are the tasks in your list:`  
  `1. [D][✘]Finish homework (by: Sep 21 2020 06:00 PM)`  
  
### Locating tasks by name: `find`
Find tasks whose name contain any of the given keywords.

Format: `find KEYWORD`

Examples:
* `find dinner`
* `find homework`

Expected outcomes:
* `You have not added any tasks yet!` (because there are no tasks matching the keyword 'dinner')
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
