package tf.tfischer.musicbooks.interpreter.commands;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tf.tfischer.musicbooks.interpreter.Story;

import java.util.*;

public class PlayCommand implements CommandExecutor, TabCompleter {

    Server server;

    public PlayCommand(Server server) {
        this.server = server;
    }

    Set<Player> activePlayers = new HashSet<>();

    private boolean isActive(Player player){
        return activePlayers.contains(player);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player player)){
            commandSender.sendMessage("§6[§eMusic§6] §eDu musst ein Spieler sein.");
            return true;
        }
        Material material = player.getInventory().getItemInMainHand().getType();
        if(!(material.equals(Material.WRITABLE_BOOK) || material.equals(Material.WRITTEN_BOOK))){
            player.sendMessage("§6[§eMusic§6] §eDu musst ein Buch in der Hand halten.");
            return true;
        }

        if(isActive(player)){
            player.sendMessage("§6[§eMusic§6] §eDeine Musik spielt bereits.");
            return true;
        }

        activePlayers.add(player);

        new Story(player, server,activePlayers).start();
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return new ArrayList<>();
    }
}
