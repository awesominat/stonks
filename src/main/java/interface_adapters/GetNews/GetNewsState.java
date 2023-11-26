package interface_adapters.GetNews;

import java.util.List;
import java.util.Map;

public class GetNewsState {
    private String tickerError = null;
    private String ticker = "";
    List<Map<String, String>> newsItems;
    private Boolean renderNewInfo;

    public String getTickerError() {
        return tickerError;
    }

    public void setTickerError(String tickerError) {
        this.tickerError = tickerError;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public List<Map<String, String>> getNewsItems() {
        return this.newsItems;
    }

    public void setNewsItems(List<Map<String, String>> newsItems) {
        this.newsItems = newsItems;
    }

    public Map<String, String> getNewsItem() {
        return this.newsItems.get(0);
    }

    public Boolean getRenderNewInfo() {
        return renderNewInfo;
    }

    public void setRenderNewInfo(Boolean renderNewInfo) {
        this.renderNewInfo = renderNewInfo;
    }

    // Due to the ViewModel's default constructor call, we must explicitly define the default constructor.
    //  So, we overload the copy constructor.
    public GetNewsState() {

    }

}
