import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args0) throws IOException {
        HashMap<Integer, String> currencyCodes = new HashMap<Integer,String>();


        currencyCodes.put(1, "USD");
        currencyCodes.put(2, "GBP");
        currencyCodes.put(3, "TRY");
        currencyCodes.put(4, "EUR");
        currencyCodes.put(5, "INR");

        String fromCode, toCode;
        double amount;

        Scanner sc =  new Scanner(System.in);

        System.out.println("Welcome to the currency converter!");

        System.out.println("Currency converting FROM?");
        System.out.println("1:USD (US Dollar)\t 2:GBP (British Sterling)\t 3:TRY (Lira)\t 4:EUR (Euro)\t 5:INR (Indian Rupee)");
        fromCode = currencyCodes.get(sc.nextInt());

        System.out.println("Currency converting TO?");
        System.out.println("1:USD (US Dollar)\t 2:GBP (British Sterling)\t 3:TRY (Lira)\t 4:EUR (Euro)\t 5:INR (Indian Rupee)");
        toCode = currencyCodes.get(sc.nextInt());

        System.out.println("Amount you wish to convert?");
        amount = sc.nextFloat();

        sendHttpGETRequest(fromCode, toCode, amount);

        System.out.println("Thank you for using the currency converter");

    }
    private static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException {
        String GET_URL = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_soBpc2LUCeurcy5UEYJcx3qcdS7IOLc2TURvqOGZ&base=" + fromCode + "&symbols=" + toCode;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println("Response from API: " + response.toString()); // Print response for debugging

            JSONObject obj = new JSONObject(response.toString());
            JSONObject data = obj.getJSONObject("data");
            double exchangeRate = data.getDouble(toCode);

            System.out.println(amount + " " + fromCode + " = " + (amount * exchangeRate) + " " + toCode);
        } else {
            System.out.println("GET request failed!");
        }
    }

}

