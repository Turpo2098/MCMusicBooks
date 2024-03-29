package tf.tfischer.musicbooks.interpreter.objects.music;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Wait extends AMusicalAction{
    long time;
    public Wait(Server server, Player player, long time) {
        super(server,player);
        this.time = time;
    }

    @Override
    public void execute(HashMap<String, MusicalAction> definitions) throws InterruptedException {
        Thread.sleep(time);
    }
}
