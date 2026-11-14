package wallet.controller;

import wallet.cli.CommandResult;
import wallet.model.Category;
import wallet.model.CategoryType;
import wallet.model.User;
import wallet.model.Wallet;
import wallet.service.AuthService;
import wallet.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryController {

    private final CategoryService categoryService;
    private final AuthService authService;

    public CategoryController (CategoryService categoryService, AuthService authService) {
        this.categoryService = categoryService;
        this.authService = authService;
    }

    private Wallet getCurrentWallet() {
        User user = authService.getCurrentUser();

        if (user == null) {
            throw new IllegalStateException("User is not logged in");
        }

        return user.getWallet();
    }

    public CommandResult addCategory (List<String> args) {

        Wallet wallet = this.getCurrentWallet();

        if (args.size() != 3) {
            throw new IllegalArgumentException("Incorrect arguments");
        }

        String type = args.get(1);
        String name = args.get(2);

        try {
            CategoryType categoryType = CategoryType.valueOf(type.toUpperCase());
            this.categoryService.add(wallet, name, categoryType);
            return new CommandResult(true, "Category " + name + " successfully added");
        } catch (IllegalArgumentException e) {
            return new CommandResult(false, e.getMessage());
        }
    }

    public CommandResult getCategoryList () {
        try {
            Wallet wallet = getCurrentWallet();
            List<Category> categories = categoryService.getList(wallet);

            String message = categories.stream()
                    .map(category -> category.getType() + ": " + category.getName())
                    .collect(Collectors.joining("\n"));

            return new CommandResult(true, message);

        } catch (IllegalStateException e) {
            return new CommandResult(false, e.getMessage());
        }
    }
}
