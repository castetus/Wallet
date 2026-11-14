package wallet;

import wallet.cli.CommandParser;
import wallet.cli.ConsoleApp;
import wallet.controller.AuthController;
import wallet.controller.CategoryController;
import wallet.service.AuthService;
import wallet.service.CategoryService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CommandParser parser = new CommandParser();
        AuthService authService = new AuthService();
        CategoryService categoryService = new CategoryService();
        AuthController authController = new AuthController(authService);
        CategoryController categoryController = new CategoryController(categoryService, authService);

        ConsoleApp app = new ConsoleApp(authController, parser);
        app.run();
    }
}