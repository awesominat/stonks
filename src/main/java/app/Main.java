package app;

public class Main {
    public static void main(String[] args) {
//        LocalDate now = LocalDate.now();
//        LocalDate then = now.minusMonths(1);
//
//        System.out.println(then.datesUntil(now));
//        System.out.println(DAYS.between(then, now));
//        Finnhub a = new Finnhub();

        // the api offers very cool ability to choose resolution window of 1 minute
        // so we have minutely access to live stock data
        // it also offers the following resolutions
        // 1, 5, 15, 30, 60, D, W, M
        // TODO generalize String resolutionType to contain only the possible outputs shown above

//        System.out.println(a.getLastMonthPrices("AAPL", "D"));

        // Test Finnhub.getCompanyNews()
        // Define end of news period to be right now
//        LocalDate to = LocalDate.now();
//        // Define start of news period to be a month ago
//        LocalDate from = to.minusMonths(1);
//        // Print output
//        List<CompanyNews> news_list = a.getCompanyNews("AAPL", from, to);
//        // System.out.println(news_list);  // a whole bunch of CompanyNews objects
//        CompanyNews first_news_item = news_list.get(0);
//        // Inspect news item
//        System.out.println(first_news_item.toString());
//
//        // Test Finnhub.getCompanyProfile()
//        CompanyInformation sample_profile = a.getCompanyProfile("AAPL");
//        System.out.println("\n" + sample_profile.toString());


        // TASK check if this is your desired output
        // of course it's date-specific, but the general idea
        // [(2023-09-17: price is 176.48), (2023-09-18: price is 177.52), (2023-09-19: price is 179.26) ... ]

    }
}
