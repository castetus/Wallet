package wallet.cli;

import wallet.controller.AuthController;
import wallet.controller.CategoryController;

public record Controllers(
        AuthController auth,
        CategoryController category
//        TransactionController transaction,
//        BudgetController budget
) {}