using Microsoft.AspNetCore.Mvc;
using zad3.Models;

namespace zad3.Controllers
{
    public class DateController : Controller
    {
        [HttpGet("/date")]
        public IActionResult Initial()
        {
            var queryParams = HttpContext.Request.Query;

            if (queryParams.Count == 0){
                var currentDate = DateTime.UtcNow.ToString("yyyy-MM-dd");
                var model = new DateViewModel { Time = currentDate };
                return View("date", model);
            }
            else{
                if (queryParams.ContainsKey("time")){
                    var currentTime = DateTime.UtcNow.ToString("dd.MM.yyyy HH:mm:ss");
                    var model = new DateViewModel { Time = currentTime };
                    return View("dateAndTime", model);
                }
                else{
                    var error = "INVALID OPERATION - the service only functions either without any parameters or with the parameter \"time\", used with or without a value";
                    var model = new DateViewModel { Time = error };
                    return View("date", model);
                }
            }
        }
    }
}
