package interface_adapter.GetNews;

import java.util.List;
import java.util.Map;

/**
 * Represents the state associated with the GetNews use case.
 * Encapsulates information such as the ticker, news items, error messages, and
 * a flag indicating whether new information is available for rendering in the view.
 */
public class GetNewsState {

    /** The error message related to the ticker (default null). */
    private String tickerError = null;

    /** The ticker of the company for which news is being retrieved. */
    private String ticker = "";

    /** The list of news items, each represented as a map of key-value pairs. */
    List<Map<String, String>> newsItems;

    /** A flag indicating whether new information is available for rendering in the view. */
    private Boolean renderNewInfo;

    /**
     * Gets the ticker error message.
     *
     * @return  The ticker error message or null if no error.
     */
    public String getTickerError() {
        return tickerError;
    }

    /**
     * Sets the ticker error message.
     *
     * @param tickerError  The ticker error message.
     */
    public void setTickerError(String tickerError) {
        this.tickerError = tickerError;
    }

    /**
     * Gets the ticker searched by the user.
     *
     * @return  The ticker.
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * Sets the ticker for the view state.
     *
     * @param ticker  The ticker to be set.
     */
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    /**
     * Gets the list of news item maps, with each map representing a news article.
     *
     * @return  The list of news items.
     */
    public List<Map<String, String>> getNewsItems() {
        return this.newsItems;
    }

    /**
     * Sets the list of news items for the view state.
     *
     * @param newsItems  The list of news items to be set.
     */
    public void setNewsItems(List<Map<String, String>> newsItems) {
        this.newsItems = newsItems;
    }

    /**
     * Gets the value of the flag indicating whether new information is available for rendering.
     *
     * @return  True if new information needs to be rendered, false otherwise.
     */
    public Boolean getRenderNewInfo() {
        return renderNewInfo;
    }

    /**
     * Sets the flag indicating whether new information is available for rendering.
     *
     * @param renderNewInfo  The boolean value with which to set the flag
     *                       indicating whether new information should be rendered.
     */
    public void setRenderNewInfo(Boolean renderNewInfo) {
        this.renderNewInfo = renderNewInfo;
    }

    /**
     * Set a default constructor.
     * This is necessary to satisfy the GetNewsViewModel's default constructor call.
     */
    public GetNewsState() {

    }

}
