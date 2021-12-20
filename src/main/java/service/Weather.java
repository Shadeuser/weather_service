package service;

public class Weather {
    private long id;
    private String weather;

    public Weather(long id, String weather) {
        this.id = id;
        this.weather = weather;
    }

    public long getId() {
        return id;
    }

    public String getWeather() {
        return weather;
    }
}
