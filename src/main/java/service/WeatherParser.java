package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherParser {
    final static private String URL_TEXT = "https://yandex.ru/";
    final static private String REG_EX = ".*?<div class='weather__content'>.*?\"(.*?)\"";

    private String getHtml() {
        StringBuffer resp = null;
        try {
            URL url = new URL(URL_TEXT);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader bf = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            resp = new StringBuffer();
            while (true) {
                String inputString = bf.readLine();
                if (inputString != null) {
                    resp.append(inputString);
                } else {
                    break;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (resp != null) ? resp.toString() : "";
    }

    public String getWeatherFromInternet() {
        String text = getHtml();
        Pattern pattern = Pattern.compile(REG_EX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return (matcher.find()) ? matcher.group(1) : "";
    }

}
