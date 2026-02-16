package dk.hartmanndemo.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityInfoDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("hovedtype")
    private String mainType;

    @JsonProperty("undertype")
    private String subType;

    @JsonProperty("primærtnavn")
    private String primaryName;

    @JsonProperty("primærnavnestatus")
    private String primaryNameStatus;

    @JsonProperty("ændret")
    private String modified;

    @JsonProperty("geo_ændret")
    private String geoModified;

    @JsonProperty("geo_version")
    private int geoVersion;

    @JsonProperty("href")
    private String href;

    @JsonProperty("egenskaber")
    private Properties properties;

    @JsonProperty("visueltcenter")
    private List<Double> visualCenter;

    @JsonProperty("bbox")
    private List<Double> boundingBox;

    @JsonProperty("kommuner")
    private List<Municipality> municipalities;

    @JsonProperty("sekundærenavne")
    private List<String> secondaryNames;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Properties {

        @JsonProperty("bebyggelseskode")
        private int settlementCode;

        @JsonProperty("indbyggerantal")
        private Integer population;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Municipality {

        @JsonProperty("href")
        private String href;

        @JsonProperty("kode")
        private String code;

        @JsonProperty("navn")
        private String name;
    }

}
