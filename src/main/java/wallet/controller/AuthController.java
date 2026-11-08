package wallet.controller;

import wallet.cli.CommandResult;
import wallet.service.AuthService;
import java.util.List;

public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public CommandResult register(List<String> args) {
        if (args.size() != 3) {
            return new CommandResult(
                    false,
                    "Usage: register <username> <password>"
            );
        }

        String username = args.get(1);
        String password = args.get(2);

        if (username.isBlank() || password.isBlank()) {
            return new CommandResult(false, "Username and password are required");
        }

        boolean registered = authService.register(username, password);

        if (!registered) {
            return new CommandResult(false, "Username already exists");
        }

        return new CommandResult(true, "User registered successfully");
    }
}