package tf.tfischer.musicbooks.interpreter.objects.music;

import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.stream.Stream;

public class PlayNote extends AMusicalAction{
    private final Note note;
    private final Instrument instrument;

    public PlayNote(Server server, Player player, Note note, Instrument instrument) {
        super(server,player);
        this.note = note;
        this.instrument = instrument;
    }


    @Override
    public void execute(HashMap<String, MusicalAction> definitions) {
        Location location = player.getLocation();
        Stream<? extends Player> playerStream = getPlayersInDistance();
        playerStream.forEach(p -> p.playNote(location,instrument,note));
    }

    public void playerSound(Player player, Location location){
        player.playNote(location,instrument,note);
    }
}
