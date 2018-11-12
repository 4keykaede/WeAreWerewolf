package network.kze.werewolf.game.listener;

import lombok.RequiredArgsConstructor;
import network.kze.werewolf.game.GameManager;
import network.kze.werewolf.game.player.GamePlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class GameListener implements Listener {

    private final GameManager gameManager;

    @EventHandler
    public void onDead(PlayerDeathEvent event) {
        event.setDeathMessage("");

        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = event.getEntity();

        if (gameManager.getGame().isGamePlayer(player)) {
            GamePlayer gamePlayer = gameManager.getGame().getGamePlayer(player);
            gamePlayer.isDead = true;
        }
    }

    @EventHandler
    public void onHungry(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setFormat(ChatColor.GREEN + "%s" + ChatColor.DARK_GREEN + " 》" + ChatColor.GRAY + " %s");
    }


}
