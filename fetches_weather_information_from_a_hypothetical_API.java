import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherApp {
    
    // API key for the weather service
    private static final String API_KEY = "your_api_key"; 
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";

    // Method to fetch weather data
    public static String getWeatherData(String city) {
        String response = "";
        try {
            // Construct the API URL
            String apiUrl = BASE_URL + city + "&appid=" + API_KEY + "&units=metric"; // For Celsius
            
            // Establish a connection to the API
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the API response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // Close connection
            connection.disconnect();

            response = content.toString();
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
        return response;
    }

    // Method to parse and display weather data
    public static void displayWeather(String city) {
        String weatherData = getWeatherData(city);

        try {
            // Parse JSON response
            JSONObject jsonObject = new JSONObject(weatherData);
            String cityName = jsonObject.getString("name");
            JSONObject main = jsonObject.getJSONObject("main");
            double temperature = main.getDouble("temp");
            String weatherDescription = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");

            // Display weather information
            System.out.println("City: " + cityName);
            System.out.println("Temperature: " + temperature + "°C");
            System.out.println("Weather: " + weatherDescription);

        } catch (Exception e) {
            System.out.println("Error parsing weather data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Example: fetch weather for Colombo
        displayWeather("Colombo");
    }
}
