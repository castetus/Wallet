package wallet.service;

import wallet.model.Budget;
import wallet.model.Category;
import wallet.model.Wallet;

import java.util.List;

public class BudgetService {
    public void set(Wallet wallet, Category category, int amount) {
        Budget existing = wallet.getBudgets().stream()
                .filter(budget -> budget.getCategory() == category)
                .findFirst()
                .orElse(null);

        if (existing != null) {
            existing.setLimit(amount);
        } else {
            wallet.addBudget(new Budget(category, amount));
        }
    }

    public List<Budget> getList(Wallet wallet) {
        return wallet.getBudgets();
    }
}