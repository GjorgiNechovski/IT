using Microsoft.AspNetCore.Mvc;
using zad5.Models.scaperModel;

namespace zad5.Controllers
{
    public class DateController : Controller{
        [HttpPost("/date")]
        public async Task<IActionResult> GetDate(string option, string paramName, string paramValue){
            string redirectUrl = "";

            switch (option){
                case "date.cgi":
                    redirectUrl = "http://localhost:8081/date.cgi";
                    if (!string.IsNullOrEmpty(paramName) || !string.IsNullOrEmpty(paramValue))
                        redirectUrl += "?" + paramName + "=" + paramValue;
                    break;
                case "date Spring":
                    redirectUrl = "http://localhost:8082/date";
                    if (!string.IsNullOrEmpty(paramName) || !string.IsNullOrEmpty(paramValue))
                        redirectUrl += "?" + paramName + "=" + paramValue;
                    break;
                case "date .NET":
                    redirectUrl = "http://localhost:8083/date";
                    if (!string.IsNullOrEmpty(paramName) || !string.IsNullOrEmpty(paramValue))
                        redirectUrl += "?" + paramName + "=" + paramValue;
                    break;
            }

            HttpClient client = new HttpClient();
            string response = await CallUrl(redirectUrl);

            if(response.Contains("PM") || response.Contains("AM"))
                response = response.ToString().Split("12:")[0]; // ne znam zoshto konvertiranjeto ne raboti

            var model = new scaperModel{name = Request.Cookies["username"], role=Request.Cookies["role"], scrapedData = response};
            TempData["scrappedData"] = response;

            return RedirectToAction("ApplicationPage", "Application");
            // ako e vlezot bez parametri sekogash mi dava 12:00:00 AM na krajot od datata

        }

        private static async Task<string> CallUrl(string fullUrl){
            HttpClient client = new HttpClient();
            var response = await client.GetStringAsync(fullUrl);
            return response.Split("<p>")[1].Split("</p>")[0];
        }
    }
}
