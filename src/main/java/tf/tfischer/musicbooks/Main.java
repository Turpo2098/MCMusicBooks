package tf.tfischer.musicbooks;

import org.bukkit.plugin.java.JavaPlugin;
import tf.tfischer.musicbooks.interpreter.commands.PlayCommand;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("music").setExecutor(new PlayCommand(getServer()));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
