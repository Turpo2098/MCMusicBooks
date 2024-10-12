package tf.tfischer.musicbooks.interpreter;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import tf.tfischer.musicbooks.interpreter.objects.music.MusicalAction;

import java.util.*;

public class Story extends Thread{
    public boolean stoping = false;
    LazyParser lazyParser;
    ItemStack itemStack;
    Player player;
    Server server;
    Map<Player,Story> activePlayers;

    public Story(Player player, Server server, Map<Player,Story> activePlayers) {
        this.player         = player;
        this.server         = server;
        this.activePlayers  = activePlayers;

        itemStack = player.getInventory().getItemInMainHand();

        if(!isBook(itemStack.getType())){
            player.sendMessage("You need to hav a book in your MainHand.");
            return;
        }

        BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();

        if(bookMeta == null){
            player.sendMessage("You can't read the pages!");
            return;
        }

        List<String> text = bookMeta.getPages();
        lazyParser = new LazyParser(server,text,player);
    }

    private boolean hasSameBookInHand(){
        return player.getInventory().getItemInMainHand().equals(itemStack);
    }

    private boolean isBook(Material material){
        return material.equals(Material.WRITTEN_BOOK) || material.equals(Material.WRITABLE_BOOK);
    }

    private void removePlayer(){
        activePlayers.remove(player);
    }

    @Override
    public void run() {
        if(!hasSameBookInHand()){
            removePlayer();
            return;
        }
        HashMap<String,MusicalAction> definitions = new HashMap<>();

        while(!stoping && !lazyParser.isFinished()) {
            try {
                Optional<MusicalAction> musicalActionOptional = lazyParser.parseNextAction();
                if (musicalActionOptional.isEmpty()) {
                    player.sendMessage("§6Ended the music because it failed to parse something..");
                    removePlayer();
                    return;
                }

                MusicalAction musicalAction = musicalActionOptional.get();
                musicalAction.execute(definitions);

            } catch (ParseError | InterruptedException e) {
                player.sendMessage(e.getMessage());
                removePlayer();
                return;
            }
        }
        removePlayer();
    }
}
