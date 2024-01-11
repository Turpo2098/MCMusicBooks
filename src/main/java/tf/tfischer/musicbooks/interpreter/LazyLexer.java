package tf.tfischer.musicbooks.interpreter;

import org.bukkit.Note;
import org.bukkit.Note.Tone;
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
        return c == ' ' || c == '\t' || c == '\n' || c == 'r';
    }

    private boolean isSymbol(char c){
        return c == '<' || c == '>';
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
        for(int i = stringPos + 1; i<currentPage.length(); i++){
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
            case "=" -> {
                return Optional.of(new Token(TokenType.DEF));
            }
            case "<" -> {
                return Optional.of(new Token(TokenType.LBUNDLE));
            }
            case ">" -> {
                return Optional.of(new Token(TokenType.RBUNDLE));
            }
            case " ", "\n", "\t", "\r" -> {
                return Optional.of(new Token(TokenType.WHITESPACE));
            }
            case "#" -> {
                return Optional.of(new Token(TokenType.SHARP));
            }
            default -> {
                try {
                    double d = Double.parseDouble(str);
                    return Optional.of(new Token(d));
                } catch (Exception ignored){};
            }
        }
        return Optional.of(new Token(str));
    }
}
