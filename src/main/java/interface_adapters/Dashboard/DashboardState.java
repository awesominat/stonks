package interface_adapters.Dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardState {
    private HashMap<String, Double> userStats;
    private List<String> ownedTickers;
    private List<String> ownedFullNames;
    private List<Double> ownedAmounts;
    private List<Double> prices;
    private Boolean renderNewInfo;
    private String error = null;

    public DashboardState(
            HashMap<String, Double> userStats,
            List<String> ownedTickers,
            List<String> ownedFullNames,
            List<Double> ownedAmounts,
            List<Double> prices
    ) {
        this.userStats = userStats;
        this.ownedTickers = ownedTickers;
        this.ownedAmounts = ownedAmounts;
        this.ownedFullNames = ownedFullNames;
        this.prices = prices;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setRenderNewInfo(Boolean renderNewInfo) {
        this.renderNewInfo = renderNewInfo;
    }

    public Boolean getRenderNewInfo() {
        return renderNewInfo;
    }

    public List<Double> getOwnedAmounts() {
        return this.ownedAmounts;
    }

    public List<String> getOwnedAmountsStrings() {
        List<String> amountsOut = new ArrayList<>();
        if (this.ownedAmounts != null) {
            for (Double amount : this.ownedAmounts) {
                String amountOut = String.valueOf(amount);
                amountsOut.add(amountOut);
            }
        }
        return amountsOut;
    }

    public void setOwnedAmounts(List<Double> ownedAmounts) {
        this.ownedAmounts = ownedAmounts;
    }

    public List<String> getOwnedTickers() {
        return ownedTickers;
    }

    public void setOwnedTickers(List<String> ownedTickers) {
        this.ownedTickers = ownedTickers;
    }

    public List<String> getOwnedFullNames() {
        return ownedFullNames;
    }

    public void setOwnedFullNames(List<String> ownedFullNames) {
        this.ownedFullNames = ownedFullNames;
    }

    public HashMap<String, String> getUserStats() {
        HashMap<String, String> statsOut = new HashMap<>();

        // Handle case where userStats is initialized (without causing error when it is not).
        if (this.userStats != null) {
            statsOut.put("Balance", String.format("%.2f", userStats.get("balance")));
            statsOut.put("Stock Holdings Net Worth", String.format("%.2f", userStats.get("totalAssets")));
            Double daysSinceLastTopup = userStats.get("daysSinceLastTopup");
            statsOut.put("Days Since Last Top-Up",
                    (daysSinceLastTopup < 0) ? "Never Topped Up" : String.format("%.0f", daysSinceLastTopup));
            statsOut.put("Total Profit", String.format("%.2f", userStats.get("totalProfit")));
            statsOut.put("Aggregate Volume", String.format("%.2f", userStats.get("aggregateVolume")));
            statsOut.put("Total Assets", String.format("%.2f", userStats.get("balance") + userStats.get("totalAssets")));
        } else {
            throw new IllegalArgumentException();
        }
        return statsOut;
    }

    public void setUserStats(HashMap<String, Double> userStats) {
        this.userStats = userStats;
    }

    public Double getCurBalance() {
        // Note that we cannot create a setCurBalance method because Dashboard does not have that power.
        if (userStats != null) {
            return userStats.get("balance");
        }
        return null;
    }

    public String getCurBalanceString() {
        // Convert to String to avoid difficulties in DashboardView
        return String.valueOf(userStats.get("balance"));
    }

    public List<String> getPricesStrings() {
        List<String> pricesOut = new ArrayList<>();
        if (this.prices != null) {
            for (Double price : prices) {
                pricesOut.add(price.toString());
            }
        }
        return pricesOut;
    }

    public List<Double> getPrices() {
        return prices;
    }

    public void setPrices(List<Double> prices) {
        this.prices = prices;
    }

    public DashboardState() {

    }

}
