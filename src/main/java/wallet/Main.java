package wallet;

import wallet.cli.CommandParser;
import wallet.cli.ConsoleApp;
import wallet.cli.Controllers;
import wallet.controller.AuthController;
import wallet.controller.BudgetController;
import wallet.controller.CategoryController;
import wallet.service.AuthService;
import wallet.service.BudgetService;
import wallet.service.CategoryService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CommandParser parser = new CommandParser();
        AuthService authService = new AuthService();
        CategoryService categoryService = new CategoryService();
        BudgetService budgetService = new BudgetService();

        AuthController authController = new AuthController(authService);
        BudgetController budgetController = new BudgetController(
                authService,
                categoryService,
                budgetService
        );
        CategoryController categoryController = new CategoryController(
                categoryService,
                authService
                );

        Controllers controllers = new Controllers(
                authController,
                categoryController,
                budgetController
        );

        ConsoleApp app = new ConsoleApp(controllers, parser);
        app.run();
    }
}