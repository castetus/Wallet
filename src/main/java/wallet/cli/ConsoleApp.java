package wallet.cli;

import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private final Controllers controllers;
    private final CommandParser parser;

    public ConsoleApp(Controllers controllers, CommandParser parser) {
        this.controllers = controllers;
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

                List<String> commandArgs = args.subList(1, args.size());

                CommandResult result = switch (command) {
                    case "register" -> controllers.auth().register(commandArgs);
                    case "login" -> controllers.auth().login(commandArgs);
                    case "logout" -> controllers.auth().logout();

                    case "category" -> {
                        if (commandArgs.isEmpty()) {
                            yield new CommandResult(false, "Expected: category add|list");
                        }

                        yield switch (commandArgs.get(0)) {
                            case "add" -> controllers.category().addCategory(commandArgs.subList(1, commandArgs.size()));
                            case "list" -> controllers.category().getCategoryList();
                            default -> new CommandResult(false, "Unknown category command");
                        };
                    }

                    case "budget" -> {
                        if (commandArgs.isEmpty()) {
                            yield new CommandResult(false, "Expected: budget set|list");
                        }

                        yield switch (commandArgs.get(0)) {
                            case "set" -> controllers.budget().set(
                                    commandArgs.subList(1, commandArgs.size())
                            );
                            case "list" -> controllers.budget().getList();
                            default -> new CommandResult(false, "Unknown budget command");
                        };
                    }

                    case "income" -> controllers.transaction().addIncome(commandArgs);
                    case "expense" -> controllers.transaction().addExpense(commandArgs);
                    case "transfer" -> controllers.transaction().addTransfer(commandArgs);

                    default -> new CommandResult(false, "Unknown command");
                };

                System.out.println(result.message());

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid command: " + e.getMessage());
            }
        }
    }
}