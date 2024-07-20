#include <cpprest/http_client.h>
#include <cpprest/filestream.h>
#include <cpprest/json.h>

using namespace utility;
using namespace web;
using namespace web::http;
using namespace web::http::client;
using namespace concurrency::streams;

void send_notification(const string_t& channel_id, const string_t& notification_title, const string_t& notification_body)
{
    string_t base_url = U("https://createnotificationendpointgen2-duaunwu3gq-uc.a.run.app/") + channel_id;

    // Make http post
    try
    {
        http_client client(base_url);

        json::value data;
        data[U("title")] = json::value::string(notification_title);
        data[U("description")] = json::value::string(notification_body);

        http_request request(methods::POST);
        request.headers().add(U("Content-Type"), U("application/json"));
        request.set_body(data.serialize());

        client.request(request)
            .then([](http_response response)
        {
            return response.extract_string();
        })
            .then([](string_t result)
        {
            std::wcout << U("Notification Result: ") << result << std::endl;
        }).wait();
    }
    catch (const std::exception& e)
    {
        std::wcout << U("Error: ") << e.what() << std::endl;
    }
}

int main()
{
    send_notification(U("channel_id"), U("notification_title"), U("notification_body"));
    return 0;
}
