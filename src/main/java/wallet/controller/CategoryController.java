package wallet.controller;

import wallet.cli.CommandResult;
import wallet.context.WalletContext;
import wallet.model.Category;
import wallet.model.CategoryType;
import wallet.model.Wallet;
import wallet.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryController {

    private final CategoryService categoryService;
    private final WalletContext walletContext;

    public CategoryController (CategoryService categoryService, WalletContext walletContext) {
        this.categoryService = categoryService;
        this.walletContext = walletContext;
    }

    public CommandResult addCategory (List<String> args) {

        Wallet wallet = this.walletContext.getCurrentWallet();

        if (args.size() != 2) {
            throw new IllegalArgumentException("Incorrect arguments");
        }

        String type = args.get(0);
        String name = args.get(1);

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
            Wallet wallet = this.walletContext.getCurrentWallet();
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
