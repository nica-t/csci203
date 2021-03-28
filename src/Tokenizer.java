public class Tokenizer {

	private static TokenVO tokens = new TokenVO();
	private static TokenVO currentToken = tokens;
	private static boolean valid = true;

	public static void tokenize(String input) throws Exception {

		TokenVO baseToken = tokens;
		String str = input;

		while (str.length() > 0 && valid) {
			str = getNextToken(str);
		}
		
		//print
		TokenVO displayToken = baseToken;
		while (displayToken.getNextToken() != null) {
			displayToken = displayToken.getNextToken();
			System.out.println(displayToken.getT().getName() + ": " + displayToken.getLexeme() );
		}

	}

	private static String getNextToken(String input) {

		TokenVO newToken = new TokenVO();
		int i = 0;
		String newStr = "";
		boolean skip = false;

		switch (input.charAt(i)) {
		case '+': {
			newToken.setLexeme("+");
			newToken.setT(Token.PLUS);
			break;
		}
		case '-': {
			newToken.setLexeme("-");
			newToken.setT(Token.MINUS);
			break;
		}
		case '*': {
			if (input.charAt(i + 1) == '*') {
				newToken.setLexeme("**");
				newToken.setT(Token.EXP);
				i++;
			} else {
				newToken.setLexeme("*");
				newToken.setT(Token.MULT);
			}
			break;
		}
		case '/': {
			newToken.setLexeme("/");
			newToken.setT(Token.DIVIDE);
			break;
		}
		case '%': {
			newToken.setLexeme("%");
			newToken.setT(Token.MODULO);
			break;
		}
		case '(': {
			newToken.setLexeme("(");
			newToken.setT(Token.LPAREN);
			break;
		}
		case ')': {
			newToken.setLexeme(")");
			newToken.setT(Token.RPAREN);
			break;
		}
		case ',': {
			newToken.setLexeme(",");
			newToken.setT(Token.COMMA);
			break;
		}
		case ';': {
			newToken.setLexeme(";");
			newToken.setT(Token.SEMICOLON);
			break;
		}
		case '=': {
			newToken.setLexeme("=");
			newToken.setT(Token.EQUALS);
			break;
		}
		case ' ':
		case '\n':{
			skip = true;
			break;
		}
		case '\'': 
		case '"': {
			if( i+1 < input.length()) {
				char charAtI = input.charAt(i);
				int j = i+1;
				String remainingStr = input.substring(j, input.length());
				int k = remainingStr.indexOf(charAtI);
				System.out.println("index of next " + k);
				
				newToken.setLexeme(input.substring(0, k+2));
				newToken.setT(Token.STRING);
				i = k+1;
			} else {
				System.out.println("UNTERMINATED STRING");
				valid = false;
			}
			break;
		}
		default: {
			char x = input.charAt(i);

			// number
			if (Character.isDigit(x)) {
				int j = i + 1;
				String lexeme = "" + x;
				boolean dotExist = false;
				boolean eExist = false;
				boolean digitRequiredNext = false;
				boolean numberRequiredNext = false;

				while (j < input.length()) {
					char k = input.charAt(j);
					if (Character.isDigit(k)) {
						lexeme = lexeme + k;
						digitRequiredNext = false;
					} else if (!dotExist && k == '.') {
						lexeme = lexeme + k;
						digitRequiredNext = true;
					} else if (!eExist && (k == 'e' || k == 'E')) {
						lexeme = lexeme + k;
						numberRequiredNext = true;
					} else if (numberRequiredNext && k == '-') {
						lexeme = lexeme + k;
						numberRequiredNext = false;
						digitRequiredNext = true;
					} else if (!digitRequiredNext && k != '.') {
						break; // valid
					} else {
						System.out.println("BADLY FORMED NUMBER");
						valid = false;
					}
					j++;
				}
				
				newToken.setLexeme(lexeme);
				newToken.setT(Token.NUMBER);
				
				i = j-1 ;
			} else if (Character.isLetter(x)) {
				int j = i + 1;
				String lexeme = "" + x;
				while (j < input.length()) {
					char k = input.charAt(j);
					if (Character.isLetter(k)) {
						lexeme = lexeme + k;
					} else if (isValidAfterIdent(k)) {
						break; // valid
					} else {
						System.out.println("ILLEGAL CHARCTER");
						valid = false;
					}
					j++;
				}
				
				newToken.setLexeme(lexeme);
				newToken.setT(Token.IDENT);
				
				i = j-1 ;
			} else {
				System.out.println("ILLEGAL CHARCTER" + x);
			}

		}
		}
		
		if(!skip) {
			currentToken.setNextToken(newToken);
			currentToken = currentToken.getNextToken();
		}

		if (i+1 <= input.length()) {
			newStr = input.substring(i+1, input.length());
		}
		
		return newStr;
	}

	private static boolean isValidAfterIdent(char letter) {
		return (letter == '+' || letter == '-' || letter == '*' || letter == '/' || letter == '\n' ||
				letter == '%' || letter == ' ' || letter == '(' || letter == ')' || letter == ';');
				
	}

}
