package tf.tfischer.musicbooks.interpreter.objects.music;

import java.util.HashMap;

public interface MusicalAction {
    void execute(HashMap<String,MusicalAction> definitions) throws InterruptedException;
}
