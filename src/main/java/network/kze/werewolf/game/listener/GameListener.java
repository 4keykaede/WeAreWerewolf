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
import org.bukkit.event.player.PlayerJoinEvent;
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

        if (!gameManager.isGame() || !gameManager.getGame().isGamePlayer(event.getPlayer())) {
            event.setFormat(ChatColor.GREEN + "%s" + ChatColor.DARK_GREEN + " 》" + ChatColor.GRAY + " %s");
        }

        if (!gameManager.getGame().getGamePlayer(player).isWolf) {
            event.setFormat(ChatColor.GREEN + "%s" + ChatColor.DARK_GREEN + " 》" + ChatColor.GRAY + " %s");
        }

        if (event.getMessage().startsWith("!") || event.getMessage().startsWith("！")) {
            gameManager.getGame().getWolfs().forEach(wolf -> wolf.getPlayer().sendMessage(
                    ChatColor.DARK_RED + "人狼チャット " +ChatColor.GREEN + player.getName() + ChatColor.DARK_GREEN + " 》" + ChatColor.GRAY + " " + event.getMessage().substring(1)));
            event.setCancelled(true);
        } else {
            event.setFormat(ChatColor.GREEN + "%s" + ChatColor.DARK_GREEN + " 》" + ChatColor.GRAY + " %s");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.GREEN + "浪人 》" + ChatColor.GRAY + "OSS 人狼プラグイン -> https://github.com/4keykaede/WeAreWerewolf");
    }


}
