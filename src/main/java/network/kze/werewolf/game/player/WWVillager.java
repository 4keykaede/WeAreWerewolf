package network.kze.werewolf.game.player;

import lombok.Data;
import lombok.Setter;
import network.kze.werewolf.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;

@Data
public class WWVillager extends GamePlayer {

    public Job job = Job.NORMAL;

    public WWVillager(Player player) {
        super(player);

        isWolf = false;
    }

    @Override
    public void active() {
        if (job == Job.NORMAL) {
            player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.GRAY + "あなたは" + ChatColor.GREEN + " 村人 " + ChatColor.GRAY + "として選ばれました。");
            yourName = ChatColor.GREEN + "村人";
        }
        if (job == Job.YOGEN) {
            player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.GRAY + "あなたは" + ChatColor.GOLD + " 預言者 " + ChatColor.GRAY + "として選ばれました。");
            player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.GRAY + "誰か一人の役職を知ることができます。");
            yourName = ChatColor.GOLD + "預言者";
        }
        if (job == Job.REIBAI) {
            player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.GRAY + "あなたは" + ChatColor.DARK_AQUA + " 霊媒師 " + ChatColor.GRAY + "として選ばれました。");
            player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.GRAY + "既に死んでいる人の役職を一人だけ知ることができます。");
            yourName = ChatColor.DARK_AQUA + "霊媒師";
        }
    }

    @Override
    public void sendBar() {
        if (job == Job.YOGEN) {
            PlayerUtil.sendActionBar(player, ChatColor.DARK_GRAY + "/ww yogen <PlayerID>");
        }

        if (job == Job.REIBAI) {
            PlayerUtil.sendActionBar(player, ChatColor.DARK_GRAY + "/ww reibai <PlayerID>");
        }
    }

    public enum Job {
        NORMAL, YOGEN, REIBAI
    }
}
