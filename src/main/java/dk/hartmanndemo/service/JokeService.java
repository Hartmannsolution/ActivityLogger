package dk.hartmanndemo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Purpose:
 * author: Thomas Hartmann
 */
public class JokeService {
    private static final String CHUCK_URL = "https://api.chucknorris.io/jokes/random";
    private static final String DAD_URL = "https://icanhazdadjoke.com";
    private static JokeService instance;
    private static EntityManagerFactory emf;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        // Configure object mapper to ignore unknown properties
        objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JokeService service = new JokeService();
        String output = service.readJoke(DAD_URL);
        System.out.println(output);
        System.out.println("-------------------------------------------------");
        ChuckDTO chuck = service.getChuckJoke();
        System.out.println(chuck.getValue());
    }

    public String readJoke(String urlString){
        StringBuilder jsonStr = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("User-Agent", "MyApp");
            try (Scanner scan = new Scanner(con.getInputStream())) {
                while (scan.hasNext()) {
                    jsonStr.append(scan.nextLine());
                }
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return jsonStr.toString();
    }

    public ChuckDTO getChuckJoke(){
        try {
            ChuckDTO chuck = objectMapper.readValue(readJoke(CHUCK_URL), ChuckDTO.class);
            return chuck;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public DadDTO getDadJoke(){
        try {
            DadDTO dad = objectMapper.readValue(readJoke(DAD_URL), DadDTO.class);
            return dad;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    private static class ChuckDTO {
        String created_at;
        String icon_url;
        String id;
        String updated_at;
        String url;
        String value;

        public ChuckDTO() {
        }

        @Override
        public String toString() {
            return "ChuckDTO{" +
                    "created_at='" + created_at + '\'' +
                    ", icon_url='" + icon_url + '\'' +
                    ", id='" + id + '\'' +
                    ", updated_at='" + updated_at + '\'' +
                    ", url='" + url + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }

        public String getValue() {
            return value;
        }
    }

    private static class DadDTO {
        String id;
        String joke;

        public DadDTO() {
        }

        @Override
        public String toString() {
            return "ChuckDTO{" +
                    ", id='" + id + '\'' +
                    ", joke='" + joke + '\'' +
                    '}';
        }

        public String getJoke() {
            return joke;
        }
    }

}
