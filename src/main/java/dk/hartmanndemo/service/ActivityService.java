package dk.hartmanndemo.service;

import dk.hartmanndemo.dtos.ActivityDTO;
import dk.hartmanndemo.dtos.CityInfoDTO;
import dk.hartmanndemo.dtos.WeatherDTO;
import dk.hartmanndemo.persistence.Activity;
import dk.hartmanndemo.persistence.ActivityDAO;
import dk.hartmanndemo.persistence.ExerciseType;
import dk.hartmanndemo.persistence.HibernateConfig;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

/**
 * Purpose:
 * author: Thomas Hartmann
 */
public class ActivityService {
    ActivityDAO activityDAO = new ActivityDAO(HibernateConfig.getEntityManagerFactory());
    EntityConversion entityConversion = new EntityConversion();
    public ActivityDTO createActivity(LocalDate date, ExerciseType type, LocalTime timeOfDay, int duration, int distance, String comment){
        APIReader apiReader = new APIReader();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<CityInfoDTO> cityData = executor.submit(apiReader::getCityData);
        Future<WeatherDTO> weatherData = executor.submit(apiReader::getWeatherData);

        ActivityDTO activityDTO = new ActivityDTO(date.format(DateTimeFormatter.ISO_LOCAL_DATE), type, timeOfDay, duration, distance, comment);
        try {
            activityDTO.setCityInfoDTO(cityData.get());
            activityDTO.setWeatherDTO(weatherData.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
        return activityDTO;
    }

    public ActivityDTO persistActivity(ActivityDTO activityDTO){
        Activity activity = entityConversion.convertToActivity(activityDTO);
        activity = activityDAO.create(activity);
        return entityConversion.convertToActivityDTO(activity);
    }


    public static void main(String[] args) {
        ActivityService activityService = new ActivityService();
        ActivityDTO activityDTO = activityService.createActivity(LocalDate.now(), ExerciseType.RUN, LocalTime.now(), 60, 10, "Nice run");
        System.out.println(activityDTO);
        ActivityDTO returned = activityService.persistActivity(activityDTO);
        System.out.println(returned);
    }
}
