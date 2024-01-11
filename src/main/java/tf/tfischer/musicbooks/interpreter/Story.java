package tf.tfischer.musicbooks.interpreter;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import tf.tfischer.musicbooks.interpreter.objects.music.MusicalAction;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Story extends Thread{
    LazyParser lazyParser;
    ItemStack itemStack;
    Player player;
    Server server;

    public Story(Player player, Server server) {
        this.player = player;
        this.server = server;

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

    @Override
    public void run() {
        if(!hasSameBookInHand()){
            interrupt();
            return;
        }
        HashMap<String,MusicalAction> definitions = new HashMap<>();

        while(!lazyParser.isFinished()) {
            try {
                Optional<MusicalAction> musicalActionOptional = lazyParser.parseNextAction();
                if (musicalActionOptional.isEmpty()) {
                    player.sendMessage("§6Ended the Music.");
                    return;
                }

                MusicalAction musicalAction = musicalActionOptional.get();
                musicalAction.execute(definitions);

            } catch (ParseError | InterruptedException e) {
                player.sendMessage(e.getMessage());
                System.out.println("Ende");
                return;
            }
        }

        System.out.println("Ende");
    }
}
