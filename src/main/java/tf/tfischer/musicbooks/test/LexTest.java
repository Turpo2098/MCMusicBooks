package tf.tfischer.musicbooks.test;

import tf.tfischer.musicbooks.interpreter.LazyLexer;

import java.util.List;

public class LexTest {
    public static void main(String[] args){
        String page1, page2, page3;
        page1 = "< Hallo jsadh A B C D E F G #D";
        page2 = ">>>SS<s";
        page3 = "sda as";
        List<String> book = List.of(page1,page2,page3);
        LazyLexer lexer = new LazyLexer(book);
        while (!lexer.isFinished()){
            System.out.println(lexer.nextToken());
        }
    }
}
