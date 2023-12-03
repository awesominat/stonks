package entity;

/**
 * Abstract class for a transaction. Concretely implemented by BuyTransaction, SellTransaction and
 * TopupTransaction.
 */
public abstract class Transaction {
    private TransactionType type;
    private Double amount;
    private PricePoint pricePoint;

    /**
     * Constructor for the Transaction type
     *
     * @param type          type of the transaction, which is from the enum TransactionType
     * @param amount        amount of stocks involved (either in buy or sell)
     * @param pricePoint    current price and timestamp of the stock
     */
    public Transaction(TransactionType type, Double amount, PricePoint pricePoint) {
        this.type = type;
        this.amount = amount;
        this.pricePoint = pricePoint;
    }

    public TransactionType getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public PricePoint getPricePoint() {
        return pricePoint;
    }
}
