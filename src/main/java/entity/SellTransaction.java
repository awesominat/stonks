package entity;

public class SellTransaction extends Transaction{
    /**
     * A class that represents a single sell transaction
     *
     * @param amount        amount of stocks that were sold
     * @param pricePoint    contains information like the price at which they were sold
     *                      and a timestamp that shows when that price was fetched from
     *                      the API.
     */
    public SellTransaction(Double amount, PricePoint pricePoint) {
        super(TransactionType.SELL, amount, pricePoint);
    }
}
