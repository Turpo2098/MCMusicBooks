package tf.tfischer.musicbooks.interpreter.objects;


import org.bukkit.Instrument;
import org.bukkit.Note;

public class Token {
    TokenType   tokenType;
    String identifier;
    Instrument  instrument;
    Note.Tone   note;
    int octave;
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

    public Token(TokenType tokenType, Note.Tone note, int octave) {
        this.tokenType = tokenType;
        this.note = note;
        this.octave = octave;
    }

    public Token(double number) {
        this.tokenType = TokenType.NUMBER;
        this.number = number;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Note.Tone getNote() {
        return note;
    }

    public int getOctave() {
        return octave;
    }

    public double getNumber() {
        return number;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{Token: ");
        switch (tokenType){
            case NUMBER -> {
                stringBuilder.append(number);
            }
            case NOTE -> {
                stringBuilder.append(note.toString()).append(',').append(octave);
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
