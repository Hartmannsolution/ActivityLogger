package dk.hartmanndemo.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalTime;

/**
 * Purpose:
 *
 * author: Thomas Hartmann
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityDTO {
    private String exerciseDate;
    private ExerciseType exerciseType;
    private LocalTime timeOfDay;
    private int duration;
    private double distance;
    private String comment;

    public ActivityDTO(String exerciseDate, ExerciseType exerciseType, LocalTime timeOfDay, int duration, double distance, String comment) {
        this.exerciseDate = exerciseDate;
        this.exerciseType = exerciseType;
        this.timeOfDay = timeOfDay;
        this.duration = duration;
        this.distance = distance;
        this.comment = comment;
    }
    private CityInfoDTO cityInfoDTO;
    private WeatherDTO weatherDTO;

    public static enum ExerciseType {
        RUN, BIKE, SWIM
    }
}
