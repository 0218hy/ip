import java.util.Scanner;

public class Pebby {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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

        // Getting user input
        String command = scanner.nextLine();
        while (!command.equals("bye")) {
            System.out.println("____________________________________________________________");
            System.out.println(command);
            System.out.println("____________________________________________________________");
            command = scanner.nextLine();
        }
        System.out.println("____________________________________________________________");
        System.out.println("Bye Bye!");
        System.out.println("____________________________________________________________");
    }
}
