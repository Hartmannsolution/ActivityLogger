package dk.hartmanndemo.service;

import dk.hartmanndemo.dtos.ActivityDTO;
import dk.hartmanndemo.dtos.CityInfoDTO;
import dk.hartmanndemo.dtos.WeatherDTO;
import dk.hartmanndemo.persistence.Activity;

public class EntityConversion {
    // convert ActivityDTO to Activity
    public Activity convertToActivity(ActivityDTO dto) {
        Activity activity = new Activity();
        activity.setExerciseDate(dto.getExerciseDate());
        activity.setExerciseType(dto.getExerciseType());
        activity.setTimeOfDay(dto.getTimeOfDay());
        activity.setDuration(dto.getDuration());
        activity.setDistance(dto.getDistance());
        activity.setComment(dto.getComment());
        CityInfoDTO cityInfoDTO = dto.getCityInfoDTO();
        if (cityInfoDTO != null) {
            activity.setCityName(cityInfoDTO.getPrimaryName());
        }
        WeatherDTO weatherDTO = dto.getWeatherDTO();
        if (weatherDTO != null) {
            activity.setWeatherDescription(weatherDTO.getCurrentData().getSkyText());
            activity.setWindText(weatherDTO.getCurrentData().getWindText());
            activity.setTemperature(weatherDTO.getCurrentData().getTemperature());
        }
        return activity;
    }
    public ActivityDTO convertToActivityDTO(Activity activity) {

        ActivityDTO dto = new ActivityDTO(activity.getExerciseDate(), activity.getExerciseType(), activity.getTimeOfDay(), activity.getDuration(), activity.getDistance(), activity.getComment());
        if(activity.getId() != null)
            dto.setId(activity.getId());
        CityInfoDTO cityInfoDTO = new CityInfoDTO();
        cityInfoDTO.setPrimaryName(activity.getCityName());
        dto.setCityInfoDTO(cityInfoDTO);
        WeatherDTO weatherDTO = new WeatherDTO();
        WeatherDTO.CurrentData currentData = new WeatherDTO.CurrentData();
        currentData.setSkyText(activity.getWeatherDescription());
        currentData.setWindText(activity.getWindText());
        currentData.setTemperature(activity.getTemperature());
        weatherDTO.setCurrentData(currentData);
        dto.setWeatherDTO(weatherDTO);
        return dto;
    }
}
