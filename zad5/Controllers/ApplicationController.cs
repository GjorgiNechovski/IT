using Microsoft.AspNetCore.Mvc;
using zad5.Models.scaperModel;

namespace zad5.Controllers
{
    public class ApplicationController : Controller {
        private AccountRepository repository = new AccountRepository();

        [HttpGet("/")]
        public IActionResult FirstLog(){
            return RedirectToAction("ApplicationPage");
        }

        
        [HttpGet("/application")]
        public IActionResult ApplicationPage(){
             if(Request.Cookies.ContainsKey("role")){
                var model = new scaperModel {name=Request.Cookies["username"], role=Request.Cookies["role"], scrapedData=""};

                if(TempData["scrappedData"]!=null){
                    model.scrapedData = TempData["scrappedData"].ToString();
                }
                

                return View(Request.Cookies["role"].ToLower(), model);
             }
                
            return View("application");
        }

        [HttpPost("/application")]
        public IActionResult LogIn(string username, string password) {

            if(Request.Cookies.ContainsKey("role")){
                var model = new scaperModel {name=Request.Cookies["username"], role=Request.Cookies["role"], scrapedData=""};

                return View(Request.Cookies["role"].ToLower(), model);
            }

            Account user = repository.CheckAccount(username, password);

            if (user == null)
                return View("application");
    

            if (user.getRole() == "DATE"){
                Response.Cookies.Append("username", user.getName());
                Response.Cookies.Append("role", user.getRole());

                var model = new scaperModel {name=user.getName(), role=user.getRole(), scrapedData=""};

                return View("date", model);
            }

            else if (user.getRole() == "HANGMAN"){
                 Response.Cookies.Append("username", user.getName());
                Response.Cookies.Append("role", user.getRole());

                var model = new scaperModel {name=user.getName(), role=user.getRole(), scrapedData=""};

                return View("hangman", model);
            }

            return View("application");
        }

       
     }
}
