import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class date {
    public static void main(String[] args){
        System.out.println("Content-Type: text/html\n\n");

        String request = System.getenv("REQUEST_URI");

        LocalDate date = LocalDate.now();

        if(!request.contains("?")){
            System.out.println("<html><head><title>Current Date</title></head><body>");
            System.out.println("<p>" + date + "</p>");
        }
        else{
            String time = request.split("\\?")[1];

            if(time.split("=")[0].equals("time")){
                LocalTime localTime = LocalTime.now();
                DateTimeFormatter formater = DateTimeFormatter.ofPattern("HH:mm:ss");

                System.out.println("<html><head><title>Current Date and Time</title></head><body>");
                System.out.println("<p>" + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " " + localTime.format(formater) + "</p>");
            }

            else {
                System.out.println("<html><head><title>Current Date</title></head><body>");
                System.out.println("<p>" + "INVALID OPERTION - the service only functions either without any parameters or with the parameter 'time', used with or without a value" + "</p>");
            }
        }


        System.out.println("</body></html>");
    }
}
