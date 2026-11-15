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
        if (args.size() != 2) {
            return new CommandResult(false, "Expected: income <category> <amount>");
        }

        try {
            Wallet wallet = this.walletContext.getCurrentWallet();

            String categoryName = args.get(0);
            int amount = Integer.parseInt(args.get(1));

            if (amount <= 0) {
                return new CommandResult(false, "Amount must be greater than zero");
            }

            Category category = categoryService.get(
                    wallet,
                    categoryName,
                    CategoryType.INCOME
            );

            if (category == null) {
                return new CommandResult(false, "Income category not found");
            }

            transactionService.addIncome(wallet, category, amount);

            return new CommandResult(true, "Income added successfully");

        } catch (NumberFormatException e) {
            return new CommandResult(false, "Amount must be an integer");

        } catch (IllegalStateException | IllegalArgumentException e) {
            return new CommandResult(false, e.getMessage());
        }
    }

    public CommandResult addExpense(List<String> args) {

    }

    public CommandResult addTransfer(List<String> args) {

    }
}
