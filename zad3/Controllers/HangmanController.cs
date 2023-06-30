using Microsoft.AspNetCore.Mvc;
using zad3.Models;

namespace zad3.Controllers{
    public class HangmanController : Controller{
        [HttpGet("/hangman")]
        public IActionResult getMethod(string operand1){
            var queryParams = HttpContext.Request.Query;
            if (queryParams.Count == 0)
                return View("help");

            string input;
            if (queryParams.ContainsKey("operand1"))
                input = queryParams["operand1"];
            else
                return View("error");

            if (input.Length != 1)
                return View("error");

            string guessedLetters;
            string correctWord;

            if (checkCookies())  {
                guessedLetters = "________";
                correctWord = "СТРУМИЦА";
            }
            else {
                guessedLetters = Request.Cookies["guessedLetters"];
                correctWord = Request.Cookies["correctWord"];
            }

            var guessedLettersArray = guessedLetters.ToCharArray();
            for (int i = 0; i < correctWord.Length; i++) {
                if (correctWord[i] == input[0])
                    guessedLettersArray[i] = input[0];
            }

            guessedLetters = new string(guessedLettersArray);
            
            Response.Cookies.Append("guessedLetters", guessedLetters);

            var model = new hangmanModel { GuessedLetters = guessedLetters };

            return View("hangman", model);
        }

        [HttpPost("/hangman")]
        public IActionResult postMethod(string operand1) {
            if (operand1 == null)
                return View("help");

            if(!operand1.Contains("operand1="))
                return View("error");
            
            try{
                operand1 = operand1.Split("operand1=")[1];
            }
            catch(Exception e){
                return View("error");
            }

            if (operand1.Length != 1)
                return View("error");

            string guessedLetters;
            string correctWord;

            if (checkCookies()) {
                guessedLetters = "________";
                correctWord = "СТРУМИЦА";
            }
            else{
                guessedLetters = Request.Cookies["guessedLetters"];
                correctWord = Request.Cookies["correctWord"];
            }

            var guessedLettersArray = guessedLetters.ToCharArray();
            for (int i = 0; i < correctWord.Length; i++){
                if (correctWord[i] == operand1[0])
                    guessedLettersArray[i] = operand1[0];
            }

            guessedLetters = new string(guessedLettersArray);

            Response.Cookies.Append("guessedLetters", guessedLetters);

            var model = new hangmanModel { GuessedLetters = guessedLetters };
            return View("hangman", model);
        }

        private bool checkCookies(){
            bool returnMe = false;
            if (!Request.Cookies.ContainsKey("correctWord")) {
                Response.Cookies.Append("correctWord", "СТРУМИЦА");
                returnMe = true;
            }

            if (!Request.Cookies.ContainsKey("guessedLetters"))
                Response.Cookies.Append("guessedLetters", "________");

            return returnMe;
        }
    }
}
