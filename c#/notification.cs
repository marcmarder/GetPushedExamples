// Install-Package Newtonsoft.Json
using System;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace YourNamespace
{
    public class NotificationSender
    {
        public async Task SendNotification(string channelId, string notificationTitle, string notificationBody)
        {
            string baseUrl = $"https://createnotificationendpointgen2-duaunwu3gq-uc.a.run.app/{channelId}";

            // Make http post
            try
            {
                using (HttpClient client = new HttpClient())
                {
                    var content = new StringContent(JsonConvert.SerializeObject(new
                    {
                        title = notificationTitle,
                        description = notificationBody
                    }), Encoding.UTF8, "application/json");

                    HttpResponseMessage result = await client.PostAsync(baseUrl, content);
                    Console.WriteLine("Notification Result: " +
                        result.StatusCode.ToString() + " " +
                        await result.Content.ReadAsStringAsync());
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("Error: " + e.ToString());
            }
        }
    }
}
