package gjorgi.domashni.it.zad2.myClasses;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Controller
public class Date {
    @GetMapping("/date")
    public String date(@RequestParam Map<String, String> params, Model model) {
        if (params.isEmpty()) {
            model.addAttribute("date", getDate());
        } else {
            String parameterName = params.keySet().iterator().next();
            if (parameterName.equals("time")) {
                model.addAttribute("date", getTimeAndDate());
            } else {
                model.addAttribute("date", "INVALID OPERATION - the service only functions either without any parameters or with the parameter \"time\", used with or without a value");
            }
        }
        return "date";
    }


    public String getDate(){
        LocalDate temp = LocalDate.now(ZoneId.of("UTC"));
        return temp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getTimeAndDate(){
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return now.format(formatter);
    }
}
