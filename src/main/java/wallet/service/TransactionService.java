package wallet.service;

import wallet.model.*;

public class TransactionService {

    private final FinanceService financeService = new FinanceService();
    private final AuthService authService;

    public TransactionService(AuthService authService) {
        this.authService = authService;
    }

    public void addIncome(Wallet wallet, Category category, int amount) {
        wallet.addTransaction(new Income(amount, category));
    }

    public void addExpense(Wallet wallet, Category category, int amount) {
        wallet.addTransaction(new Expense(amount, category));
    }

    public void addTransfer(Wallet wallet, String username, int amount) {
        User recipient = authService.getUserByLogin(username);
        User sender = authService.getCurrentUser();

        if (recipient == null) {
            throw new IllegalArgumentException("Recipient not found");
        }

        if (recipient == authService.getCurrentUser()) {
            throw new IllegalArgumentException("Cannot transfer to yourself");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (financeService.getBalance(wallet) < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        Wallet recipientWallet = recipient.getWallet();

        wallet.addTransaction(new TransferOut(amount, recipient.getLogin()));
        recipientWallet.addTransaction(new TransferIn(amount, sender.getLogin()));
    }
}
