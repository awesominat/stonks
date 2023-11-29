package entity;

public class SellTransaction extends Transaction{
    public SellTransaction(Double amount, PricePoint pricePoint) {
        super(TransactionType.SELL, amount, pricePoint);
    }
}
