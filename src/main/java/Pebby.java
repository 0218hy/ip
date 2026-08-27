import java.util.ArrayList;
import java.io.IOException;
import java.time.LocalDate;

/** A command-line task chatbot that keeps tasks in a local data file. */
public class Pebby {
//  public static Task[] tasks = new Task[100];
//  public static int taskCount = 0;
    public static ArrayList<Task> tasks = new ArrayList<>();
    private static final Storage storage = new Storage();

//    No longer usse pure Task
//    public static String addTask(String description) {
//        Task newTask = new Task(description);
//        tasks.add(newTask);
//        taskCount++;
//        return "added: " + newTask.description + '\n';
//    }

    public static String listTask() {
        StringBuilder taskList = new StringBuilder();
        taskList.append("Here are the tasks in your list:").append('\n');
        for (int i = 0; i < tasks.size(); i++) {
            int taskNo = i + 1;
            taskList.append(taskNo).append(". ").append(tasks.get(i).toString()).append('\n');
        }
        return taskList.toString();
    }

    public static String markTask(int taskNo) {
        Task task = tasks.get(taskNo);
        task.markAsDone();
        String output = "Nice! I've marked this task as done: \n";
        return output + "  " + task.toString() + saveTasks();
    }

    public static String unmarkTask(int taskNo) {
        Task task = tasks.get(taskNo);
        task.markAsNotDone();
        String output = "OK, I've marked this task as not done yet: \n";
        return output + "  " + task.toString() + saveTasks();
    }

    public static String addTodo(String description) {
        Todo todo = new Todo(description);
        tasks.add(todo);
        String output = "Got it. I've added this task: \n";
        return output + "  " + todo.toString() + saveTasks();
    }

    public static String addDeadline(String description, String by) {
        Deadline deadline = new Deadline(description, by);
        tasks.add(deadline);
        String output = "Got it. I've added this task: \n";
        return output + "  " + deadline.toString() + saveTasks();
    }

    public static String addEvent(String description, String from, String to) {
        Event event = new Event(description, from, to);
        tasks.add(event);
        String output = "Got it. I've added this task: \n";
        return output + "  " + event.toString() + saveTasks();
    }

    public static String taskInList() {
        return "Now you have " + tasks.size() + " tasks in the list.";
    }

    public static String handleTodo(String command) {
        try {
            String description = CommandType.TODO.argumentFrom(command);
            if (description.isBlank()) {
                throw new IllegalArgumentException("Please provide a description.");
            }
            return addTodo(description) + '\n' + taskInList();
        } catch (IllegalArgumentException e) {
            return "Invalid todo: " + e.getMessage();
        }
    }

    public static String handleDeadline(String command) {
        try {
            String input = CommandType.DEADLINE.argumentFrom(command);
            if (input.isBlank()) {
                throw new IllegalArgumentException("Please provide a description and deadline.");
            }

            int byIndex = input.indexOf("/by");
            if (byIndex == -1) {
                throw new IllegalArgumentException("Please include /by followed by a deadline.");
            }

            String description = input.substring(0, byIndex).trim();
            String by = input.substring(byIndex + "/by".length()).trim();
            if (description.isBlank()) {
                throw new IllegalArgumentException("Please provide a description.");
            }
            if (by.isBlank()) {
                throw new IllegalArgumentException("Please provide a deadline after /by.");
            }
            LocalDate deadlineDate = Deadline.parseDate(by);
            return addDeadline(description, deadlineDate.toString()) + '\n' + taskInList();
        } catch (IllegalArgumentException e) {
            return "Invalid deadline: " + e.getMessage();
        }
    }

    /** Finds and displays every deadline that occurs on the date supplied by the user. */
    public static String handleFind(String command) {
        try {
            String dateText = CommandType.FIND.argumentFrom(command);
            if (dateText.isBlank()) {
                throw new IllegalArgumentException("Please provide a date to search for.");
            }

            LocalDate date = Deadline.parseDate(dateText);
            StringBuilder matches = new StringBuilder("Deadlines on ")
                    .append(date).append(":\n");
            int matchCount = 0;
            for (int index = 0; index < tasks.size(); index++) {
                Task task = tasks.get(index);
                if (task instanceof Deadline && ((Deadline) task).isOn(date)) {
                    matches.append(index + 1).append(". ").append(task).append('\n');
                    matchCount++;
                }
            }
            if (matchCount == 0) {
                return "No deadlines found on " + date + ".\n";
            }
            return matches.toString();
        } catch (IllegalArgumentException exception) {
            return "Invalid find: " + exception.getMessage();
        }
    }

    public static String handleEvent(String command) {
        try {
            String input = CommandType.EVENT.argumentFrom(command);
            if (input.isBlank()) {
                throw new IllegalArgumentException("Please provide a description, start time, and end time.");
            }

            int fromIndex = input.indexOf("/from");
            int toIndex = input.indexOf("/to");
            if (fromIndex == -1) {
                throw new IllegalArgumentException("Please include /from followed by a start time.");
            }
            if (toIndex == -1) {
                throw new IllegalArgumentException("Please include /to followed by an end time.");
            }
            if (toIndex < fromIndex) {
                throw new IllegalArgumentException("Please put /from before /to.");
            }

            String description = input.substring(0, fromIndex).trim();
            String from = input.substring(fromIndex + "/from".length(), toIndex).trim();
            String to = input.substring(toIndex + "/to".length()).trim();
            if (description.isBlank()) {
                throw new IllegalArgumentException("Please provide a description.");
            }
            if (from.isBlank()) {
                throw new IllegalArgumentException("Please provide a start time after /from.");
            }
            if (to.isBlank()) {
                throw new IllegalArgumentException("Please provide an end time after /to.");
            }
            return addEvent(description, from, to) + '\n' + taskInList();
        } catch (IllegalArgumentException e) {
            return "Invalid event: " + e.getMessage();
        }
    }

    public static String deleteTask(int index) {
        String output = "Noted. I've removed this task: " + '\n' + tasks.get(index).toString();
        tasks.remove(index);
        return output + '\n' + taskInList() + saveTasks();
    }

    /** Saves the current list and turns an I/O failure into a helpful UI message. */
    private static String saveTasks() {
        try {
            storage.save(tasks);
            return "";
        } catch (IOException | IllegalArgumentException exception) {
            return "\nWarning: your task was changed, but Pebby could not save it: "
                    + exception.getMessage();
        }
    }

    /** Loads prior tasks without allowing a missing or damaged file to stop Pebby. */
    private static String loadTasks() {
        try {
            Storage.LoadResult result = storage.load();
            tasks = new ArrayList<>(result.tasks);
            if (result.skippedRecords > 0) {
                return "Warning: ignored " + result.skippedRecords
                        + " invalid saved task record(s).";
            }
            return "";
        } catch (IOException exception) {
            return "Warning: Pebby could not load saved tasks: " + exception.getMessage();
        }
    }

    /** Validates the 1-based task number used by mark, unmark, and delete commands. */
    private static int taskIndexFrom(String argument) {
        try {
            int taskNumber = Integer.parseInt(argument);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                throw new IllegalArgumentException("Please choose a task number from 1 to " + tasks.size() + ".");
            }
            return taskNumber - 1;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Please provide a whole task number.");
        }
    }

    public static void main(String[] args) {
        Ui ui = new Ui();
        String loadMessage = loadTasks();
        ui.showWelcome();
        if (!loadMessage.isEmpty()) {
            ui.showLine(loadMessage);
            ui.showSeparator();
        }

        // Getting user input
        while (ui.hasNextCommand()) {
            String command = ui.readCommand();
            CommandType commandType = CommandType.from(command);
            if (commandType == CommandType.BYE) {
                break;
            }

            ui.showSeparator();
            switch (commandType) {
                case LIST:
                    ui.show(listTask());
                    break;
                case MARK: {
                    try {
                        ui.showLine(markTask(taskIndexFrom(commandType.argumentFrom(command))));
                    } catch (IllegalArgumentException exception) {
                        ui.showLine("Invalid mark: " + exception.getMessage());
                    }
                    break;
                }
                case UNMARK: {
                    try {
                        ui.showLine(unmarkTask(taskIndexFrom(commandType.argumentFrom(command))));
                    } catch (IllegalArgumentException exception) {
                        ui.showLine("Invalid unmark: " + exception.getMessage());
                    }
                    break;
                }
                case TODO: {
                    ui.showLine(handleTodo(command));
                    break;
                }
                case DEADLINE: {
                    ui.showLine(handleDeadline(command));
                    break;
                }
                case EVENT: {
                    ui.showLine(handleEvent(command));
                    break;
                }
                case FIND: {
                    ui.show(handleFind(command));
                    break;
                }
                case DELETE: {
                    try {
                        ui.showLine(deleteTask(taskIndexFrom(commandType.argumentFrom(command))));
                    } catch (IllegalArgumentException exception) {
                        ui.showLine("Invalid delete: " + exception.getMessage());
                    }
                    break;
                }
                case BYE:
                default:
                    ui.showLine("Hmmm... What does this mean? My pebble brain cant understand.");
            }
            ui.showSeparator();
        }
        ui.showGoodbye();
    }
}
