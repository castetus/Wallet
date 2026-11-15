package wallet.service;

import wallet.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinanceService {

    public int getBalance(Wallet wallet) {
        int balance = 0;

        for (Transaction transaction : wallet.getTransactions()) {
            int amount = transaction.getAmount();

            if (transaction instanceof Income
                    || transaction instanceof TransferIn) {
                balance += amount;
            } else if (transaction instanceof Expense
                    || transaction instanceof TransferOut) {
                balance -= amount;
            }
        }

        return balance;
    }

    public int getTotalIncome(Wallet wallet) {
        int total = 0;

        for (Transaction transaction : wallet.getTransactions()) {
            if (transaction instanceof Income) {
                total += transaction.getAmount();
            }
        }

        return total;
    }

    public int getTotalExpenses(Wallet wallet) {
        int total = 0;

        for (Transaction transaction : wallet.getTransactions()) {
            if (transaction instanceof Expense) {
                total += transaction.getAmount();
            }
        }

        return total;
    }

    public Map<Category, Integer> getTotalsByCategory(
            Wallet wallet,
            CategoryType type
    ) {
        Map<Category, Integer> result = new HashMap<>();

        for (Transaction transaction : wallet.getTransactions()) {
            boolean matches = switch (type) {
                case INCOME -> transaction instanceof Income;
                case EXPENSE -> transaction instanceof Expense;
            };

            if (matches) {
                result.merge(
                        transaction.getCategory(),
                        transaction.getAmount(),
                        Integer::sum
                );
            }
        }

        return result;
    }

    public Map<Category, Integer> getExpensesByCategory(Wallet wallet) {
        return getTotalsByCategory(wallet, CategoryType.EXPENSE);
    }

    public Map<Category, Integer> getIncomeByCategory(Wallet wallet) {
        return getTotalsByCategory(wallet, CategoryType.INCOME);
    }

    public int getTotalByCategories(
            Wallet wallet,
            List<Category> categories
    ) {
        int total = 0;

        for (Transaction transaction : wallet.getTransactions()) {
            if (categories.contains(transaction.getCategory())) {
                total += transaction.getAmount();
            }
        }

        return total;
    }
}
