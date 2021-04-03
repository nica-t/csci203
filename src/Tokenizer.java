public class Tokenizer {

	private static TokenVO tokens = new TokenVO();
	private static TokenVO currentToken = tokens;

	public static void tokenize(String input) throws Exception {

		TokenVO baseToken = tokens;
		String str = input;

		while (str.length() > 0) {
			str = getNextToken(str);
		}
		
		//print
		TokenVO displayToken = baseToken;
		while (displayToken.getNextToken() != null) {
			displayToken = displayToken.getNextToken();
			System.out.println(displayToken.getT().getName() + " " + displayToken.getLexeme() );
		}

	}

	private static String getNextToken(String input) {

		TokenVO newToken = new TokenVO();
		int i = 0;
		String newStr = "";
		boolean skip = false;

		MainSwitch: switch (input.charAt(i)) {
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
			if (i + 1 < input.length() && input.charAt(i + 1) == '/') {
				String remainingStr = input.substring(i, input.length());
				int j = remainingStr.indexOf('\n');
				i = j > 0 ? i+j: input.length() - 1;
				skip = true;
			} else {
				newToken.setLexeme("/");
				newToken.setT(Token.DIVIDE);
			}
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
		case '\0':
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
				int m = remainingStr.indexOf('\n');
				
				if (k < m) {
					newToken.setLexeme(input.substring(0, k+2));
					newToken.setT(Token.STRING);
					i = k + 1;
				} else {
					newToken.setLexeme("");
					newToken.setT(Token.UNTERMINATED);
					i = m + 1;
				}
				 
			} else {
				newToken.setLexeme("");
				newToken.setT(Token.UNTERMINATED);
				i = input.length()-1;
			}
			break;
		}
		case '#': {
			String remainingStr = input.substring(i, input.length());
			int j = remainingStr.indexOf('\n');
			i = j > 0 ? i+j : input.length() -1;
			skip = true;
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
						eExist = true;
					} else if (numberRequiredNext && (k == '-' || k == '+')) {
						lexeme = lexeme + k;
						numberRequiredNext = false;
						digitRequiredNext = true;
					} else if (!digitRequiredNext && k != '.') {
						break; // valid
					} else {
						newToken.setLexeme("");
						newToken.setT(Token.BADLYFORMED);
						
						String remainingStr = input.substring(i, input.length());
						int s = remainingStr.indexOf(' ');
						int m = remainingStr.indexOf('\n');
						
						if (s == -1 && m == -1) {
							i = input.length() - 1;
						} else {
							i = s < m? i + s: i + m;
						}
						
						break MainSwitch;
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
					/* } else if (isValidAfterIdent(k)) {
						break; // valid
					} else {
						newToken.setLexeme("");
						newToken.setT(Token.ILLEGALCHAR);
						
						String remainingStr = input.substring(i, input.length());
						int s = remainingStr.indexOf(' ');
						int m = remainingStr.indexOf('\n');
						
						if (s == -1 && m == -1) {
							i = input.length() - 1;
						} else {
							i = s < m? i + s: i + m;
						}
						
						break MainSwitch;
					}*/
					} else {
						break; // valid
					}
					j++;
				}
				
				newToken.setLexeme(lexeme);
				newToken.setT(Token.IDENT);
				
				i = j-1 ;
			} else {
				newToken.setLexeme("");
				newToken.setT(Token.ILLEGALCHAR);
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

	private static boolean isValidAfterIdent(char nextChar) {
		return (nextChar == '+' || nextChar == '-' || nextChar == '*' || nextChar == '/' || nextChar == '\n' ||
				nextChar == '%' || nextChar == ' ' || nextChar == '(' || nextChar == ')' || nextChar == ';' || 
				nextChar == '#' || nextChar == '"');
				
	}

}
