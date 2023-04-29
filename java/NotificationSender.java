import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.OutputStream;

public class NotificationSender {

    public static void sendNotification(String channelId, String notificationTitle, String notificationBody) {
        String baseUrl = "https://us-central1-getpushed-8ad1d.cloudfunctions.net/createNotificationEndpoint/"
                + channelId;

        // Make http post
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);
            String jsonInputString = "{\"title\": \"" + notificationTitle + "\", \"description\": \"" + notificationBody
                    + "\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            System.out.println(
                    "Notification Result: " + connection.getResponseCode() + " " + connection.getResponseMessage());

        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
        }
    }

    public static void main(String[] args) {
        sendNotification("channel_id", "notification_title", "notification_body");
    }
}
