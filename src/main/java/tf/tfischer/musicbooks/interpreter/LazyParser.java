package tf.tfischer.musicbooks.interpreter;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import tf.tfischer.musicbooks.interpreter.objects.Token;
import tf.tfischer.musicbooks.interpreter.objects.TokenType;
import tf.tfischer.musicbooks.interpreter.objects.music.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class LazyParser {
    private final LazyLexer lexer;
    private final Server server;
    private final Player player;

    public LazyParser(Server server, List<String> book, Player player){
        lexer = new LazyLexer(book);
        this.server = server;
        this.player = player;
    }

    public boolean isFinished(){
        return lexer.isFinished();
    }

    private Token nextToken() throws ParseError{
        Optional<Token> token = lexer.nextToken();
        while (token.isEmpty()){
            if(lexer.isFinished())
                throw new ParseError("§6[§eMusic§6] §eEnde des Buches erreicht!");
            token = lexer.nextToken();
        }
        return token.get();
    }

    private Token parseTokenType(TokenType tokenType) throws ParseError{
        Token token = nextToken();

        while (token.getTokenType().equals(TokenType.WHITESPACE)){
            token = nextToken();
        }

        if(tokenType.equals(token.getTokenType()))
            return token;
        throw new ParseError("§6[§eMusic§6] §cVersucht einen " + tokenType + " aber bekam " + token.getTokenType() + '!');
    }

    private long parseLong() throws ParseError{
        return parseTokenType(TokenType.NUMBER).getNumber();
    }

    private String parseIdentifier() throws ParseError{
        return parseTokenType(TokenType.IDENTIFIER).getIdentifier();
    }

    private Note parseNote() throws ParseError{
        Token token = nextToken();
        boolean sharpened = false;
        if(token.getTokenType().equals(TokenType.SHARP)){
            sharpened = true;
            token = nextToken();
        }
        if(!token.getTokenType().equals(TokenType.NOTE)){
            throw new ParseError("§6[§eMusic§6] §cVersucht eine Note zu bekommen aber einen " + token.getTokenType() + " bekommen!");
        }

        return new Note(token.getOctave(),token.getNote(),sharpened);
    }

    private PlayNote parsePlayNote(Token firstToken) throws ParseError{
        Note note = parseNote();
        Instrument instrument = firstToken.getInstrument();
        return new PlayNote(server, player, note,instrument);
    }

    private Bundle parseBundle() throws ParseError{
        List<PlayNote> list = new LinkedList<>();
        while (!lexer.isFinished()){
            Token token = nextToken();
            switch (token.getTokenType()){
                case INSTRUMENT -> {
                    PlayNote playNote = parsePlayNote(token);
                    list.add(playNote);
                }
                case RBUNDLE -> {
                    return new Bundle(server,player,list);
                }
                default -> {
                    throw new ParseError("§6[§eMusic§6] §cVersucht etwas zu parsen was keine Note ist!");
                }
            }
        }
        throw new ParseError("§6[§eMusic§6] §cDu hast das Bundle nicht geschlossen.");
    }

    public Optional<MusicalAction> parseNextAction() throws ParseError{
        if (lexer.isFinished())
            return Optional.empty();

        Token firstToken = nextToken();
        TokenType tokenType = firstToken.getTokenType();

        switch (tokenType) {
            case IDENTIFIER -> {
                return Optional.of(new Identifier(server,player,firstToken.getIdentifier()));
            }
            case INSTRUMENT -> {
                return Optional.of(parsePlayNote(firstToken));
            }
            case WAIT -> {
                return Optional.of(new Wait(server,player, parseLong()));
            }
            case DEF -> {
                String identifier = parseIdentifier();
                Optional<MusicalAction> musicalActionOptional = parseNextAction();
                if(musicalActionOptional.isEmpty())
                    throw new ParseError("§6[§eMusic§6] §cFehlgeschlagen eine MusicalAction zu parsen!");
                MusicalAction musicalAction = musicalActionOptional.get();
                return Optional.of(new Define(server,player,identifier,musicalAction));
            }
            case LBUNDLE -> {
                return Optional.of(parseBundle());
            }
        }
        return Optional.empty();
    }
}
