use reqwest::header::{HeaderMap, CONTENT_TYPE};
use serde_json::json;
use std::error::Error;

async fn send_notification(
    channel_id: &str,
    notification_title: &str,
    notification_body: &str,
) -> Result<(), Box<dyn Error>> {
    let base_url = format!(
        "https://createnotificationendpointgen2-duaunwu3gq-uc.a.run.app/{}",
        channel_id
    );

    // Make http post
    let client = reqwest::Client::new();
    let mut headers = HeaderMap::new();
    headers.insert(CONTENT_TYPE, "application/json".parse().unwrap());

    let data = json!({
        "title": notification_title,
        "description": notification_body,
    });

    let response = client.post(&base_url).headers(headers).json(&data).send().await?;

    println!(
        "Notification Result: {} {}",
        response.status(),
        response.text().await?
    );

    Ok(())
}

#[tokio::main]
async fn main() {
    match send_notification("channel_id", "notification_title", "notification_body").await {
        Ok(_) => println!("Notification sent successfully"),
        Err(e) => println!("Error: {}", e),
    }
}
