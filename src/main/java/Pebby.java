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
