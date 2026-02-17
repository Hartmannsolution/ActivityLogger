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
 * author: Thomas Hartmann
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
        System.out.println("##########################################################################");
        System.out.println("------------------Using a generic converter-------------------------------");
        WeatherDTO weatherDTO2 = instance.getAndConvertData(WHEATHERURL, WeatherDTO.class);
        System.out.println(weatherDTO2);
        CityInfoDTO cityInfoDTO2 = instance.getAndConvertData(CITYURL, CityInfoDTO[].class)[0];
        System.out.println(cityInfoDTO2);

    }

    public WeatherDTO getWeatherData(){
        String response = getDataWithObjectMapper(WHEATHERURL);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, WeatherDTO.class);
        } catch (JsonProcessingException ex){
            throw new IllegalArgumentException("Could not convert data to WeatherDTO. Try again later");
        }
    }
    public CityInfoDTO getCityData(){
        String response = getDataWithObjectMapper(CITYURL);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, CityInfoDTO[].class)[0];
        } catch (JsonProcessingException ex){
            throw new IllegalArgumentException("Could not convert data to CityInfoDTO. Try again later");
        }
    }

    private String getDataWithObjectMapper(String url){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(new URI(url).toURL());
            return node.toPrettyString();
        } catch (URISyntaxException | IOException ex){
            throw new IllegalArgumentException("Could not retrieve data from the provided URL. Try again later");
        }
    }
    private <T> T getAndConvertData(String url, Class<T> tClass){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(new URI(url).toURL());
            return objectMapper.treeToValue(node, tClass);
        } catch (URISyntaxException | IOException ex){
            throw new IllegalArgumentException("Could not retrieve data from the provided URL. Try again later");
        }
    }


    public String getDataWithClient(String url){

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            }
            System.out.println("GET request failed. Status code: " + response.statusCode());
            throw new IllegalArgumentException("Could not retrieve data from the provided URL. Try again later");
        } catch (Exception e) {
            throw new IllegalArgumentException("An error occurred while trying to retrieve data. Try again later");
        }
    }
}
