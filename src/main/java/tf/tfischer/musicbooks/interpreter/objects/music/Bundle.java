package tf.tfischer.musicbooks.interpreter.objects.music;

import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class Bundle extends AMusicalAction{
    List<PlayNote> playNotes;
    public Bundle(Server server, Player player, List<PlayNote> playNotes) {
        super(server, player);
        this.playNotes = playNotes;
    }

    @Override
    public void execute(HashMap<String, MusicalAction> definitions) {
        Location location = player.getLocation();
        getPlayersInDistance().forEach(p -> {
            for(PlayNote playNote : playNotes){
                playNote.playerSound(p,location);
            }
        });
    }
}
