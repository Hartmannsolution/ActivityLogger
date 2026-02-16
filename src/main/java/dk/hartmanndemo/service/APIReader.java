package dk.hartmanndemo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.hartmanndemo.dtos.CityInfoDTO;
import dk.hartmanndemo.dtos.WeatherDTO;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class APIReader {
    private static final String WHEATHERURL = "https://vejr.eu/api.php?location=Roskilde&degree=C";
    private static final String CITYURL = "https://dawa.aws.dk/steder?hovedtype=Bebyggelse&undertype=by&prim%C3%A6rtnavn=Roskilde";
    public static void main(String[] args) {
//        WeatherDTO weatherDTO = new APIReader().getWeatherData();
        APIReader instance = new APIReader();
//        instance.getDataWithClient(URL);
        String wheatherData = instance.getDataWithObjectMapper(WHEATHERURL);
        System.out.println(wheatherData);
        System.out.println("-------------------------------------------------");
        WeatherDTO weatherDTO = instance.getWeatherData();
        System.out.println(weatherDTO);
        System.out.println("-------------------------------------------------");
        String cityData = instance.getDataWithObjectMapper(CITYURL);
        System.out.println(cityData);
        System.out.println("-------------------------------------------------");
        CityInfoDTO cityInfoDTO = instance.getCityData();
        System.out.println(cityInfoDTO);
    }

    public WeatherDTO getWeatherData(){
        String response = getDataWithObjectMapper(WHEATHERURL);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, WeatherDTO.class);
        } catch (JsonProcessingException ex){
            ex.printStackTrace();
            throw new IllegalArgumentException("Could not convert data to WeatherDTO. Try again later");
        }
    }
    public CityInfoDTO getCityData(){
        String response = getDataWithObjectMapper(CITYURL);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, CityInfoDTO[].class)[0];
        } catch (JsonProcessingException ex){
            ex.printStackTrace();
            throw new IllegalArgumentException("Could not convert data to CityInfoDTO. Try again later");
        }
    }

    private String getDataWithObjectMapper(String url){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(new URI(url).toURL());
            return node.toPrettyString();
        } catch (URISyntaxException | IOException ex){
            ex.printStackTrace();
            throw new IllegalArgumentException("Could not retrieve data from the provided URL. Try again later");
        }
    }
    public String getDataWithClient(String url){

        try {
            // Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            // Create a request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the status code and print the response
            if (response.statusCode() == 200) {
//                System.out.println(response.body());
                return response.body();
            }
            System.out.println("GET request failed. Status code: " + response.statusCode());
            throw new IllegalArgumentException("Could not retrieve data from the provided URL. Try again later");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
