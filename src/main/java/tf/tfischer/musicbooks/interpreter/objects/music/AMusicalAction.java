package tf.tfischer.musicbooks.interpreter.objects.music;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.stream.Stream;

public abstract class AMusicalAction implements MusicalAction{
    protected Server server;
    protected Player player;
    protected double DISTANCE = 30;

    public AMusicalAction(Server server, Player player) {
        this.server = server;
        this.player = player;
    }

    protected Stream<? extends Player> getPlayersInDistance(){
        Location location = player.getLocation();
        return server.getOnlinePlayers().stream()
                .filter(p   -> p.getWorld().equals(location.getWorld()) && p.getLocation().distance(location) <= DISTANCE);
    }
}
