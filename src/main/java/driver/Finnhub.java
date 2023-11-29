package driver;

import entity.CompanyInformation;
import entity.CompanyNews;
import entity.PricePoint;
import entity.StockInformation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.APIAccessInterface;

import java.io.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class Finnhub implements APIAccessInterface {
    String APIKEY;  // Question: should this be private or static or final or some combination of these things?
    int NUM_ARTICLES = 5;
    String RESOLUTION = "D";

    public Finnhub() {
        File f = new File("file.txt");
        FileReader fileReader = new FileReader();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        // Set constant attribute `APIKEY` of Finnhub object to your personal API key
        APIKEY = fileReader.readFromInputStream(inputStream);
    }

    @Override
    public boolean isMarketOpen() {
        String url = "https://finnhub.io/api/v1/stock/market-status?exchange=US&token=" + APIKEY;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            JSONObject responseBody = new JSONObject(response.body().string());

            return (Boolean) responseBody.get("isOpen");
        }
        catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompanyInformation getCompanyProfile(String ticker) {
        // Get profile for company `ticker`.

        // Define url for API call
        String url = "https://finnhub.io/api/v1/stock/profile2?symbol=" + ticker + "&token=" + APIKEY;

        // Use OkHttp client to access API
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            // Make API call
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            JSONObject responseBody = new JSONObject(response.body().string());

            // Save API response values
            String country = (String) responseBody.get("country");
            String name = (String) responseBody.get("name");
            String ticker_response = (String) responseBody.get("ticker");
            String weburl = (String) responseBody.get("weburl");
            String ipo = (String) responseBody.get("ipo");

            // Create and return CompanyInformation object with information from API response
            return new CompanyInformation(country, name, ticker_response, weburl, ipo);
        }
        catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CompanyNews> getCompanyNews(String ticker, LocalDate from, LocalDate to) {
        // Get news related to company `ticker` from the last month.
        // Limit it to the first `NUM_ARTICLES` articles.

        // LocalDate documentation: https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html
        // Finnhub documentation: https://finnhub.io/docs/api/company-news
        // Date section of url: &from=2023-08-15&to=2023-08-20 (not unix)

        String url = "https://finnhub.io/api/v1/company-news?symbol=" + ticker + "&from=" + from + "&to=" + to +
                "&token=" + APIKEY;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            assert response.body() != null;

            // Read API data into a JSON array
            JSONArray responseBody = new JSONArray(response.body().string());
            // Initialize output array (for return value)
            List<CompanyNews> ret = new ArrayList<CompanyNews>();

            // Iterate through dictionary of recent news articles
            // for (int i = 0; i < responseBody.length(); i ++) {
            for (int i = 0; i < NUM_ARTICLES; i ++) {
                // Get current article from JSON array
                Object curr_article = responseBody.get(i);
                // Create JSON Object for current article
                JSONObject responseArticle = new JSONObject(curr_article.toString());

                // Convert API response into usable variables
                String category = (String) responseArticle.get("category");
                String headline = (String) responseArticle.get("headline");
                String url_out = (String) responseArticle.get("url");
                String summary = (String) responseArticle.get("summary");
                Integer timeStamp = (Integer) responseArticle.get("datetime");  // get timeStamp from API response

                // Convert timeStamp from Integer to LocalDate
                Instant instant = Instant.ofEpochSecond(timeStamp.longValue());
                ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
                LocalDate datetime = zdt.toLocalDate();

                ret.add(new CompanyNews(category, datetime, headline, url_out, summary));
            }
            return ret;
        }
        catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PricePoint getCurrentPrice(String ticker) {
        ticker = ticker.replaceAll("[^a-zA-Z0-9]", "");

        String url = "https://finnhub.io/api/v1/quote?symbol=" + ticker + "&token=" + APIKEY;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            JSONObject responseBody = new JSONObject(response.body().string());
            LocalDate now = LocalDate.now();
            Double price;

            if (responseBody.get("c") instanceof BigDecimal) {
                price = ((BigDecimal) responseBody.get("c")).doubleValue();
            } else {
                price = (Double) responseBody.get("c");
            }

            return new PricePoint(now, price);
        }
        catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public StockInformation getCurrentStockInformation(String ticker) {
        ticker = ticker.replaceAll("[^a-zA-Z0-9]", "");

        String url = "https://finnhub.io/api/v1/quote?symbol=" + ticker + "&token=" + APIKEY;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            JSONObject responseBody = new JSONObject(response.body().string());
            Double price;
            Double priceChange;
            Double percentChange;

            if (responseBody.get("c") instanceof BigDecimal) {
                price = ((BigDecimal) responseBody.get("c")).doubleValue();
            } else {
                price = (Double) responseBody.get("c");
            }

            if (responseBody.get("d") instanceof BigDecimal) {
                priceChange = ((BigDecimal) responseBody.get("d")).doubleValue();
            } else {
                priceChange = (Double) responseBody.get("d");
            }

            if (responseBody.get("dp") instanceof BigDecimal) {
                percentChange = ((BigDecimal) responseBody.get("dp")).doubleValue();
            } else {
                percentChange = (Double) responseBody.get("dp");
            }

            return new StockInformation(price, priceChange, percentChange);
        }
        catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
