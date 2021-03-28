
public enum Token {
	PLUS("PLUS"),
	MINUS("MINUS"),
	MULT("MULT"), 
	DIVIDE("DIVIDE"), 
	MODULO("MODULO"), 
	EXP("EXP"), 
	LPAREN("LPAREN"), 
	RPAREN("RPAREN"), 
	COMMA("COMMA"), 
	SEMICOLON("SEMICOLON"), 
	EQUALS("EQUALS"), 
	NUMBER("NUMBER"), 
	IDENT("IDENT"), 
	STRING("STRING"), 
	EOF("EOF");

	private int count = 0;
	private String name;

	public void addOne() {
		count++;
	}

	public int getCount() {
		return count;
	}

	Token(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
