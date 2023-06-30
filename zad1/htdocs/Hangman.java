import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Hangman {
	public static void main(String[] args) throws IOException {
		firstLoad();

		Map<String, String> env = System.getenv();
		String type = env.get("REQUEST_METHOD");
		String input;

		if (type.equals("POST")) {
			if (env.get("CONTENT_LENGTH").equals("26")) { //10 za latinica // 15 za kirilica
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				input = in.readLine().split("=")[1];

				if(input.contains("%3D")) {
					if (input.split("%3D")[0].equals("operand1"))
						input = input.split("%3D")[1];
					else {
						error();
						return;
					}
				}

			} else if (env.get("CONTENT_LENGTH").equals("9")) {
				input = "";
			} else {
				input = "error";
			}
		} else {
			String get = env.get("QUERY_STRING");

			if (!get.contains("=")) {
				help();
				return;
			}

			String operand1 = get.split("=")[0];

			if (!operand1.equals("operand1")) {
				error();
				return;
			}

			if (get.split("=").length != 2)
				help();
			input = get.split("=")[1];
		}

		if (input.length() != 6 && input.length() != 0) { // input.length()>2 za latinica // input.length() != 6 && input.length() != 0 za kirilica
			error();
			return;
		}

		if (input.length() == 0) {
			help();
			return;
		}

		input = URLDecoder.decode(input, StandardCharsets.UTF_8);

		String cookies = env.get("HTTP_COOKIE");
		String correctAnswer = "СТРУМИЦА";
		String guessedLetters = "";
		boolean skip = false;

		if (cookies != null && !cookies.isEmpty()) {
			String[] cookieList = cookies.split("; ");
			for (String cookie : cookieList) {
				String[] cookieParts = cookie.split("=");
				String cookieName = cookieParts[0];
				String cookieValue = "";

				if (cookieParts.length >= 2)
					cookieValue = cookieParts[1];

				if (cookieName.equals("correctAnswer"))
					correctAnswer = cookieValue;
				else if (cookieName.equals("guessedLetters"))
					guessedLetters = URLDecoder.decode(cookieValue, StandardCharsets.UTF_8);
			}
			skip = true;
		}

		if (!skip) {
			guessedLetters = "________";
		}

		if (correctAnswer.contains(input)) {
			char[] temp = guessedLetters.toCharArray();
			for (int i = 0; i < correctAnswer.length(); i++) {
				if (correctAnswer.charAt(i) == input.charAt(0)) {
					temp[i] = input.charAt(0);
				}
			}
			guessedLetters = String.valueOf(temp);
		}

		String encodedGuessedLetters = URLEncoder.encode(guessedLetters, StandardCharsets.UTF_8);

		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);

		out.println("Set-Cookie: guessedLetters=" + encodedGuessedLetters);

		out.println("Content-Type: text/html; charset=UTF-8\n");
		if (guessedLetters.equals(correctAnswer))
			out.println("You guessed correctly СТРУМИЦА");
		else
			out.println(guessedLetters);

		out.close();
	}

	public static void error() {
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);
		out.println("Content-Type: text/html\n\n");
		out.println("<head><title>ERROR</title></head>");
		out.println("INVALID OPERATION - the hangman service expects a GET or POST parameter operand1 with a single alphabetical character as a value and will output if you guessed the char correctly (if an imagined string contains the guessed character and at which positions)");
		out.close();
	}

	public static void help() {
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);
		out.println("Content-Type: text/html\n\n");
		out.println("<head><title>HELP</title></head>");
		out.println("INVALID OPERATION - the hangman service expects a GET or POST parameter operand1 with a single alphabetical character as a value and will output if you guessed the char correctly (if an imagined string contains the guessed character and at which positions)");
		out.close();
	}

	public static void firstLoad() {
		String cookies = System.getenv("HTTP_COOKIE");

		if (cookies == null || !cookies.contains("guessedLetters")) {
			String guessedLetters = "________";
			PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);
			out.println("Set-Cookie: guessedLetters=" + URLEncoder.encode(guessedLetters, StandardCharsets.UTF_8));
		}
	}
}
