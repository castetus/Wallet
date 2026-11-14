package wallet.model;

public class Budget {
    private final Category category;
    private int limit;

    public Budget(Category category, int limit) {
        this.category = category;
        this.limit = limit;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setLimit(int amount) {
        this.limit = amount;
    }

    public int getLimit() {
        return this.limit;
    }
}
