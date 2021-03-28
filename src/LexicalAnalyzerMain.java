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

		// test
		file = "C:\\workspace\\CSCI203_Project\\sampleinput.txt";
		
		StringBuilder resultStringBuilder = new StringBuilder();
	    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            resultStringBuilder.append(line).append("\n");
	        }
	        
	        System.out.println("Tokenizing: " + resultStringBuilder.toString());
	        Tokenizer.tokenize(resultStringBuilder.toString());
	    } catch (IOException e) {
			System.out.printf("Enter filename: {}", e.getMessage());
		}

	}

}
