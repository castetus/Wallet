package wallet.services;

//import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import wallet.model.Category;
import wallet.model.CategoryType;
import wallet.model.Wallet;
import wallet.service.BudgetService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BudgetServiceTest {

    @Test
    void shouldUpdateExistingBudget() {
        // Arrange
        Wallet wallet = new Wallet();
        Category category = new Category("Food", CategoryType.EXPENSE);
        wallet.addCategory(category);

        BudgetService budgetService = new BudgetService();

        // Act
        budgetService.set(wallet, category, 1000);
        budgetService.set(wallet, category, 2000);

        // Assert
        assertEquals(1, wallet.getBudgets().size());
        assertEquals(2000, wallet.getBudgets().get(0).getLimit());
    }
}
