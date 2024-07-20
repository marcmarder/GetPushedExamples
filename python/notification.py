# pip install requests
import requests
import json

async def send_notification(channel_id, notification_title, notification_body):
    base_url = f"https://createnotificationendpointgen2-duaunwu3gq-uc.a.run.app/{channel_id}"

    # Make http post
    try:
        result = requests.post(
            base_url,
            headers={
                "Content-Type": "application/json",
            },
            data=json.dumps({
                "title": notification_title,
                "description": notification_body,
            })
        )
        print("Notification Result: " +
              str(result.status_code) +
              " " +
              result.text)
    except Exception as e:
        print("Error: " + str(e))

# Example usage:
# asyncio.run(send_notification("channel_id", "notification_title", "notification_body"))
