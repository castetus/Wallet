package wallet.model;

public class User {
    private final String login;
    private final String password;
    private final Wallet wallet;

    public User(String login, String password) {
        this(login, password, new Wallet());
    }

    public User(String login, String password, Wallet wallet) {
        this.login = login;
        this.password = password;
        this.wallet = wallet;
    }

    public String getLogin() {
        return login;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
