package dk.hartmanndemo.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "activity")
public class Activity implements IEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String exerciseDate;
    private ExerciseType exerciseType;
    private LocalTime timeOfDay;
    private int duration;
    private double distance;
    private String comment;
    // From city info API
    private String cityName;
    // From weather API
    private String weatherDescription;
    private double temperature;
    private String windText;
}