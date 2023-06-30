package it.zad.zad4.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class HangmanController {

    //TODO sredi go POST za .cgi
    //TODO sredi UTF-16 od .NET vo UTF-8 za Java
    @PostMapping("/hangman")
    public String forwardToHangman(@RequestParam String option,
                                   @RequestParam String type,
                                   @RequestParam String paramName,
                                   @RequestParam String paramValue,
                                   RedirectAttributes redirectAttributes) throws IOException {
        String redirectUrl = "";
        if (type.equals("GET")) {
            switch (option) {
                case "hangman.cgi":
                    redirectUrl = "http://localhost:8081/hangman.cgi";
                    if (!paramName.isEmpty() || !paramValue.isEmpty()) {
                        String encodedParamValue = URLEncoder.encode(paramValue, StandardCharsets.UTF_8);
                        redirectUrl += "?" + paramName + "=" + encodedParamValue;
                    }
                    break;
                case "hangman Spring":
                    redirectUrl = "http://localhost:8082/hangman";
                    if (!paramName.isEmpty() || !paramValue.isEmpty()) {
                        String encodedParamValue = URLEncoder.encode(paramValue, StandardCharsets.UTF_8);
                        redirectUrl += "?" + paramName + "=" + encodedParamValue;
                    }
                    break;
                case "hangman .NET":
                    redirectUrl = "http://localhost:8083/hangman";
                    if (!paramName.isEmpty() || !paramValue.isEmpty()) {
                        String encodedParamValue = URLEncoder.encode(paramValue, StandardCharsets.UTF_8);
                        redirectUrl += "?" + paramName + "=" + encodedParamValue;
                    }
                    break;
            }

            String scrapedData = extractDataFromHtml(fetchHtmlContent(redirectUrl));
            redirectAttributes.addFlashAttribute("scrapedData", scrapedData);
        }  else {
            switch (option) {
                case "hangman.cgi":
                    redirectUrl = "http://localhost:8081/hangman.cgi";
                    break;
                case "hangman Spring":
                    redirectUrl = "http://localhost:8082/hangman";
                    break;
                case "hangman .NET":
                    redirectUrl = "http://localhost:8083/hangman";
                    break;
            }

            URL urlObject = new URL(redirectUrl);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String postData = "operand1=" + paramName + "=" + paramValue;
            byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);
            connection.getOutputStream().write(postDataBytes);

            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null)
                    response.append(line);
            }


            String scrapedData = extractDataFromHtml(response.toString());
            redirectAttributes.addFlashAttribute("scrapedData", scrapedData);
        }

        return "redirect:/application";
    }


    private String fetchHtmlContent(String url) throws IOException {
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept-Charset", "UTF-8");

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            char[] buffer = new char[4096];
            int bytesRead;
            while ((bytesRead = reader.read(buffer)) != -1) {
                content.append(buffer, 0, bytesRead);
            }
        }

        return content.toString();
    }



    private String extractDataFromHtml(String htmlContent) {
        if(htmlContent.contains("<p>"))
            return htmlContent.split("<p>")[1].split("</p>")[0];
        else if(htmlContent.contains("<body>"))
             return htmlContent.split("<body>")[1].split("</body>")[0];
        return htmlContent;
    }
}
