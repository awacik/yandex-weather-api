import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        String apiKey = "API-KEY";
        String url = "https://api.weather.yandex.ru/v2/forecast?lat=61.6637&lon=50.816";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-Yandex-Weather-Key", apiKey)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Ошибка запроса. HTTP-код ошибки: " + response.statusCode());
            return;
        }

        String json = response.body();
        System.out.println(json);

        Pattern factTempPattern = Pattern.compile(
                "\"fact\"\\s*:\\s*\\{.*?\"temp\"\\s*:\\s*(-?\\d+)",
                Pattern.DOTALL);

        Matcher factTempMatcher = factTempPattern.matcher(json);

        if (factTempMatcher.find()) {
            int temp = Integer.parseInt(factTempMatcher.group(1));
            System.out.printf("%nТекущая температура: %d°C", temp);
        } else {
            System.out.println("Не найдена информация по текущей температуре");
        }

        Pattern avgTempPattern = Pattern.compile("\"temp_avg\"\\s*:\\s*(-?\\d+)");

        Matcher avgTempMatcher = avgTempPattern.matcher(json);

        int limit = 7;
        int count = 0;
        int sum = 0;

        while (avgTempMatcher.find() && count < limit) {
            sum += Integer.parseInt(avgTempMatcher.group(1));
            count++;
        }

        if (count > 0) {
            double avgTemp = (double) sum / count;
            System.out.printf("%nСредняя температура за %d суток %.1f°C", count, avgTemp);
        } else {
            System.out.println("Не найдена информация по средней температуре");
        }
    }
}