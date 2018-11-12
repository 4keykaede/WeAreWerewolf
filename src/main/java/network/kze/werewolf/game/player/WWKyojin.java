package network.kze.werewolf.game.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WWKyojin extends GamePlayer {

    public WWKyojin(Player player) {
        super(player);
        isWolf = false;
        isNoCount = true;
    }

    @Override
    public void active() {
        player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.RED + "おなたは" + ChatColor.DARK_PURPLE + " 狂人 " + ChatColor.RED + "として選ばれてしまった！");
        player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.DARK_RED + "人狼 " + ChatColor.RED + "と協力して村人を全滅させよう！");
    }
}
