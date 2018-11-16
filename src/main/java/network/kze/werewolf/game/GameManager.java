package network.kze.werewolf.game;

import lombok.Getter;
import network.kze.werewolf.Werewolf;
import network.kze.werewolf.game.listener.GameListener;
import network.kze.werewolf.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameManager {

    @Getter private final Werewolf plugin;

    @Getter public Game game;
    @Getter public GameTask gameTask;

    public GameManager(Werewolf plugin) {
        this.plugin = plugin;

        setUp();
        this.plugin.getPluginManager().registerEvents(new GameListener(this), plugin);

        this.plugin.runTaskTimer(this::update, 0L, 20L);
    }

    public void startGame() {
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "浪人 》" + ChatColor.GRAY + "間もなくゲームが開始されます。");
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

}
