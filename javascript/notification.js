import * as http from "http";

async function sendNotification(
  channelId,
  notificationTitle,
  notificationBody
) {
  const baseUrl = `https://createnotificationendpointgen2-duaunwu3gq-uc.a.run.app/${channelId}`;

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
