package tf.tfischer.musicbooks.interpreter;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.jetbrains.annotations.NotNull;
import tf.tfischer.musicbooks.interpreter.objects.Token;
import tf.tfischer.musicbooks.interpreter.objects.TokenType;

import java.util.List;
import java.util.Optional;

public class LazyLexer {

    private List<String> inputPages;
    private String currentPage;

    private int pagePos = 0;
    private int stringPos = 0;

    public LazyLexer(@NotNull List<String> inputPages) {
        this.inputPages = inputPages;
        if(inputPages.size()>0)
            currentPage = inputPages.get(0);
    }

    private boolean isWhiteSpace(char c){ //TODO: Add switch statement for performance
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    private boolean isSymbol(char c){
        return c == '<' || c == '>' || c == '#';
    }

    private boolean isBreak(char c){
        return isWhiteSpace(c) || isSymbol(c);
    }

    private boolean isEndOfPage(){
        return currentPage.length() <= stringPos;
    }

    public boolean isFinished(){
        return currentPage == null;
    }


    private void pop(){
        if(isEndOfPage()){
            pagePos++;
            stringPos = 0;
            if(pagePos >= inputPages.size()){
                currentPage = null;
                return;
            }
            currentPage = inputPages.get(pagePos);
        }
        stringPos++;
    }

    private void pop(int n){
        for(int i = 0; i<n;i++){
            pop();
        }
    }

    private char peek(){
        if(stringPos >= currentPage.length()){ //Handle a new Page as a new Lines
            return '\n';
        }
        return currentPage.charAt(stringPos);
    }

    private char peek(int n){
        int newPos = stringPos + n;
        if(newPos >= currentPage.length()){ //Handle a new Page as a new Lines
            return '\n';
        }
        return currentPage.charAt(stringPos + n);
    }

    public String nextString(){
        StringBuilder stringBuilder = new StringBuilder();
        char test = peek();
        stringBuilder.append(test);
        pop();
        if(isBreak(test))
            return stringBuilder.toString();

        if(currentPage == null)
            return "";
        for(int i = stringPos + 1; i<=currentPage.length(); i++){
            char c = peek();
            if(isBreak(c))
                break;
            stringBuilder.append(c);
            pop();
        }

        return stringBuilder.toString();
    }

    public Optional<Token> nextToken(){
        if(isFinished())
            return Optional.empty();
        String str = nextString();
        switch (str){
            case "def" -> {
                return Optional.of(new Token(TokenType.DEF));
            }
            case "wait" -> {
                return Optional.of(new Token(TokenType.WAIT));
            }
            case "<" -> {
                return Optional.of(new Token(TokenType.LBUNDLE));
            }
            case ">" -> {
                return Optional.of(new Token(TokenType.RBUNDLE));
            }
            case " ", "\n", "\t", "\r" -> {
                return nextToken();
            }
            case "#" -> {
                return Optional.of(new Token(TokenType.SHARP));
            }
            case "A","B","C","D","E","F","G" -> {
                Note.Tone tone = Note.Tone.valueOf(str);
                return Optional.of(new Token(TokenType.NOTE, tone,2));
            }
            case "a","b","c","d","e","f","g" -> {
                Note.Tone tone = Note.Tone.valueOf(str.toUpperCase());
                return Optional.of(new Token(TokenType.NOTE, tone,1));
            }

            //Instruments

            case "bsd" ->{
                Instrument instrument = Instrument.BASS_DRUM;
                return Optional.of(new Token(TokenType.INSTRUMENT,instrument));
            }
            case "bsg" ->{
                Instrument instrument = Instrument.BASS_GUITAR;
                return Optional.of(new Token(TokenType.INSTRUMENT,instrument));
            }
            case "sna" ->{
                Instrument instrument = Instrument.SNARE_DRUM;
                return Optional.of(new Token(TokenType.INSTRUMENT,instrument));
            }
            case "hat" -> {
                Instrument instrument = Instrument.STICKS;
                return Optional.of(new Token(TokenType.INSTRUMENT,instrument));
            }
            case "bel" -> {
                Instrument instrument = Instrument.BELL;
                return Optional.of(new Token(TokenType.INSTRUMENT,instrument));
            }
            case "cbl" -> {
                Instrument instrument = Instrument.COW_BELL;
                return Optional.of(new Token(TokenType.INSTRUMENT,instrument));
            }
            case "flu" -> {
                Instrument instrument = Instrument.FLUTE;
                return Optional.of(new Token(TokenType.INSTRUMENT,instrument));
            }
            case "chi" -> {
                Instrument instrument = Instrument.CHIME;
                return Optional.of(new Token(TokenType.INSTRUMENT,instrument));
            }
            case "gui" -> {
                Instrument instrument = Instrument.GUITAR;
                return Optional.of(new Token(TokenType.INSTRUMENT,instrument));
            }
            case "xyl" -> {
                Instrument instrument = Instrument.XYLOPHONE;
                return Optional.of(new Token(TokenType.INSTRUMENT,instrument));
            }
            case "ixy" -> {
                Instrument instrument = Instrument.IRON_XYLOPHONE;
                return Optional.of(new Token(TokenType.INSTRUMENT,instrument));
            }
            case "ddg" -> {
                Instrument instrument = Instrument.DIDGERIDOO;
                return Optional.of(new Token(TokenType.INSTRUMENT,instrument));
            }
            case "bit" -> {
                Instrument instrument = Instrument.BIT;
                return Optional.of(new Token(TokenType.INSTRUMENT,instrument));
            }
            case "bnj" -> {
                Instrument instrument = Instrument.BANJO;
                return Optional.of(new Token(TokenType.INSTRUMENT,instrument));
            }
            case "plg" -> {
                Instrument instrument = Instrument.PLING;
                return Optional.of(new Token(TokenType.INSTRUMENT,instrument));
            }
            case "hrp" -> {
                Instrument instrument = Instrument.PIANO;
                return Optional.of(new Token(TokenType.INSTRUMENT,instrument));
            }

            default -> {
                try {
                    double d = Double.parseDouble(str);
                    return Optional.of(new Token(d));
                } catch (Exception ignored){};
                return Optional.of(new Token(str));
            }
        }
    }
}
