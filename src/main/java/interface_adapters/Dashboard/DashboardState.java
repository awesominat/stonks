package interface_adapters.Dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardState {
    private HashMap<String, Double> userStats;
    // change owned stocks data type
    private List<String> ownedTickers;
    private List<String> ownedFullNames;
    private List<Double> ownedAmounts;
    private Boolean renderNewInfo;

    public DashboardState(HashMap<String, Double> userStats, List<String> ownedTickers,
                          List<String> ownedFullNames, List<Double> ownedAmounts) {
        this.userStats = userStats;
        this.ownedTickers = ownedTickers;
        this.ownedAmounts = ownedAmounts;
        this.ownedFullNames = ownedFullNames;
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
        if (this.userStats != null) {
            for (Map.Entry<String, Double> entry : this.userStats.entrySet()) {
                String key = entry.getKey();
                String valueOut = String.valueOf(entry.getValue());
                statsOut.put(key, valueOut);
            }
        }
        return statsOut;
    }

    public void setUserStats(HashMap<String, Double> userStats) {
        this.userStats = userStats;
    }

    public Double getCurBalance() {
        // Note that we cannot create a setCurBalance method because Dashboard does not have that power.
        return userStats.get("balance");
    }

    public String getCurBalanceString() {
        // Convert to String to avoid difficulties in DashboardView
        return String.valueOf(userStats.get("balance"));
    }

    // Because of the previous copy constructor, the default constructor must be explicit. Hence overloading.
    public DashboardState() {

    }

}
