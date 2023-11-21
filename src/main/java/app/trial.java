package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class trial {
    public static void main(String[] args) {
        HashMap<String[], Double> ownedStocks = new HashMap<String[], Double>();
        String[] arr = {"APPL", "Apple"};
        ownedStocks.put(arr, 100.0);
        System.out.println(ownedStocks.toString());
    }
}
