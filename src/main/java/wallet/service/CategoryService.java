package wallet.service;

import wallet.model.Category;
import wallet.model.CategoryType;
import wallet.model.Wallet;

import java.util.List;

public class CategoryService {

    public void add(Wallet wallet, String name, CategoryType type) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Category name is required");
        }

        if (get(wallet, name) != null) {
            throw new IllegalArgumentException("Category already exists");
        }

        wallet.addCategory(new Category(name, type));
    }

    public Category get(Wallet wallet, String name) {
        return wallet.getCategories().stream()
                .filter(category -> category.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<Category> getList(Wallet wallet) {
        return wallet.getCategories();
    }
}