
public class TokenVO {
	private Token t;
	private String lexeme;
	private TokenVO nextToken;
	
	public Token getT() {
		return t;
	}
	public void setT(Token t) {
		this.t = t;
		t.addOne();
	}
	public String getLexeme() {
		return lexeme;
	}
	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}
	public TokenVO getNextToken() {
		return nextToken;
	}
	public void setNextToken(TokenVO nextToken) {
		this.nextToken = nextToken;
	}

}
