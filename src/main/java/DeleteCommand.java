/** Deletes the task identified by a user-supplied one-based task number. */
public class DeleteCommand extends Command {
    private final String argument;

    /** Creates a delete command using the text after the {@code delete} keyword. */
    public DeleteCommand(String argument) {
        this.argument = argument;
    }

    /** Validates the task number, deletes the task, saves the list, and shows the result. */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task deletedTask = tasks.delete(taskIndexFrom(argument, tasks));
            String output = "Noted. I've removed this task: \n" + deletedTask;
            ui.showLine(output + '\n' + taskCountMessage(tasks) + saveTasks(tasks, storage));
        } catch (IllegalArgumentException exception) {
            ui.showLine("Invalid delete: " + exception.getMessage());
        }
    }

    private int taskIndexFrom(String taskNumberText, TaskList tasks) {
        try {
            int taskNumber = Integer.parseInt(taskNumberText);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                throw new IllegalArgumentException(
                        "Please choose a task number from 1 to " + tasks.size() + ".");
            }
            return taskNumber - 1;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Please provide a whole task number.");
        }
    }

    private String taskCountMessage(TaskList tasks) {
        return "Now you have " + tasks.size() + " tasks in the list.";
    }
}
