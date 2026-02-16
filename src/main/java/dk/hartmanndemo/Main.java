package dk.hartmanndemo;

import dk.hartmanndemo.service.APIReader;

public class Main {
    public static void main(String[] args) {
        APIReader apiReader = new APIReader();
        String key = System.getenv("api_key");
        String response = apiReader.getDataWithClient("https://api.themoviedb.org/3/find/tt0068646?external_source=imdb_id&api_key="+key);
        System.out.println(response);
    }


}