package wallet.model;

public abstract class Transaction {
    private final int amount;
    private final Category category;

    protected Transaction(int amount, Category category) {
        this.amount = amount;
        this.category = category;
    }

    public int getAmount() {
        return this.amount;
    }

    public Category getCategory() {
        return this.category;
    }
}
