package wallet.cli;

import wallet.controller.AuthController;
import wallet.controller.BudgetController;
import wallet.controller.CategoryController;
import wallet.controller.TransactionController;

public record Controllers(
        AuthController auth,
        CategoryController category,
        TransactionController transaction,
        BudgetController budget
) {}