package wallet.controller;

import wallet.cli.CommandResult;
import wallet.model.*;
import wallet.service.AuthService;
import wallet.service.BudgetService;
import wallet.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

public class BudgetController {
    private final AuthService authService;
    private final CategoryService categoryService;
    private final BudgetService budgetService;

    public BudgetController(
            AuthService authService,
            CategoryService categoryService,
            BudgetService budgetService
    ) {
        this.authService = authService;
        this.categoryService = categoryService;
        this.budgetService = budgetService;
    }

    public CommandResult set(List<String> args) {
        if (args.size() != 2) {
            return new CommandResult(false, "Expected: budget set <category> <amount>");
        }

        try {
            Wallet wallet = getCurrentWallet();

            String categoryName = args.get(0);
            int amount = Integer.parseInt(args.get(1));

            Category category = categoryService.get(
                    wallet,
                    categoryName,
                    CategoryType.EXPENSE
            );

            if (category == null) {
                return new CommandResult(false, "Expense category not found");
            }

            budgetService.set(wallet, category, amount);

            return new CommandResult(true, "Budget saved");

        } catch (NumberFormatException e) {
            return new CommandResult(false, "Amount must be an integer");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new CommandResult(false, e.getMessage());
        }
    }

    public CommandResult getList() {
        try {
            Wallet wallet = getCurrentWallet();
            List<Budget> budgets = budgetService.getList(wallet);

            if (budgets.isEmpty()) {
                return new CommandResult(true, "No budgets found");
            }

            String message = budgets.stream()
                    .map(budget ->
                            budget.getCategory().getName() + ": " + budget.getLimit()
                    )
                    .collect(Collectors.joining("\n"));

            return new CommandResult(true, message);

        } catch (IllegalStateException e) {
            return new CommandResult(false, e.getMessage());
        }
    }

    private Wallet getCurrentWallet() {
        User user = authService.getCurrentUser();

        if (user == null) {
            throw new IllegalStateException("User is not logged in");
        }

        return user.getWallet();
    }
}
