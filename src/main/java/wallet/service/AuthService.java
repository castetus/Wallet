package wallet.service;

import wallet.model.User;
import wallet.storage.FileStorage;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private final Map<String, User> users = new HashMap<>();
    private User currentUser;
    private final FileStorage fileStorage = new FileStorage();

    public AuthService(Map<String, String> savedUsers) {
        for (var entry : savedUsers.entrySet()) {
            User user = new User(entry.getKey(), entry.getValue());
            users.put(entry.getKey(), user);
        }
    }

    public boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }

        users.put(username, new User(username, password));
        this.fileStorage.saveUsers(this.users);
        return true;
    }

    public boolean login(String username, String password) {
        User user = this.users.get(username);
        if (user != null && user.checkPassword(password)) {
            this.currentUser = user;
            this.fileStorage.loadWallet(username);
            return true;
        }

        return false;
    }

    public void logout() {
        fileStorage.saveWallet(
                currentUser.getLogin(),
                currentUser.getWallet()
        );
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public User getUserByLogin(String username) {
        return users.get(username);
    }
}
