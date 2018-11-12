package network.kze.werewolf.game.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WWJinro extends GamePlayer {

    public WWJinro(Player player) {
        super(player);
        isWolf = true;
    }

    @Override
    public void active() {
        player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.RED + "おまえは" + ChatColor.DARK_RED + " 人狼 " + ChatColor.RED + "として選ばれた！");
        player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.DARK_PURPLE + "狂人 " + ChatColor.RED + "と協力して村人を全滅させろ！");
    }
}
