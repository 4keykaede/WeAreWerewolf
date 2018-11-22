package network.kze.werewolf.game.listener;

import lombok.RequiredArgsConstructor;
import network.kze.werewolf.game.GameManager;
import network.kze.werewolf.game.player.GamePlayer;
import network.kze.werewolf.game.player.WWJinro;
import network.kze.werewolf.game.player.WWVillager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;

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
    public void onClickSupportItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!gameManager.isGame()) {
            return;
        }

        if (!gameManager.getGame().isGamePlayer(player)) {
            return;
        }

        if (!event.hasItem() || (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (event.getItem().getType() != Material.SCUTE && event.getItem().getItemMeta().getDisplayName().equals("モンスターエナジー")) {
            return;
        }

        GamePlayer gamePlayer = gameManager.getGame().getGamePlayer(player);

        if (gamePlayer instanceof WWVillager) {
            WWVillager villager = (WWVillager) gamePlayer;
            if (villager.getJob() == WWVillager.Job.NORMAL) {
                player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.DARK_RED + "預言者・霊媒師・人狼のみが使用できます！");
                return;
            }

            if (villager.getJob() == WWVillager.Job.YOGEN) {

            }

            if (villager.getJob() == WWVillager.Job.REIBAI) {

            }
        }

        if (gamePlayer instanceof WWJinro) {

        }

        player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.DARK_RED + "預言者・霊媒師・人狼のみが使用できます！");
        return;
    }


    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (gameManager.getGame().isGamePlayer(player)) {
            event.setRespawnLocation(player.getLocation());
            player.setGameMode(GameMode.SPECTATOR);
        }
    }

    @EventHandler
    public void onHungry(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
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
            gameManager.getGame().getWolfs(false).forEach(wolf -> wolf.getPlayer().sendMessage(
                    ChatColor.DARK_RED + "人狼チャット " + ChatColor.GREEN + player.getName() + ChatColor.DARK_GREEN + " 》" + ChatColor.GRAY + " " + event.getMessage().substring(1)));
            event.setCancelled(true);
        } else {
            event.setFormat(ChatColor.GREEN + "%s" + ChatColor.DARK_GREEN + " 》" + ChatColor.GRAY + " %s");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.GREEN + "浪人 》" + ChatColor.GRAY + "https://github.com/4keykaede/WeAreWerewolf");
    }


}
