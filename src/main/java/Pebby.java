import java.util.Scanner;

public class Pebby {
    public static Task[] tasks = new Task[100];
    public static int taskCount = 0;

    public static void greetUser() {
        String banner = " ____       _     _          \n"
                + "|  _ \\  ___| |__ | |__  _   _\n"
                + "| |_) |/ _ \\ '_ \\| '_ \\| | | |\n"
                + "|  __/|  __/ |_) | |_) | |_| |\n"
                + "|_|    \\___|_.__/|_.__/ \\__, |\n"
                + "                         |___/\n";
        System.out.println("____________________________________________________________");
        System.out.print(banner);
        System.out.println("Hello! I'm Pebby.");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    public static String addTask(String description) {
        Task newTask = new Task(description);
        tasks[taskCount] = newTask;
        taskCount++;
        return "added: " + newTask.description + '\n';
    }

    public static String listTask() {
        StringBuilder taskList = new StringBuilder();
        taskList.append("Here are the tasks in your list:").append('\n');
        for (int i = 0; i < taskCount; i++) {
            int taskNo = i + 1;
            taskList.append(taskNo).append(". ").append(tasks[i].toString()).append('\n');
        }
        return taskList.toString();
    }

    public static String markTask(int taskNo) {
        Task task = tasks[taskNo];
        task.markAsDone();
        String output = "Nice! I've marked this task as done: \n";
        return output + "  " + task.toString();
    }

    public static String unmarkTask(int taskNo) {
        Task task = tasks[taskNo];
        task.markAsNotDone();
        String output = "OK, I've marked this task as not done yet: \n";
        return output + "  " + task.toString();
    }

    public static String addTodo(String description) {
        Todo todo = new Todo(description);
        tasks[taskCount] = todo;
        taskCount++;
        String output = "Got it. I've added this task: \n";
        return output + "  " + todo.toString();
    }

    public static String addDeadline(String description, String by) {
        Deadline deadline = new Deadline(description, by);
        tasks[taskCount] = deadline;
        taskCount++;
        String output = "Got it. I've added this task: \n";
        return output + "  " + deadline.toString();
    }

    public static String addEvent(String description, String from, String to) {
        Event event = new Event(description, from, to);
        tasks[taskCount] = event;
        taskCount++;
        String output = "Got it. I've added this task: \n";
        return output + "  " + event.toString();
    }

    public static String taskInList() {
        return "Now you have " + taskCount + " tasks in the list.";
    }

    public static String handleTodo(String command) {
        try {
            String description = command.substring("todo".length()).trim();
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
            String input = command.substring("deadline".length()).trim();
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
            return addDeadline(description, by) + '\n' + taskInList();
        } catch (IllegalArgumentException e) {
            return "Invalid deadline: " + e.getMessage();
        }
    }

    public static String handleEvent(String command) {
        try {
            String input = command.substring("event".length()).trim();
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        greetUser();

        // Getting user input
        String command = scanner.nextLine();
        while (!command.equals("bye")) {
            System.out.println("____________________________________________________________");
            switch(command) {
                case "list":
                    System.out.print(listTask());
                    break;
                case String s when s.startsWith("mark"): {
                    int index = Integer.parseInt(s.substring(5)) - 1;
                    System.out.println(markTask(index));
                    break;
                }
                case String s when s.startsWith("unmark"): {
                    int index = Integer.parseInt(s.substring(7)) - 1;
                    System.out.println(unmarkTask(index));
                    break;
                }
                case String s when s.startsWith("todo"): {
                    System.out.println(handleTodo(s));
                    break;
                }
                case String s when s.startsWith("deadline"): {
                    System.out.println(handleDeadline(s));
                    break;
                }
                case String s when s.startsWith("event"): {
                    System.out.println(handleEvent(s));
                    break;
                }
                default:
                    System.out.println("Hmmm... What does this mean? My pebble brain cant understand.");
            }
            System.out.println("____________________________________________________________");
            command = scanner.nextLine();
        }
        System.out.println("____________________________________________________________");
        System.out.println("Bye Bye!");
        System.out.println("____________________________________________________________");
    }
}
