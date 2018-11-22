package network.kze.werewolf.game.player;

import network.kze.werewolf.game.GameHelper;
import network.kze.werewolf.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WWJinro extends GamePlayer {

    private int barDelay = 15;
    private boolean showTip = false;
    private boolean isUpdate = false;

    public WWJinro(Player player) {
        super(player);

        isWolf = true;
    }

    @Override
    public void active() {
        player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.RED + "おまえは" + ChatColor.DARK_RED + " 人狼 " + ChatColor.RED + "として選ばれた！");
        player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.DARK_PURPLE + "狂人 " + ChatColor.RED + "と協力して村人を全滅させろ！");

        yourName = ChatColor.DARK_RED + "人狼";
    }

    @Override
    public void sendBar() {
        if (showTip) {
            PlayerUtil.sendActionBar(player, ChatColor.WHITE + "チャットの先頭に!を置くと人狼チームチャットになる");
            barDelay = barDelay - 2;
        } else {
            List<String> wolfs = new ArrayList<>();
            List<String> kyojins = new ArrayList<>();
            plugin.getGameManager().getGame().getWolfs(true).forEach(wolf -> wolfs.add(wolf.getPlayer().getPlayerListName()));
            plugin.getGameManager().getGame().getKyojin(true).forEach(kyojin -> kyojins.add(kyojin.getPlayer().getPlayerListName()));

            String actionBar = ChatColor.DARK_RED + "人狼" + Arrays.toString(wolfs.toArray()) + ChatColor.DARK_PURPLE + " 狂人";
            if (isUpdate) {
                actionBar = actionBar + Arrays.toString(kyojins.toArray());
            }
            PlayerUtil.sendActionBar(player, actionBar);

            barDelay--;
        }

        if (barDelay <= 0) {
            showTip = !showTip;
            barDelay = 15;
        }
    }
}
