import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Temp {

    public static String factTemp(String json) {
        Pattern factTempPattern = Pattern.compile(
                "\"fact\"\\s*:\\s*\\{.*?\"temp\"\\s*:\\s*(-?\\d+)",
                Pattern.DOTALL);

        Matcher factTempMatcher = factTempPattern.matcher(json);

        if (factTempMatcher.find()) {
            int temp = Integer.parseInt(factTempMatcher.group(1));
            return "Текущая температура: " + temp + "°C";
        } else {
            return "Не найдена информация по текущей температуре";
        }
    }

    public static String avgTemp(String json, int limit) {
        Pattern avgTempPattern = Pattern.compile("\"day\"\\s*:\\s*\\{.*?\"temp_avg\"\\s*:\\s*(-?\\d+)",
                Pattern.DOTALL);

        Matcher avgTempMatcher = avgTempPattern.matcher(json);

        int count = 0;
        int sum = 0;

        while (avgTempMatcher.find() && count < limit) {
            sum += Integer.parseInt(avgTempMatcher.group(1));
            count++;
        }

        if (count > 0) {
            double avgTemp = Math.round((double) sum / count * 10.0) / 10.0;
            return "Средняя температура за " + count +
                    " суток " + avgTemp + "°C";
        } else {
            return "Не найдена информация по средней температуре";
        }
    }
}
