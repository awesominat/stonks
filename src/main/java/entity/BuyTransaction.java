package entity;

public class BuyTransaction extends Transaction {
    /**
     * Represents a buy transaction
     *
     * @param amount        amount of stocks the user tries to buy
     * @param pricePoint    current price and timestamp of the price being fetched
     */
    public BuyTransaction(Double amount, PricePoint pricePoint) {
        super(TransactionType.BUY, amount, pricePoint);
    }

    @Override
    public String toString() {
        return super.getType() + " " + super.getAmount() + " " + super.getPricePoint();
    }
}
