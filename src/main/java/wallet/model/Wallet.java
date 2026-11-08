package wallet.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Wallet {
    private final List<Transaction> transactions = new ArrayList<>();
    private final List<Category> categories = new ArrayList<>();
    private final List<Budget> budgets = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public List<Category> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public void addBudget(Budget budget) {
        budgets.add(budget);
    }

    public List<Budget> getBudgets() {
        return Collections.unmodifiableList(budgets);
    }
}
