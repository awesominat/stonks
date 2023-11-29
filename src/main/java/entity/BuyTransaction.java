package entity;

public class BuyTransaction extends Transaction {
    public BuyTransaction(Double amount, PricePoint pricePoint) {
        super(TransactionType.BUY, amount, pricePoint);
    }

    @Override
    public String toString() {
        return super.getType() + " " + super.getAmount() + " " + super.getPricePoint();
    }
}
