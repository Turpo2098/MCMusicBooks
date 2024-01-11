package tf.tfischer.musicbooks.interpreter.commands;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tf.tfischer.musicbooks.interpreter.Story;

import java.util.ArrayList;
import java.util.List;

public class PlayCommand implements CommandExecutor, TabCompleter {

    Server server;

    public PlayCommand(Server server) {
        this.server = server;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player player)){
            commandSender.sendMessage("You need to be a player");
            return true;
        }

        System.out.println(player.getName());
        new Story(player, server).start();
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return new ArrayList<>();
    }
}
