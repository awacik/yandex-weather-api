import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        String apiKey = "bc3dd378-e327-41ad-aafb-a92e94e21e51";
        String url = "https://api.weather.yandex.ru/v2/forecast?lat=61.40&lon=50.49";

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



    }

}