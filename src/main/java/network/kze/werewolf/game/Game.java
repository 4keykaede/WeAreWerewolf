package network.kze.werewolf.game;

import lombok.Data;
import network.kze.werewolf.game.player.GamePlayer;
import network.kze.werewolf.game.player.WWVillager;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class Game {

    private final GameManager gameManager;

    public Phase phase = Phase.WAITING;
    public Result result = Result.NONE;

    public int time = 0;

    private final Map<UUID, Player> joinPlayers = new HashMap<>();
    private final Map<UUID, GamePlayer> gamePlayers = new HashMap<>();

    public Game(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void join(Player player) {
        joinPlayers.put(player.getUniqueId(), player);
    }

    public void addGamePlayer(GamePlayer gamePlayer) {
        gamePlayers.put(gamePlayer.getPlayer().getUniqueId(), gamePlayer);
    }

    public void leave(Player player) {
        player.getInventory().clear();

        joinPlayers.remove(player.getUniqueId());
        gamePlayers.remove(player.getUniqueId());
    }

    public boolean isGamePlayer(Player player) {
        return gamePlayers.containsKey(player.getUniqueId());
    }


    public void addTime() {
        time++;
    }

    public enum Phase {

        WAITING, PRESTATE, PLAYING, ENDING
    }

    public enum Result {

        VILLAGERS_WIN, WOLFS_WIN, NONE
    }

    public GamePlayer getGamePlayer(Player player) {
        if (!isGamePlayer(player)) {
            return null;
        }

        return gamePlayers.get(player.getUniqueId());
    }

    public Collection<Player> getJoinPlayers() {
        return joinPlayers.values();
    }

    public Collection<GamePlayer> getGamePlayers() {
        return gamePlayers.values();
    }

    public Collection<GamePlayer> getVillagers() {
        return getGamePlayers().stream().filter(gamePlayer -> !gamePlayer.isWolf && !gamePlayer.isDead && !gamePlayer.isNoCount).collect(Collectors.toList());
    }

    public Collection<GamePlayer> getWolfs() {
        return getGamePlayers().stream().filter(gamePlayer -> gamePlayer.isWolf && !gamePlayer.isDead && !gamePlayer.isNoCount).collect(Collectors.toList());
    }


}
