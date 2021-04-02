import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class LexicalAnalyzerMain {

	public static void main(String[] args) throws Exception {

		String file = null;
		if (args != null && args.length > 0) {
			file = args[0];
		} else {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter filename: ");
			file = sc.nextLine();
		}

		StringBuilder resultStringBuilder = new StringBuilder();
	    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            resultStringBuilder.append(line).append("\n");
	        }
	        
	        Tokenizer.tokenize(resultStringBuilder.toString());
	    } catch (IOException e) {
			System.out.printf("Cannot open filename: {}", e.getMessage());
		}

	}

}
