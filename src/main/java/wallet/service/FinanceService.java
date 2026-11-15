package wallet.service;

import wallet.model.Wallet;
import wallet.model.Transaction;
import wallet.model.Income;
import wallet.model.Expense;
import wallet.model.TransferIn;
import wallet.model.TransferOut;

public class FinanceService {

    public int getBalance(Wallet wallet) {
        int balance = 0;

        for (Transaction transaction : wallet.getTransactions()) {
            int amount = transaction.getAmount();

            if (transaction instanceof Income || transaction instanceof TransferIn) {
                balance += amount;
            } else if (transaction instanceof Expense || transaction instanceof TransferOut) {
                balance -= amount;
            }
        }

        return balance;
    }
}
