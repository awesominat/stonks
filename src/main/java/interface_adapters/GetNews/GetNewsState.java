package interface_adapters.GetNews;

import java.util.List;
import java.util.Map;

public class GetNewsState {
    private String newsError = null;
    private List<Map<String, String>> newsList = null;

    public GetNewsState(List<Map<String, String>> news_list, String newsError) {
        this.newsList = news_list;
        this.newsError = newsError;
    }

    public String getNewsError() {return newsError;}

    public void setNewsError() {this.newsError = newsError;}

    public List<Map<String, String>> getNewsList() {return newsList;}

    public void setNewsList(List<Map<String, String>> news_list) {this.newsList = news_list;}

    // Due to the copy constructor, the default constructor must be explicit. So we overload it.
    public GetNewsState() {}
}
