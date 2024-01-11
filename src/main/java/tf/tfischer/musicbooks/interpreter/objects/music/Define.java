package tf.tfischer.musicbooks.interpreter.objects.music;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Define extends AMusicalAction{
    String identifier;
    MusicalAction musicalAction;
    public Define(Server server, Player player, String identifier, MusicalAction musicalAction) {
        super(server, player);
        this.identifier = identifier;
        this.musicalAction = musicalAction;
    }

    @Override
    public void execute(HashMap<String, MusicalAction> definitions) throws InterruptedException {
        definitions.put(identifier,musicalAction);
    }
}
