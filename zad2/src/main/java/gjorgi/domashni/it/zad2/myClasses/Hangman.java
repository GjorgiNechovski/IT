package gjorgi.domashni.it.zad2.myClasses;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.Map;

@Controller
@SessionAttributes({"GuessedLetters", "CorrectWord"})
public class Hangman {

    @RequestMapping(value = "/hangman", method = {RequestMethod.GET, RequestMethod.POST})
    public String hangman(@RequestParam(required = false) String operand1, @RequestParam Map<String, String> requestParams, Model model, HttpServletRequest requestMethod) {
        checkFirstLoad(model);

        if ((operand1 == null || operand1.isEmpty()) && requestParams.isEmpty()) {
            model.addAttribute("help",
                    "INVALID OPERATION - the hangman service expects a GET or POST parameter operand1 with a single alphabetical character as a value and will output if you guessed the char correctly (if an imagined string contains the guessed character and at which positions)");
            return "help";
        } else {
            if(RequestMethod.valueOf(requestMethod.getMethod()) == RequestMethod.POST){
                if(operand1 == null || operand1.isEmpty() ){
                    model.addAttribute("help",
                            "INVALID OPERATION - the hangman service expects a GET or POST parameter operand1 with a single alphabetical character as a value and will output if you guessed the char correctly (if an imagined string contains the guessed character and at which positions)");
                    return "help";
                }
                if (operand1.contains("operand1=")){
                    try{
                        operand1 = operand1.split("operand1=")[1];
                    }
                    catch (Exception e){
                        model.addAttribute("error",
                                "INVALID OPERATION - the hangman service expects a GET or POST parameter operand1 with a single alphabetical character as a value and will output if you guessed the char correctly (if an imagined string contains the guessed character and at which positions)");
                        return "error";
                    }
                }
                else{
                    model.addAttribute("error",
                            "INVALID OPERATION - the hangman service expects a GET or POST parameter operand1 with a single alphabetical character as a value and will output if you guessed the char correctly (if an imagined string contains the guessed character and at which positions)");
                    return "error";
                }

            }
            else{
                Enumeration<String> parameterName = requestMethod.getParameterNames();
                if(!parameterName.nextElement().equals("operand1")){
                    model.addAttribute("error",
                            "INVALID OPERATION - the hangman service expects a GET or POST parameter operand1 with a single alphabetical character as a value and will output if you guessed the char correctly (if an imagined string contains the guessed character and at which positions)");
                    return "error";
                }
            }
            try {
                if (operand1.length() > 1 || Character.isDigit(operand1.charAt(0))) {
                    model.addAttribute("error",
                            "INVALID OPERATION - the hangman service expects a GET or POST parameter operand1 with a single alphabetical character as a value and will output if you guessed the char correctly (if an imagined string contains the guessed character and at which positions)");
                    return "error";
                }
            }
            catch (Exception e){
                model.addAttribute("error",
                        "INVALID OPERATION - the hangman service expects a GET or POST parameter operand1 with a single alphabetical character as a value and will output if you guessed the char correctly (if an imagined string contains the guessed character and at which positions)");
                return "error";
            }



            String correctWord = (String) model.getAttribute("CorrectWord");
            String guessedLettersStr = (String) model.getAttribute("GuessedLetters");
            char[] guessedLetters = guessedLettersStr.toCharArray();

            if (correctWord.contains(operand1)) {
                char[] temp = correctWord.toCharArray();
                for (int i = 0; i < correctWord.length(); i++) {
                    if (temp[i] == operand1.charAt(0)) {
                        guessedLetters[i] = operand1.charAt(0);
                    }
                }
            }

            if (new String(guessedLetters).equals(correctWord)) {
                model.addAttribute("hangman",
                        "You guessed correctly - " + correctWord);
                model.addAttribute("GuessedLetters", "________");
                return "hangman";
            }

            model.addAttribute("GuessedLetters", new String(guessedLetters));
            model.addAttribute("hangman", new String(guessedLetters));
        }

        return "hangman";
    }

    @ModelAttribute("GuessedLetters")
    public String getGuessedLetters() {
        return "________";
    }

    @ModelAttribute("CorrectWord")
    public String getCorrectWord() {
        return "СТРУМИЦА";
    }

    public void checkFirstLoad(Model model) {
        String guessedLetters = (String) model.getAttribute("CorrectWord");
        if (guessedLetters == null) {
            model.addAttribute("GuessedLetters", "________");
            model.addAttribute("CorrectWord", "СТРУМИЦА");
        }
    }
}
