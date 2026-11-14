package wallet.model;

public class Category {
    private final String name;
    private final CategoryType type;

    public Category(String name, CategoryType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public CategoryType getType() {
        return type;
    }
}

