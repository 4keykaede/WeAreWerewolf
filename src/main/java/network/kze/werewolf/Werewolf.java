package network.kze.werewolf;

import lombok.Getter;
import network.kze.werewolf.commands.WWCommand;
import network.kze.werewolf.game.GameManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Werewolf extends JavaPlugin {

    @Getter private static Werewolf instance;

    @Getter private GameManager gameManager;

    @Getter private final PluginManager pluginManager = getServer().getPluginManager();

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        gameManager = new GameManager(this);

        getCommand("ww").setExecutor(new WWCommand(this));
    }

    public BukkitTask runTaskTimer(final Runnable run, final long delay, final long period) {
        return getServer().getScheduler().runTaskTimer(this, run, delay, period);
    }

}
