import fetch from 'node-fetch';

export async function sendNotification(
  channel: string,
  title: string,
  description: string
) {
  const result = await fetch(
    "https://createnotificationendpointgen2-duaunwu3gq-uc.a.run.app/" +
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
