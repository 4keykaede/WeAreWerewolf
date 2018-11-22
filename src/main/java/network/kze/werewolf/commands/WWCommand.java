package network.kze.werewolf.commands;

import network.kze.werewolf.Werewolf;
import network.kze.werewolf.game.Game;
import network.kze.werewolf.game.GameHelper;
import network.kze.werewolf.game.player.GamePlayer;
import network.kze.werewolf.game.player.WWVillager;
import network.kze.werewolf.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WWCommand implements CommandExecutor {

    private final Werewolf plugin;

    public WWCommand(Werewolf plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only");
            return true;
        }

        Player player = (Player) sender;
        if (args.length <= 0) {
            return true;
        }

        if (args[0].equalsIgnoreCase("yogen")) {
            if (!plugin.getGameManager().isGame()) {
                return true;
            }

            GamePlayer gamePlayer = plugin.getGameManager().getGame().getGamePlayer(player);
            if (gamePlayer instanceof WWVillager) {
                WWVillager villager = (WWVillager) gamePlayer;
                if (villager.getJob() != WWVillager.Job.YOGEN) {
                    player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.DARK_RED + "預言者のみ使用できるコマンドだ！");
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null || !plugin.getGameManager().getGame().isGamePlayer(target)) {
                    player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.DARK_RED + "ターゲットが見つからなかった！");
                    return true;
                }

                if (villager.isUseCommand()) {
                    player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.DARK_RED + "既にコマンドは使われている！");
                    return true;
                }
                villager.setUseCommand(true);

                player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.DARK_RED + target.getName() + ChatColor.RED + " は " + plugin.getGameManager().getGame().getGamePlayer(target).getYourName());
            }
        }

        if (args[0].equalsIgnoreCase("reibai")) {
            if (!plugin.getGameManager().isGame()) {
                return true;
            }

            GamePlayer gamePlayer = plugin.getGameManager().getGame().getGamePlayer(player);
            if (gamePlayer instanceof WWVillager) {
                WWVillager villager = (WWVillager) gamePlayer;
                if (villager.getJob() != WWVillager.Job.REIBAI) {
                    player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.DARK_RED + "霊媒師のみ使用できるコマンドだ！");
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null || !plugin.getGameManager().getGame().isGamePlayer(target)) {
                    player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.DARK_RED + "ターゲットが見つからなかった！");
                    return true;
                }

                GamePlayer targetPlayer = plugin.getGameManager().getGame().getGamePlayer(target);

                if (villager.isUseCommand()) {
                    player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.DARK_RED + "既にコマンドは使われている！");
                    return true;
                }

                villager.setUseCommand(true);
                if (targetPlayer.isDead) {
                    player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.DARK_RED + target.getName() + ChatColor.RED + " は既に死んでいた！");
                    if (targetPlayer.isWolf) {
                        player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.RED + " 判定は... " + ChatColor.DARK_GRAY + "黒");
                    } else {
                        player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.RED + " 判定は... " + ChatColor.WHITE + "白");
                    }
                } else {
                    player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.DARK_RED + target.getName() + ChatColor.RED + " はまだ生きているぞ！");
                }
            }

        }

        if (!sender.isOp()) {
            return true;
        }

        if (args[0].equalsIgnoreCase("test")) {
            player.sendMessage("villager " + plugin.getGameManager().getGame().getVillagers(false).size());
            player.sendMessage("wolfs " + plugin.getGameManager().getGame().getWolfs(false).size());
            Game game = plugin.getGameManager().getGame();
            player.sendMessage("Village " + game.getVillagerNum());
            player.sendMessage("Yogen " + game.getYogenNum());
            player.sendMessage("Reibai " + game.getReibaiNum());
            player.sendMessage("Kyojin " + game.getKyojinNum());
            player.sendMessage("Jinro " + game.getJhinroNum());
        }

        if (args[0].equalsIgnoreCase("test2")) {

        }

        if (args[0].equalsIgnoreCase("test3")) {
            player.getInventory().setItem(0, GameHelper.ITEM_SPLASH);
            player.getInventory().setItem(1, GameHelper.ITEM_LOD);
            player.getInventory().setItem(2, GameHelper.ITEM_PICKAXE);
        }

        if (args[0].equalsIgnoreCase("reset")) {
            plugin.getGameManager().resetGame();
            Bukkit.getOnlinePlayers().forEach(resetPlayer -> resetPlayer.teleport(new Location(Bukkit.getWorld("world"), -104, 38, 693)));
        }

        if (args[0].equalsIgnoreCase("set")) {

            if (args[1].equalsIgnoreCase("all")) {
                Bukkit.getOnlinePlayers().forEach(plugin.getGameManager().getGame()::join);
                return true;
            }

            if (args[1].equalsIgnoreCase("spec")) {

                return true;
            }

            if (args[1].equalsIgnoreCase("num")) {
                if (args.length <= 5) {
                    player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.GRAY + "/ww set num [Village] [Yogen] [Reibai] [Kyojin] [Jhinro]");
                    return true;
                }
                Game game = plugin.getGameManager().getGame();
                game.setVillagerNum(Integer.parseInt(args[2]));
                game.setYogenNum(Integer.parseInt(args[3]));
                game.setReibaiNum(Integer.parseInt(args[4]));
                game.setKyojinNum(Integer.parseInt(args[5]));
                game.setJhinroNum(Integer.parseInt(args[6]));
                player.sendMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.GRAY + "Change it!");
                return true;
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("get")) {

            if (args[1].equalsIgnoreCase("tool")) {
                ItemStack tool = new ItemStack(Material.STICK);
                ItemMeta toolMeta = tool.getItemMeta();
                toolMeta.setDisplayName("スポーンポイント");
                tool.setItemMeta(toolMeta);
                player.getInventory().setItem(0, tool);
                return true;
            }

        }

        if (args[0].equalsIgnoreCase("start")) {
            plugin.getGameManager().startGame();
            return true;
        }

        return true;
    }

}
