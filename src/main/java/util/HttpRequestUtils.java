package util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestUtils {
    public static String sendPost(String requestUrl, String urlParameters) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = urlParameters.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        return getResponse(conn);
    }

    public static String sendGet(String requestUrl) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        return getResponse(conn);
    }

    private static String getResponse(HttpURLConnection conn) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        }
    }
}

