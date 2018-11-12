package network.kze.werewolf.game.player;

import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WWVillager extends GamePlayer {

    @Setter public Job job = Job.NORMAL;

    public WWVillager(Player player) {
        super(player);
        isWolf = false;
    }

    @Override
    public void active() {
        if (job == Job.NORMAL) {
            player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.GRAY + "あなたは" + ChatColor.GREEN + " 村人 " + ChatColor.GRAY + "として選ばれました。");
        }
        if (job == Job.YOGEN) {
            player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.GRAY + "あなたは" + ChatColor.GOLD + " 預言者 " + ChatColor.GRAY + "として選ばれました。");
            player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.GRAY + "誰か一人の役職を知ることができます。");
        }
        if (job == Job.REIBAI) {
            player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.GRAY + "あなたは" + ChatColor.DARK_AQUA + " 霊媒師 " + ChatColor.GRAY + "として選ばれました。");
            player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.GRAY + "既に死んでいる人の役職を一人だけ知ることができます。");
        }
    }

    public enum Job {
        NORMAL, YOGEN, REIBAI
    }
}
