import java.util.Scanner;

public class Pebby {
    public static String[] tasks = new String[100];
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

    public static String addTask(String task) {
        tasks[taskCount] = task;
        taskCount++;
        return "added: " + task;
    }

    public static String listTask() {
        StringBuilder taskList = new StringBuilder();
        for (int i = 0; i < taskCount; i++) {
            int taskNo = i + 1;
            taskList.append(taskNo).append(". ").append(tasks[i]).append('\n');
        }
        return taskList.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        greetUser();

        // Getting user input
        String command = scanner.nextLine();
        while (!command.equals("bye")) {
            System.out.println("____________________________________________________________");
            if (command.equals("list")) {
                System.out.println(listTask());
            } else {
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
