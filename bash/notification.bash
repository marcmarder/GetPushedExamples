#!/bin/bash

send_notification() {
  local channel_id="$1"
  local notification_title="$2"
  local notification_body="$3"

  local base_url="https://createnotificationendpointgen2-duaunwu3gq-uc.a.run.app/${channel_id}"

  # Make http post
  local result=$(curl -s -w "\n%{http_code}" -X POST "${base_url}" \
    -H "Content-Type: application/json" \
    -d "{\"title\": \"${notification_title}\", \"description\": \"${notification_body}\"}")

  local response_body=$(echo "${result}" | head -n1)
  local status_code=$(echo "${result}" | tail -n1)

  echo "Notification Result: ${status_code} ${response_body}"
}

send_notification "channel_id" "notification_title" "notification_body"
