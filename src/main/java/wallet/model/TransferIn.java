package wallet.model;

public class TransferIn extends Transaction {
    private final String sender;

    public TransferIn(int amount, String sender) {
        super(amount);
        this.sender = sender;
    }

    public String getCounterparty() {
        return this.sender;
    }
}
