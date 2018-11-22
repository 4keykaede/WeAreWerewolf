package network.kze.werewolf.game;

import lombok.Getter;
import network.kze.werewolf.Werewolf;
import network.kze.werewolf.game.listener.GameListener;
import network.kze.werewolf.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class GameManager {

    @Getter
    private final Werewolf plugin;

    @Getter
    public Game game;
    @Getter
    public GameTask gameTask;

    public GameManager(Werewolf plugin) {
        this.plugin = plugin;

        setUp();
        this.plugin.getPluginManager().registerEvents(new GameListener(this), plugin);

        this.plugin.runTaskTimer(this::update, 0L, 20L);
    }

    public void startGame() {
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.GRAY + "間もなくゲームが開始されます。");
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.DARK_PURPLE + "---=== " + ChatColor.LIGHT_PURPLE + "配分" + ChatColor.DARK_PURPLE + " ===--- ");
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.WHITE + "村人 : " + ChatColor.GREEN + getGame().getVillagerNum());
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.WHITE + "預言者 : " + ChatColor.GREEN + getGame().getYogenNum());
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.WHITE + "霊媒師 : " + ChatColor.GREEN + getGame().getReibaiNum());
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "浪人 》");
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.WHITE + "狂人 : " + ChatColor.GREEN + getGame().getKyojinNum());
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.WHITE + "人狼 : " + ChatColor.GREEN + getGame().getJhinroNum());
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.DARK_PURPLE + "---===   ===--- ");

        startEffect();


        getGame().setPhase(Game.Phase.PRESTATE);
        GameHelper.tpStage(this);
    }

    public void resetGame() {
        if (game == null) {
            return;
        }

        new ArrayList<>(game.getGamePlayers()).forEach(gamePlayer -> {
            game.leave(gamePlayer.getPlayer());
        });

        this.game = null;
        this.gameTask = null;

        setUp();
    }

    public boolean isGame() {
        if (plugin.getGameManager().getGame() == null) {
            return false;
        }

        if (plugin.getGameManager().getGame().getPhase() != Game.Phase.PLAYING) {
            return false;
        }

        return true;
    }

    void setUp() {
        this.game = new Game(this);
        this.gameTask = new GameTask(this);
    }

    void update() {
        if (game == null) {
            return;
        }

        gameTask.update();
    }

    private void startEffect() {
        getGame().getJoinPlayers().forEach(gamePlayer -> {
            gamePlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (3 * 20), 1));
            gamePlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (3 * 20), 99));
            gamePlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (3 * 20), 130));
        });
    }

}
