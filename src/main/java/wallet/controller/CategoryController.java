package wallet.controller;

import wallet.service.CategoryService;

public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController (CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
