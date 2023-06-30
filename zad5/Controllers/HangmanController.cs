using Microsoft.AspNetCore.Mvc;
using zad5.Models.scaperModel;

namespace zad5.Controllers
{
    public class HangManController : Controller
    {
        [HttpPost("/hangman")]
        public async Task<IActionResult> GetHangman(string option, string type, string paramName, string paramValue)
        {
            string redirectUrl = "";

            switch (option)
            {
                case "date.cgi":
                    redirectUrl = "http://localhost:8081/hangman.cgi";
                    if (!string.IsNullOrEmpty(paramName) || !string.IsNullOrEmpty(paramValue))
                        redirectUrl += "?" + paramName + "=" + paramValue;
                    break;
                case "date Spring":
                    redirectUrl = "http://localhost:8082/hangman";
                    if (!string.IsNullOrEmpty(paramName) || !string.IsNullOrEmpty(paramValue))
                        redirectUrl += "?" + paramName + "=" + paramValue;
                    break;
                case "date .NET":
                    redirectUrl = "http://localhost:8083/hangman";
                    if (!string.IsNullOrEmpty(paramName) || !string.IsNullOrEmpty(paramValue))
                        redirectUrl += "?" + paramName + "=" + paramValue;
                    break;
            }
            string response = "";

            if (type == "GET")
            {
                HttpClient client = new HttpClient();
                response = await CallUrlGet(redirectUrl);
            }
            else
            {
                Dictionary<string, string> postData = new Dictionary<string, string>
                {
                    { "operand1", $"{paramName}={paramValue}" }
                };
                response = await CallUrlPost(redirectUrl, postData);
            }

            if(response.Contains("<p>"))
                response = response.Split("<p>")[1].Split("</p>")[0];
             else if(response.Contains("</head>"))
                 response = response.Split("</head>")[1];

            var model = new scaperModel { name = Request.Cookies["username"], role = Request.Cookies["role"], scrapedData = response };
            TempData["scrappedData"] = response;

            return RedirectToAction("ApplicationPage", "Application");


        }

        private static async Task<string> CallUrlGet(string fullUrl)
        {
            HttpClient client = new HttpClient();
            var response = await client.GetStringAsync(fullUrl);
            return response;
        }
        private static async Task<string> CallUrlPost(string fullUrl, Dictionary<string, string> postData)
        {
            HttpClient client = new HttpClient();
            var content = new FormUrlEncodedContent(postData);
            var response = await client.PostAsync(fullUrl, content);
            response.EnsureSuccessStatusCode();
            var responseString = await response.Content.ReadAsStringAsync();
            return responseString;
        }
    }
}