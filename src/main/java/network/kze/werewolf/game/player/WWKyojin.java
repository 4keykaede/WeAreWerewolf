package network.kze.werewolf.game.player;

import network.kze.werewolf.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        yourName = ChatColor.GREEN + "村人";
    }

    @Override
    public void sendBar() {
    }
}
