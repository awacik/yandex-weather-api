import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);

        System.out.print("Введите количество суток для средней температуры (максимально 7): ");
        int limit = sc.nextInt();

        if (limit <= 0 || limit > 7) {
            System.out.println("Неверное количество суток");
            return;
        }

        String apiKey = "API-KEY";
        String url = "https://api.weather.yandex.ru/v2/forecast?lat=61.6637&lon=50.816&limit=" + limit;
        String json;

        try {
            json = Http.httpConnect(apiKey, url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println(json);
        System.out.println(Temp.factTemp(json));
        System.out.println(Temp.avgTemp(json, limit));
    }
}