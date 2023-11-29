package entity;

public class TopupTransaction extends Transaction{
    public TopupTransaction(Double amount, PricePoint pricePoint) {
        super(TransactionType.TOPUP, amount, pricePoint);
    }
}
