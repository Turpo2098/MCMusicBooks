package tf.tfischer.musicbooks.interpreter.objects.music;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Identifier extends AMusicalAction{
    String identifier;
    MusicalAction musicalAction;
    public Identifier(Server server, Player player, String identifier) {
        super(server, player);
        this.identifier = identifier;
    }

    @Override
    public void execute(HashMap<String, MusicalAction> definitions) throws InterruptedException {
        musicalAction = definitions.get(identifier);

        if(musicalAction == null)
            return;

        musicalAction.execute(definitions);
    }
}
