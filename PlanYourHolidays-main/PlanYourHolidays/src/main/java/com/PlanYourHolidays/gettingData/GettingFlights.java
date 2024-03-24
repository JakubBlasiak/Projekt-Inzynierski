package com.PlanYourHolidays.gettingData;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.PlanYourHolidays.gettingData.extractingDataFromEndpoint.getStringResponseEntity;

@Service
@Slf4j
public class GettingFlights {
    private static final String URL = "https://test.api.amadeus.com/v2/shopping/flight-offers?originLocationCode=";
    private static final String destinationURL = "&destinationLocationCode=";
    private static final String departureDateURL = "&departureDate=";
    private static final String reurnDateURL = "&returnDate=";
    private static final String numberOfAdultsURL = "&adults=";
    private static final String nonStopURL = "&nonStop=";
    private static final String currencyCodeURL = "&currencyCode=";
    private static final String maxPriceURL = "&maxPrice=";
    static private final String maxResultsURL = "&max=";


    public static double getFlightData(String flightTo, String flightFrom, String departureDate, String returnDate, int seats) throws JSONException {

        //String flightTo = "JFK";
        //String flightFrom = "MLA";

        //String departureDate = "2024-05-02";
        //String returnDate = "2024-05-09";

        //int seats = 2;

        boolean nonStop = false;

        String currencyCode = "PLN";

        int maxPrice = 10000;

        int maxResults = 5;

        String finalURL = URL + flightTo + destinationURL + flightFrom + departureDateURL + departureDate + reurnDateURL
                + returnDate + numberOfAdultsURL + seats + nonStopURL + nonStop + currencyCodeURL + currencyCode
                + maxPriceURL + maxPrice + maxResultsURL + maxResults;

        String result = String.valueOf(getStringResponseEntity(finalURL));

        System.out.println(result);
        if (Objects.equals(result, "<200 OK OK,0,[]>")) {
            return 0;
        }

        return extracter(result);
    }

    public static double extracter(String jsonResponse) throws JSONException {

        //System.out.println(jsonResponse);

        int startIndex = jsonResponse.indexOf("{");
        String jsonOnly = jsonResponse.substring(startIndex);

        JSONObject jsonObject = new JSONObject(jsonOnly);
        //JSONArray dataArray = jsonObject.getJSONArray("data");
        //String price = dataArray.getJSONObject(4).getJSONObject("price").getString("total");
        JSONArray data = jsonObject.getJSONArray("data");
        //System.out.println(price);
        List<Double> totalValues = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            JSONObject flightOffer = data.getJSONObject(i);
            JSONObject price = flightOffer.getJSONObject("price");
            String total = price.getString("total");
            System.out.println("Total " + (i + 1) + ": " + total);
            totalValues.add(Double.parseDouble(total));
        }

        if (totalValues.isEmpty()) {
            System.out.println("No flight total values found.");
            return 0;
        }

        double lowestValue = Collections.min(totalValues);
        System.out.println("Lowest flight total value: " + lowestValue);

        return lowestValue;
    }

    public static String getBestFlightCode(String flightTo, String flightFrom, String departureDate, String returnDate, int seats) throws JSONException {
        boolean nonStop = false;

        String currencyCode = "PLN";

        int maxPrice = 10000;

        int maxResults = 5;

        String finalURL = URL + flightTo + destinationURL + flightFrom + departureDateURL + departureDate + reurnDateURL
                + returnDate + numberOfAdultsURL + seats + nonStopURL + nonStop + currencyCodeURL + currencyCode
                + maxPriceURL + maxPrice + maxResultsURL + maxResults;

        String result = String.valueOf(getStringResponseEntity(finalURL));

        System.out.println(result);
        if (Objects.equals(result, "<200 OK OK,0,[]>")) {
            return null;
        }

        return getFlightName(result);
    }


    public static String getFlightName(String jsonResponse) throws JSONException {

        int startIndex = jsonResponse.indexOf("{");
        String jsonOnly = jsonResponse.substring(startIndex);

        JSONObject jsonObject = new JSONObject(jsonOnly);
        JSONArray data = jsonObject.getJSONArray("data");
        List<Double> totalValues = new ArrayList<>();
        List<String> allFlightCodes = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            JSONObject flightOffer = data.getJSONObject(i);
            JSONObject price = flightOffer.getJSONObject("price");
            String total = price.getString("total");
            System.out.println("Total " + (i + 1) + ": " + total);
            totalValues.add(Double.parseDouble(total));

            JSONObject flightData = data.getJSONObject(i);
            JSONArray itineraries = flightData.getJSONArray("itineraries");

            for (int j = 0; j < itineraries.length(); j++) {
                JSONObject itinerary = itineraries.getJSONObject(j);
                JSONArray segments = itinerary.getJSONArray("segments");

                for (int k = 0; k < segments.length(); k++) {
                    JSONObject segment = segments.getJSONObject(k);
                    String carrierCode = segment.getString("carrierCode");
                    String number = segment.getString("number");
                    String code = segment.getJSONObject("aircraft").getString("code");
                    String finalData = carrierCode + " " + number + " " + code;
                    allFlightCodes.add(finalData);


                }
            }
        }
        if (totalValues.isEmpty()) {
            System.out.println("No flight total values found.");
            return null;
        }

        double lowestValue = Collections.min(totalValues);
        int lowestIndex = totalValues.indexOf(lowestValue);

        if (lowestIndex==0){
            return allFlightCodes.get(2);
        }else{
            return allFlightCodes.get(lowestIndex*2);
            }
    }
}