package tf.tfischer.musicbooks.interpreter.objects;


import org.bukkit.Instrument;
import org.bukkit.Note;

public class Token {
    TokenType   tokenType;
    String identifier;
    Instrument  instrument;
    Note.Tone   note;
    double      number;

    public Token(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public Token(String identifier) {
        tokenType = TokenType.IDENTIFIER;
        this.identifier = identifier;
    }

    public Token(TokenType tokenType, Instrument instrument) {
        this.tokenType = tokenType;
        this.instrument = instrument;
    }

    public Token(TokenType tokenType, Note.Tone note) {
        this.tokenType = tokenType;
        this.note = note;
    }

    public Token(double number) {
        this.tokenType = TokenType.NUMBER;
        this.number = number;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{Token: ");
        switch (tokenType){
            case NUMBER -> {
                stringBuilder.append(number);
            }
            case NODE -> {
                stringBuilder.append(note.toString());
            }
            case IDENTIFIER -> {
                stringBuilder.append(identifier);
            }
            default -> {
                stringBuilder.append(tokenType.toString());
            }
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
