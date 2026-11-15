package wallet;

import wallet.cli.CommandParser;
import wallet.cli.ConsoleApp;
import wallet.cli.Controllers;
import wallet.context.WalletContext;
import wallet.controller.AuthController;
import wallet.controller.BudgetController;
import wallet.controller.CategoryController;
import wallet.controller.TransactionController;
import wallet.service.AuthService;
import wallet.service.BudgetService;
import wallet.service.CategoryService;
import wallet.service.TransactionService;

public class Main {
    public static void main(String[] args) {
        CommandParser parser = new CommandParser();
        AuthService authService = new AuthService();
        CategoryService categoryService = new CategoryService();
        BudgetService budgetService = new BudgetService();
        TransactionService transactionService = new TransactionService(authService);

        WalletContext walletContext = new WalletContext(authService);

        AuthController authController = new AuthController(authService);
        BudgetController budgetController = new BudgetController(
                walletContext,
                categoryService,
                budgetService
        );
        CategoryController categoryController = new CategoryController(
                categoryService,
                walletContext
                );
        TransactionController transactionController = new TransactionController(
                transactionService,
                categoryService,
                budgetService,
                walletContext
        );
        Controllers controllers = new Controllers(
                authController,
                categoryController,
                transactionController,
                budgetController
        );

        ConsoleApp app = new ConsoleApp(controllers, parser);
        app.run();
    }
}