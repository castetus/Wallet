package wallet.controller;

import wallet.cli.CommandResult;
import wallet.context.WalletContext;
import wallet.model.Category;
import wallet.model.CategoryType;
import wallet.model.Wallet;
import wallet.service.BudgetService;
import wallet.service.CategoryService;
import wallet.service.TransactionService;

import java.util.List;

public class TransactionController {

    private final TransactionService transactionService;
    private final BudgetService budgetService;
    private final CategoryService categoryService;
    private final WalletContext walletContext;

    public TransactionController(
            TransactionService transactionService,
            CategoryService categoryService,
            BudgetService budgetService,
            WalletContext walletContext
    ) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
        this.budgetService = budgetService;
        this.walletContext = walletContext;
    }

    public CommandResult addIncome(List<String> args) {
        return addTransaction(args, CategoryType.INCOME);
    }

    public CommandResult addExpense(List<String> args) {
        return addTransaction(args, CategoryType.EXPENSE);
    }

    private CommandResult addTransaction(List<String> args, CategoryType type) {
        String command = type == CategoryType.INCOME ? "income" : "expense";

        if (args.size() != 2) {
            return new CommandResult(
                    false,
                    "Expected: " + command + " <category> <amount>"
            );
        }

        try {
            Wallet wallet = walletContext.getCurrentWallet();

            String categoryName = args.get(0);
            int amount = Integer.parseInt(args.get(1));

            if (amount <= 0) {
                return new CommandResult(false, "Amount must be greater than zero");
            }

            Category category = categoryService.get(wallet, categoryName, type);

            if (category == null) {
                return new CommandResult(false, "Category not found");
            }

            if (type == CategoryType.INCOME) {
                transactionService.addIncome(wallet, category, amount);
            } else {
                transactionService.addExpense(wallet, category, amount);
            }

            return new CommandResult(true, "Transaction added successfully");

        } catch (NumberFormatException e) {
            return new CommandResult(false, "Amount must be an integer");

        } catch (IllegalArgumentException | IllegalStateException e) {
            return new CommandResult(false, e.getMessage());
        }
    }

    public CommandResult addTransfer(List<String> args) {

    }
}
