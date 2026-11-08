package wallet.cli;

import wallet.controller.AuthController;

import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private final AuthController authController;
    private final CommandParser parser;

    public ConsoleApp(AuthController authController, CommandParser parser) {
        this.authController = authController;
        this.parser = parser;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            try {
                List<String> args = parser.parse(input);

                if (args.isEmpty()) {
                    continue;
                }

                String command = args.getFirst();

                if (command.equals("exit")) {
                    break;
                }

                CommandResult result;

                switch (command) {
                    case "register" -> result = authController.register(args);
                    default -> result = new CommandResult(false, "Unknown command");
                }

                System.out.println(result.message());

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid command: " + e.getMessage());
            }
        }
    }
}