package de.notonlyone.notificationplugin;

import java.io.BufferedReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * notificationplugin java plugin
 */
public class Plugin extends JavaPlugin implements org.bukkit.event.Listener {
  private static final Logger LOGGER = Logger.getLogger("notificationplugin");
  private String apiUrl;

  public void onEnable() {
    saveDefaultConfig();
    FileConfiguration config = getConfig();
    apiUrl = config.getString("apiUrl",
        "https://us-central1-getpushed-8ad1d.cloudfunctions.net/createNotificationEndpoint/fphRgjBMrKN1uNN2xn4E");
    getServer().getPluginManager().registerEvents(this, this);
    LOGGER.info("notificationplugin enabled");
    LOGGER.info("apiUrl: " + apiUrl);
  }

  public void onDisable() {
    LOGGER.info("notificationplugin disabled");
  }

  public void sendNotification(String title, String description) {
    LOGGER.info("Sending notification: " + title + " " + description);

    final String _title = title;
    final String _description = description;
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          URI myUri = new URI(apiUrl);
          URL url = myUri.toURL();
          // the endpoints expects a json object in the body of the post request,
          // containing the title and description of the notification
          // when there was an error return the error message which was returned by the
          // endpoint
          // as text
          String jsonInputString = "{\"title\":\"" + _title + "\",\"description\":\"" + _description + "\"}";

          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setDoOutput(true);
          conn.setRequestMethod("POST");
          conn.setRequestProperty("Content-Type", "application/json");

          // set timeout to 3 seconds
          conn.setConnectTimeout(3000);
          conn.setReadTimeout(3000);

          OutputStream os = conn.getOutputStream();
          os.write(jsonInputString.getBytes());
          os.flush();

          if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
          }

          BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
          String output;
          while ((output = br.readLine()) != null) {
            LOGGER.info(output);
          }

          conn.disconnect();
        } catch (URISyntaxException | IOException e) {
          e.printStackTrace();
        }
      }
    });
    thread.start();
  }

  @org.bukkit.event.EventHandler
  public void onJoin(PlayerJoinEvent event) {
    sendNotification("Player Joined",
        event.getPlayer().getName() + " has joined the server.");
  }

  @org.bukkit.event.EventHandler
  public void onQuit(PlayerQuitEvent event) {
    sendNotification("Player Left",
        event.getPlayer().getName() + " has left the server.");
  }

  @org.bukkit.event.EventHandler
  public void onChat(org.bukkit.event.player.AsyncPlayerChatEvent event) {
    sendNotification("Player Chat",
        event.getPlayer().getName() + ": " + event.getMessage());
  }

  @org.bukkit.event.EventHandler
  public void onDeath(org.bukkit.event.entity.PlayerDeathEvent event) {
    sendNotification("Player Death",
        event.getEntity().getName() + " has died.");
  }

  @org.bukkit.event.EventHandler
  public void onCommand(org.bukkit.event.player.PlayerCommandPreprocessEvent event) {
    sendNotification("Player Command",
        event.getPlayer().getName() + " has executed the command " + event.getMessage());
  }
}

// https://
// us-central1-getpushed-8ad1d.cloudfunctions.net/createNotificationEndpoint/fphRgjBMrKN1uNN2xn4E/Player
// // Left/MarcMarder has left the server.