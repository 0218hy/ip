# Console UI Test Plan

Run these tests from the project root with:

```sh
python3 skills/test-ui/scripts/run_ui_tests.py
```

Each test case records its aim, command, console input, and complete expected console output. Test cases run in the order listed. The runner stops at the first failure.

## Test Case: Add a deadline

### Aim
Verify that a deadline command stores the task description and the text after /by as the deadline.

### Command
```sh
java -cp out/production/ip Pebby
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
