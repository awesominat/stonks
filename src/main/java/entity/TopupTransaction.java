package entity;

/**
 * Represents a topup or reset transaction, where the user portfolio is emptied and the
 * balance is reset to 10000.
 */
public class TopupTransaction extends Transaction{
    /**
     * Constructor for a topup transaction.
     *
     * @param amount        amount that is given back to the user (1)
     * @param pricePoint    pricepoint of the transaction (also 10000)
     */
    public TopupTransaction(Double amount, PricePoint pricePoint) {
        super(TransactionType.TOPUP, amount, pricePoint);
    }
}
