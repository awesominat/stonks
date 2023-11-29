package entity;

public abstract class Transaction {
    private TransactionType type;
    private Double amount;
    private PricePoint pricePoint;
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
