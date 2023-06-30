package it.zad.zad4.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class DateController {

    @PostMapping("/date")
    public String forwardToHangman(@RequestParam String option, @RequestParam String paramName,
                                   @RequestParam String paramValue, RedirectAttributes redirectAttributes) throws IOException {
        String redirectUrl = "";
        switch (option) {
            case "date.cgi":
                redirectUrl = "http://localhost:8081/date.cgi";
                if (!paramName.isEmpty() || !paramValue.isEmpty())
                    redirectUrl += "?" + paramName + "=" + paramValue;
                break;
            case "date Spring":
                redirectUrl = "http://localhost:8082/date";
                if (!paramName.isEmpty() || !paramValue.isEmpty())
                    redirectUrl += "?" + paramName + "=" + paramValue;
                break;
            case "date .NET":
                redirectUrl = "http://localhost:8083/date";
                if (!paramName.isEmpty() || !paramValue.isEmpty())
                    redirectUrl += "?" + paramName + "=" + paramValue;
                break;
        }

        String scrapedData = extractDataFromHtml(fetchHtmlContent(redirectUrl));
        redirectAttributes.addFlashAttribute("scrapedData", scrapedData);

        return "redirect:/application";
    }

    private String fetchHtmlContent(String url) throws IOException {
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("GET");

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null)
                content.append(line);
        }
        finally {
            connection.disconnect();
        }

        return content.toString();
    }

    private String extractDataFromHtml(String htmlContent) {
        return htmlContent.split("<p>")[1].split("</p>")[0];
    }
}
