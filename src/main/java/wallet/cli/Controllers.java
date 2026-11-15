package wallet.cli;

import wallet.controller.*;

public record Controllers(
        AuthController auth,
        CategoryController category,
        TransactionController transaction,
        BudgetController budget,
        FinanceController finance
) {}