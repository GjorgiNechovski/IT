package it.zad.zad4.Controllers;

import it.zad.zad4.accountBase.Account;
import it.zad.zad4.accountBase.AccountRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class ApplicationController {
    AccountRepository repository = new AccountRepository();

    @GetMapping("/")
    public String firstLog() {
        return "redirect:/application";
    }

    @GetMapping("/application")
    public String applicationPage(HttpSession session, Model model) {
        if (session.getAttribute("LoggedIn") == null) {
            return "application";
        } else {
            String role = session.getAttribute("role").toString().toLowerCase();
            Object scrapedData = model.asMap().get("scrapedData");
            if (scrapedData != null)
                model.addAttribute("scrapedData", scrapedData);

            return role;
        }
    }


    @PostMapping("/application")
    public String logIn(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        Account user = repository.checkAccount(username, password);

        if (user == null) {
            model.addAttribute("error", "LOGIN UNSUCCESSFUL");
            return "application";
        }

        if (user.getRole().equals("DATE")) {
            session.setAttribute("username", username);
            session.setAttribute("role", user.getRole());


            model.addAttribute("username", username);
            model.addAttribute("role",  user.getRole());

            session.setAttribute("LoggedIn", "true");

            return "date";
        }
        else if(user.getRole().equals("HANGMAN")){
            model.addAttribute("username", username);
            model.addAttribute("role", user.getRole());

            session.setAttribute("LoggedIn", "true");

            session.setAttribute("username", username);
            session.setAttribute("role", user.getRole());

            return "hangman";
        }

        return "application";
    }
}
