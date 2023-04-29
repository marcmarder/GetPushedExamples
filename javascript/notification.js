import * as http from "http";

async function sendNotification(
  channelId,
  notificationTitle,
  notificationBody
) {
  const baseUrl = `https://us-central1-getpushed-8ad1d.cloudfunctions.net/createNotificationEndpoint/${channelId}`;

  // Make http post
  try {
    const result = await http.post(baseUrl, {
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        title: notificationTitle,
        description: notificationBody,
      }),
    });
    console.log(
      "Notification Result: " +
        result.statusCode.toString() +
        " " +
        result.body.toString()
    );
  } catch (e) {
    console.log("Error: " + e.toString());
  }
}

export { sendNotification };
