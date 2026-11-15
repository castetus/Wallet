package wallet;

import wallet.cli.CommandParser;
import wallet.cli.ConsoleApp;
import wallet.cli.Controllers;
import wallet.context.WalletContext;
import wallet.controller.*;
import wallet.service.*;
import wallet.storage.FileStorage;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        FileStorage fileStorage = new FileStorage();
        Map<String, String> users = fileStorage.loadUsers();

        CommandParser parser = new CommandParser();
        AuthService authService = new AuthService(users);
        CategoryService categoryService = new CategoryService();
        BudgetService budgetService = new BudgetService();
        TransactionService transactionService = new TransactionService(authService);
        FinanceService financeService = new FinanceService();

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
        FinanceController financeController = new FinanceController(
                financeService,
                budgetService,
                categoryService,
                walletContext
        );
        Controllers controllers = new Controllers(
                authController,
                categoryController,
                transactionController,
                budgetController,
                financeController
        );

        ConsoleApp app = new ConsoleApp(controllers, parser, authService);
        app.run();
    }
}