
package wallet.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import wallet.model.Wallet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FileStorage {

    private final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private final Path directory = Path.of("data");
    private final Path usersFile = directory.resolve("users.json");
    private final Path walletsDirectory = directory.resolve("wallets");

    public void saveUsers(Map<String, String> users) {
        try {
            Files.createDirectories(directory);
            mapper.writeValue(usersFile.toFile(), users);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to save users", e
            );
        }
    }

    public Map<String, String> loadUsers() {
        if (Files.notExists(usersFile)) {
            return new HashMap<>();
        }

        try {
            return mapper.readValue(
                    usersFile.toFile(),
                    new TypeReference<Map<String, String>>() {}
            );
        } catch (IOException e) {
            System.err.println("Failed to load users: " + e.getMessage());
            return new HashMap<>();
        }
    }

    private Path getWalletPath(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Invalid username");
        }

        return walletsDirectory.resolve(username + ".json");
    }

    public void saveWallet(String username, Wallet wallet) {
        try {
            Files.createDirectories(walletsDirectory);
            mapper.writeValue(getWalletPath(username).toFile(), wallet);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to save wallet", e);
        }
    }

    public Wallet loadWallet(String username) {
        Path file = getWalletPath(username);

        if (Files.notExists(file)) {
            return new Wallet();
        }

        try {
            return mapper.readValue(file.toFile(), Wallet.class);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load wallet", e);
        }
    }
}
