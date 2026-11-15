package wallet.controller;

import wallet.cli.CommandResult;
import wallet.context.WalletContext;
import wallet.model.Budget;
import wallet.model.Category;
import wallet.model.CategoryType;
import wallet.model.Wallet;
import wallet.service.BudgetService;
import wallet.service.CategoryService;
import wallet.service.FinanceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FinanceController {
    private final FinanceService financeService;
    private final BudgetService budgetService;
    private final CategoryService categoryService;
    private final WalletContext walletContext;

    public FinanceController(
            FinanceService financeService,
            BudgetService budgetService,
            CategoryService categoryService,
            WalletContext walletContext
    ) {
        this.financeService = financeService;
        this.budgetService = budgetService;
        this.categoryService = categoryService;
        this.walletContext = walletContext;
    }

    public CommandResult report() {
        try {
            Wallet wallet = walletContext.getCurrentWallet();

            int balance = financeService.getBalance(wallet);
            int income = financeService.getTotalIncome(wallet);
            int expenses = financeService.getTotalExpenses(wallet);

            Map<Category, Integer> expensesByCategory =
                    financeService.getExpensesByCategory(wallet);

            StringBuilder result = new StringBuilder();

            result.append("Balance: ").append(balance).append("\n");
            result.append("Income: ").append(income).append("\n");
            result.append("Expenses: ").append(expenses);

            if (expenses > income) {
                result.append("\nWarning: expenses exceed income");
            }

            result.append("\nBudgets:");

            for (Budget budget : budgetService.getList(wallet)) {
                int spent = expensesByCategory.getOrDefault(
                        budget.getCategory(), 0
                );

                int remaining = budget.getLimit() - spent;

                result.append("\n")
                        .append(budget.getCategory().getName())
                        .append(": ")
                        .append(spent)
                        .append(" / ")
                        .append(budget.getLimit())
                        .append(", remaining: ")
                        .append(remaining);

                if (remaining < 0) {
                    result.append(" (OVER BUDGET)");
                }
            }

            return new CommandResult(true, result.toString());

        } catch (IllegalStateException e) {
            return new CommandResult(false, e.getMessage());
        }
    }

    public CommandResult reportCategories() {
        try {
            Wallet wallet = walletContext.getCurrentWallet();

            Map<Category, Integer> incomes =
                    financeService.getIncomeByCategory(wallet);

            Map<Category, Integer> expenses =
                    financeService.getExpensesByCategory(wallet);

            StringBuilder result = new StringBuilder("Income by category:");

            for (Map.Entry<Category, Integer> entry : incomes.entrySet()) {
                result.append("\n")
                        .append(entry.getKey().getName())
                        .append(": ")
                        .append(entry.getValue());
            }

            result.append("\nExpenses by category:");

            for (Map.Entry<Category, Integer> entry : expenses.entrySet()) {
                result.append("\n")
                        .append(entry.getKey().getName())
                        .append(": ")
                        .append(entry.getValue());
            }

            return new CommandResult(true, result.toString());

        } catch (IllegalStateException e) {
            return new CommandResult(false, e.getMessage());
        }
    }

    public CommandResult reportSelected(
            List<String> args,
            CategoryType type
    ) {
        if (args.isEmpty()) {
            return new CommandResult(false, "Specify at least one category");
        }

        try {
            Wallet wallet = walletContext.getCurrentWallet();
            List<Category> categories = new ArrayList<>();

            for (String name : args) {
                Category category = categoryService.get(wallet, name, type);

                if (category == null) {
                    return new CommandResult(
                            false,
                            "Category not found: " + name
                    );
                }

                if (!categories.contains(category)) {
                    categories.add(category);
                }
            }

            int total = financeService.getTotalByCategories(
                    wallet, categories
            );

            return new CommandResult(
                    true,
                    "Total " + type.name().toLowerCase() + ": " + total
            );

        } catch (IllegalStateException e) {
            return new CommandResult(false, e.getMessage());
        }
    }
}