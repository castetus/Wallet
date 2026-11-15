package wallet.service;

import wallet.model.Category;
import wallet.model.Income;
import wallet.model.Wallet;

public class TransactionService {

    public void addIncome(Wallet wallet, Category category, int amount) {
        wallet.addTransaction(new Income(amount, category));
    }
}
