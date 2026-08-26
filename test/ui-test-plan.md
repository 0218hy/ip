# Console UI Test Plan

Run these tests from the project root with:

```sh
python3 skills/test-ui/scripts/run_ui_tests.py
```
 
Each test case records its aim, command, console input, and complete expected console output. Test cases run in the order listed. The runner stops at the first failure.

## Test Case: Save tasks for the next startup

### Aim
Verify that a task created in one session is stored for a later session.

### Command
```sh
rm -f /private/tmp/pebby-ui-restart.txt && java -Dpebby.storage.path=/private/tmp/pebby-ui-restart.txt -cp out/production/ip Pebby
```

### Input
```text
todo persist this task
bye
```

### Expected Output
```text
____________________________________________________________
 ____       _     _          
|  _ \  ___| |__ | |__  _   _
| |_) |/ _ \ '_ \| '_ \| | | |
|  __/|  __/ |_) | |_) | |_| |
|_|    \___|_.__/|_.__/ \__, |
                         |___/
Hello! I'm Pebby.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task: 
  [T] [ ] persist this task
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Bye Bye!
____________________________________________________________
```

## Test Case: Load tasks after restart

### Aim
Verify that the task from the preceding session is available immediately after Pebby starts again.

### Command
```sh
java -Dpebby.storage.path=/private/tmp/pebby-ui-restart.txt -cp out/production/ip Pebby
```

### Input
```text
list
bye
```

### Expected Output
```text
____________________________________________________________
 ____       _     _          
|  _ \  ___| |__ | |__  _   _
| |_) |/ _ \ '_ \| '_ \| | | |
|  __/|  __/ |_) | |_) | |_| |
|_|    \___|_.__/|_.__/ \__, |
                         |___/
Hello! I'm Pebby.
What can I do for you?
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1. [T] [ ] persist this task
____________________________________________________________
____________________________________________________________
Bye Bye!
____________________________________________________________
```

## Test Case: Reject an invalid task number

### Aim
Verify that an invalid mark command reports an error instead of crashing Pebby.

### Command
```sh
rm -f /private/tmp/pebby-ui-invalid-mark.txt && java -Dpebby.storage.path=/private/tmp/pebby-ui-invalid-mark.txt -cp out/production/ip Pebby
```

### Input
```text
mark zero
bye
```

### Expected Output
```text
____________________________________________________________
 ____       _     _          
|  _ \  ___| |__ | |__  _   _
| |_) |/ _ \ '_ \| '_ \| | | |
|  __/|  __/ |_) | |_) | |_| |
|_|    \___|_.__/|_.__/ \__, |
                         |___/
Hello! I'm Pebby.
What can I do for you?
____________________________________________________________
____________________________________________________________
Invalid mark: Please provide a whole task number.
____________________________________________________________
____________________________________________________________
Bye Bye!
____________________________________________________________
```

## Test Case: Add a deadline

### Aim
Verify that a deadline command stores the task description and the text after /by as the deadline.

### Command
```sh
rm -f /private/tmp/pebby-ui-deadline.txt && java -Dpebby.storage.path=/private/tmp/pebby-ui-deadline.txt -cp out/production/ip Pebby
```

### Input
```text
deadline return book /by Sunday
bye
```

### Expected Output
```text
____________________________________________________________
 ____       _     _          
|  _ \  ___| |__ | |__  _   _
| |_) |/ _ \ '_ \| '_ \| | | |
|  __/|  __/ |_) | |_) | |_| |
|_|    \___|_.__/|_.__/ \__, |
                         |___/
Hello! I'm Pebby.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task: 
  [D] [ ] return book (by: Sunday)
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Bye Bye!
____________________________________________________________
```

## Test Case: Add a todo

### Aim
Verify that a todo command stores its description and reports the updated task count.

### Command
```sh
rm -f /private/tmp/pebby-ui-todo.txt && java -Dpebby.storage.path=/private/tmp/pebby-ui-todo.txt -cp out/production/ip Pebby
```

### Input
```text
todo read book
bye
```

### Expected Output
```text
____________________________________________________________
 ____       _     _          
|  _ \  ___| |__ | |__  _   _
| |_) |/ _ \ '_ \| '_ \| | | |
|  __/|  __/ |_) | |_) | |_| |
|_|    \___|_.__/|_.__/ \__, |
                         |___/
Hello! I'm Pebby.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task: 
  [T] [ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Bye Bye!
____________________________________________________________
```

## Test Case: Add an event

### Aim
Verify that an event command stores its description, start time, end time, and updated task count.

### Command
```sh
rm -f /private/tmp/pebby-ui-event.txt && java -Dpebby.storage.path=/private/tmp/pebby-ui-event.txt -cp out/production/ip Pebby
```

### Input
```text
event project meeting /from Monday /to Tuesday
bye
```

### Expected Output
```text
____________________________________________________________
 ____       _     _          
|  _ \  ___| |__ | |__  _   _
| |_) |/ _ \ '_ \| '_ \| | | |
|  __/|  __/ |_) | |_) | |_| |
|_|    \___|_.__/|_.__/ \__, |
                         |___/
Hello! I'm Pebby.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task: 
  [E] [ ] project meeting (from: Monday to: Tuesday)
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Bye Bye!
____________________________________________________________
```

## Test Case: Delete a task

### Aim
Verify that deleting a task by its 1-based list position removes the correct task and updates the task count.

### Command
```sh
rm -f /private/tmp/pebby-ui-delete.txt && java -Dpebby.storage.path=/private/tmp/pebby-ui-delete.txt -cp out/production/ip Pebby
```

### Input
```text
todo first task
todo second task
delete 1
list
bye
```

### Expected Output
```text
____________________________________________________________
 ____       _     _          
|  _ \  ___| |__ | |__  _   _
| |_) |/ _ \ '_ \| '_ \| | | |
|  __/|  __/ |_) | |_) | |_| |
|_|    \___|_.__/|_.__/ \__, |
                         |___/
Hello! I'm Pebby.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task: 
  [T] [ ] first task
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task: 
  [T] [ ] second task
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Noted. I've removed this task: 
[T] [ ] first task
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1. [T] [ ] second task
____________________________________________________________
____________________________________________________________
Bye Bye!
____________________________________________________________
```
