import 'dart:async';
import 'dart:convert';
import 'package:http/http.dart' as http;

Future<void> sendNotification(
    String channelId, String notificationTitle, String notificationBody) async {
  final baseUrl =
      "https://createnotificationendpointgen2-duaunwu3gq-uc.a.run.app/${channelId}";

  // Make http post
  try {
    final result = await http.post(
      Uri.parse(baseUrl),
      headers: {
        "Content-Type": "application/json",
      },
      body: jsonEncode({
        "title": notificationTitle,
        "description": notificationBody,
      }),
    );
    print("Notification Result: " +
        result.statusCode.toString() +
        " " +
        result.body.toString());
  } catch (e) {
    print("Error: " + e.toString());
  }
}
