package tf.tfischer.musicbooks.interpreter.objects.music;

import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.HashMap;

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
        server.getOnlinePlayers().stream()
                .filter(p   -> p.getWorld().equals(location.getWorld()) && p.getLocation().distance(location) <= DISTANCE)
                .forEach(p -> p.playNote(location,instrument,note));
    }

    public void playerSound(Player player, Location location){
        player.playNote(location,instrument,note);
    }
}
