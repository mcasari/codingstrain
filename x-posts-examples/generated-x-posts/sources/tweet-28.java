@Cacheable(value = "weather", key = "#city")
public String getWeather(String city) {
  simulateSlowApiCall(); // 3s
  return "Sunny in " + city;
}
