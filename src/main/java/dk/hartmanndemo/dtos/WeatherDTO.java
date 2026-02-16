package dk.hartmanndemo.dtos;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDTO {

    @JsonProperty("LocationName")
    private String locationName;

    @JsonProperty("CurrentData")
    private CurrentData currentData;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrentData {

        @JsonProperty("temperature")
        private double temperature;

        @JsonProperty("skyText")
        private String skyText;

        @JsonProperty("humidity")
        private String humidity;

        @JsonProperty("windText")
        private String windText;
    }
}

