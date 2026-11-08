package wallet.controller;

import wallet.cli.CommandResult;
import wallet.service.AuthService;
import java.util.List;

public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    private Credentials validateCredentials(List<String> args) {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Expected username and password");
        }

        String username = args.get(0);
        String password = args.get(1);

        if (username.isBlank() || password.isBlank()) {
            throw new IllegalArgumentException("Username and password are required");
        }

        return new Credentials(username, password);
    }

    public CommandResult register(List<String> args) {
        try {
            Credentials credentials = validateCredentials(args);

            boolean isRegistered = authService.register(
                    credentials.username(),
                    credentials.password()
            );

            if (!isRegistered) {
                return new CommandResult(false, "Username already exists");
            }

            return new CommandResult(true, "User registered successfully");

        } catch (IllegalArgumentException e) {
            return new CommandResult(false, e.getMessage());
        }
    }

    public CommandResult login(List<String> args) {
        try {
            Credentials credentials = validateCredentials(args);

            boolean isLoggedIn = authService.login(
                    credentials.username(),
                    credentials.password()
            );

            if (!isLoggedIn) {
                return new CommandResult(false, "Cannot login user");
            }

            return new CommandResult(true, "User logged in successfully");

        } catch (IllegalArgumentException e) {
            return new CommandResult(false, e.getMessage());
        }
    }

    public CommandResult logout() {
        authService.logout();
        return new CommandResult(true, "User is logged out");
    }
}