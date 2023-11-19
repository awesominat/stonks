package app;

public class finnhubapitesting {
    public static void main(String[] args) {
        System.out.println("hi");

        String APIKEY = "";

//        File f = new File("file.txt");
//        if(!f.exists()) {
//            // do something
//            System.out.println("please create a file in the src parent directory called file.txt with your Finnhub token");
//            return;
//        }
//
//        FileReader fileReader = new FileReader();
//        try {
//            InputStream inputStream = new FileInputStream(f);
//            APIKEY = fileReader.readFromInputStream(inputStream);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        String symbol = "AAPL";
//        String url = "https://finnhub.io/api/v1/quote?symbol=" + symbol + "&token=" + APIKEY;
//
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        Request request = new Request.Builder()
//                .url(url)
//                .get()
//                .addHeader("Content-Type", "application/json")
//                .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//            JSONObject responseBody = new JSONObject(response.body().string());
//
//            System.out.println(responseBody.get("c") + " is apple's current price! wow, grape.");
//        }
//        catch (IOException | JSONException e) {
//            throw new RuntimeException(e);
//        }
    }
}
