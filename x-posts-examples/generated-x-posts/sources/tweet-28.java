@Cacheable(value = "weather", key = "#city")
public String getWeather(String city) {
    return fetchFromSlowApi(city);
}
