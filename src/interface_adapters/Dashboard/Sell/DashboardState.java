package interface_adapters.Dashboard.Sell;

public class DashboardState {
    private String amountError = null;

    public DashboardState(String amountError) {
        this.amountError = amountError;
    }

    public String getAmountError() {
        return amountError;
    }

    public void setAmountError(String amountError) {
        this.amountError = amountError;
    }

    // Because of the previous copy constructor, the default constructor must be explicit. Hence overloading.
    public DashboardState() {

    }

}
