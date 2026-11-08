package wallet.model;

public class User {
    private final String login;
    private final String password;
    private final Wallet wallet = new Wallet();

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public Wallet getWallet() {
        return wallet;
    }
}
