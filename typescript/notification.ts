import fetch from 'node-fetch';

export async function sendNotification(
  channel: string,
  title: string,
  description: string
) {
  const result = await fetch(
    "https://us-central1-getpushed-8ad1d.cloudfunctions.net/createNotificationEndpoint/" +
      channel,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        title: title,
        description: description,
      }),
    }
  );
  console.log(result.statusText);
}
