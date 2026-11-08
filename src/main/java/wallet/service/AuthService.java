package wallet.service;

import wallet.model.User;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private final Map<String, User> users = new HashMap<>();
    private User currentUser;

    public boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }

        users.put(username, new User(username, password));
        return true;
    }

    public boolean login(String username, String password) {
        User user = this.users.get(username);
        if (user != null && user.checkPassword(password)) {
            this.currentUser = user;
            return true;
        }

        return false;
    }

    public void logout() {
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }
}
