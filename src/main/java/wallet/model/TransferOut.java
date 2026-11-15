package wallet.model;

public class TransferOut extends Transaction {
    private final String recipient;

    public TransferOut(int amount, String recipient) {
        super(amount);
        this.recipient = recipient;
    }

    public String getCounterparty() {
        return this.recipient;
    }
}
