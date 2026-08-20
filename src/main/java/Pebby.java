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
                    String description = s.substring(5);
                    System.out.println(addTodo(description));
                    System.out.println(taskInList());
                    break;
                }
                case String s when s.startsWith("deadline"): {
                    int byIndex = s.indexOf("/by");
                    String description = s.substring(9, byIndex);
                    String by = s.substring(byIndex + 4);
                    System.out.println(addDeadline(description, by));
                    System.out.println(taskInList());
                    break;
                }
                case String s when s.startsWith("event"): {
                    int fromIndex = s.indexOf("/from");
                    int toIndex = s.indexOf("/to");
                    String description = s.substring(6, fromIndex);
                    String from = s.substring(fromIndex + 6, toIndex);
                    String to = s.substring(toIndex + 4);
                    System.out.println(addEvent(description, from, to));
                    System.out.println(taskInList());
                    break;
                }
                default:
                    System.out.print(addTask(command));
            }
            System.out.println("____________________________________________________________");
            command = scanner.nextLine();
        }
        System.out.println("____________________________________________________________");
        System.out.println("Bye Bye!");
        System.out.println("____________________________________________________________");
    }
}
