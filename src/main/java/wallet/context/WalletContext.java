package wallet.context;

import wallet.model.User;
import wallet.model.Wallet;
import wallet.service.AuthService;

public class WalletContext {
    private final AuthService authService;

    public WalletContext(AuthService authService) {
        this.authService = authService;
    }

    public Wallet getCurrentWallet() {
        User user = authService.getCurrentUser();

        if (user == null) {
            throw new IllegalStateException("User is not logged in");
        }

        return user.getWallet();
    }
}
