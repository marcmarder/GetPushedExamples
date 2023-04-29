package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
)

func sendNotification(channelID string, notificationTitle string, notificationBody string) {
	baseURL := fmt.Sprintf("https://us-central1-getpushed-8ad1d.cloudfunctions.net/createNotificationEndpoint/%s", channelID)

	// Make http post
	data := map[string]string{
		"title":       notificationTitle,
		"description": notificationBody,
	}
	jsonData, err := json.Marshal(data)

	if err != nil {
		fmt.Println("Error:", err)
		return
	}

	resp, err := http.Post(baseURL, "application/json", bytes.NewBuffer(jsonData))

	if err != nil {
		fmt.Println("Error:", err)
		return
	}
	defer resp.Body.Close()

	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		fmt.Println("Error:", err)
		return
	}

	fmt.Printf("Notification Result: %d %s\n", resp.StatusCode, string(body))
}

func main() {
	sendNotification("channel_id", "notification_title", "notification_body")
}
