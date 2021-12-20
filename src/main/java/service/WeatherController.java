package service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class WeatherController {
    private final AtomicLong counter = new AtomicLong();
    private DbFeatures dbFeatures;

    public WeatherController() {
        this.dbFeatures = DbFeatures.getInstance(); //Создаем синглтон БД
    }


    @GetMapping("/weather")
    public Weather getWeather() {
        String databaseWeather = dbFeatures.getTodayWeatherFromDatabase();
        if (databaseWeather != null) {

            return new Weather(
                    counter.incrementAndGet(), databaseWeather
            );
        } else {

            WeatherParser parser = new WeatherParser();
            String currentWeather = parser.getWeatherFromInternet();
            dbFeatures.setTodayWeatherToDatabase(currentWeather);
            return new Weather(
                    counter.incrementAndGet(), currentWeather
            );
        }
    }
}
