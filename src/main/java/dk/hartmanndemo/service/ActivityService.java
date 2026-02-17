package dk.hartmanndemo.service;

import dk.hartmanndemo.dtos.ActivityDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Purpose:
 * author: Thomas Hartmann
 */
public class ActivityService {
    public ActivityDTO createActivity(LocalDate date, ActivityDTO.ExerciseType type, LocalTime timeOfDay, int duration, int distance, String comment){
        ActivityDTO activityDTO = new ActivityDTO(date.format(DateTimeFormatter.ISO_LOCAL_DATE), type, timeOfDay, duration, distance, comment);
        APIReader apiReader = new APIReader();
        activityDTO.setCityInfoDTO(apiReader.getCityData());
        activityDTO.setWeatherDTO(apiReader.getWeatherData());
        return activityDTO;
    }

    public static void main(String[] args) {
        ActivityService activityService = new ActivityService();
        ActivityDTO activityDTO = activityService.createActivity(LocalDate.now(), ActivityDTO.ExerciseType.RUN, LocalTime.now(), 60, 10, "Nice run");
        System.out.println(activityDTO);
    }
}
