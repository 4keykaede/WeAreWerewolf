package network.kze.werewolf.commands;

import network.kze.werewolf.Werewolf;
import network.kze.werewolf.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WWCommand implements CommandExecutor {

    private final Werewolf plugin;

    public WWCommand(Werewolf plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only");
            return true;
        }

        Player player = (Player) sender;
        if (args.length <= 0) {
            return true;
        }

        if (args[0].equalsIgnoreCase("test")) {
            player.sendMessage("villager " + plugin.getGameManager().getGame().getVillagers().size());
            player.sendMessage("wolfs " + plugin.getGameManager().getGame().getWolfs().size());
        }

        if (args[0].equalsIgnoreCase("reset")) {
            plugin.getGameManager().resetGame();
        }

        if (args[0].equalsIgnoreCase("set")) {

            if (args[1].equalsIgnoreCase("all")) {
                Bukkit.getOnlinePlayers().forEach(plugin.getGameManager().getGame()::join);
                return true;
            }

            if (args[1].equalsIgnoreCase("spec")) {

                return true;
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("start")) {
            plugin.getGameManager().startGame();
            return true;
        }

        return true;
    }

}
