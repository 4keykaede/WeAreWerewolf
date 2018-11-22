package network.kze.werewolf.game;

import lombok.Data;
import network.kze.werewolf.game.player.GamePlayer;
import network.kze.werewolf.game.player.WWVillager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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

    public int villagerNum = 0;
    public int yogenNum = 0;
    public int reibaiNum = 0;
    public int kyojinNum = 0;
    public int jhinroNum = 0;

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
        player.setGameMode(GameMode.SURVIVAL);

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

    public Collection<GamePlayer> getVillagers(boolean netabare) {
        if (netabare) {
            return getGamePlayers().stream().filter(gamePlayer -> !gamePlayer.isWolf && !gamePlayer.isNoCount).collect(Collectors.toList());
        }
        return getGamePlayers().stream().filter(gamePlayer -> !gamePlayer.isWolf && !gamePlayer.isDead && !gamePlayer.isNoCount).collect(Collectors.toList());
    }

    public Collection<GamePlayer> getYogens(boolean netabare) {
        if (netabare) {
            return getGamePlayers().stream().filter(gamePlayer -> !gamePlayer.isWolf && !gamePlayer.isNoCount && gamePlayer.getYourName().equals(ChatColor.GOLD + "預言者")).collect(Collectors.toList());
        }
        return getGamePlayers().stream().filter(gamePlayer -> !gamePlayer.isWolf && !gamePlayer.isDead && !gamePlayer.isNoCount && gamePlayer.getYourName().equals(ChatColor.GOLD + "預言者")).collect(Collectors.toList());
    }

    public Collection<GamePlayer> getReibais(boolean netabare) {
        if (netabare) {
            return getGamePlayers().stream().filter(gamePlayer -> !gamePlayer.isWolf && !gamePlayer.isNoCount && gamePlayer.getYourName().equals(ChatColor.DARK_AQUA + "霊媒師")).collect(Collectors.toList());
        }
        return getGamePlayers().stream().filter(gamePlayer -> !gamePlayer.isWolf && !gamePlayer.isDead && !gamePlayer.isNoCount && gamePlayer.getYourName().equals(ChatColor.DARK_AQUA + "霊媒師")).collect(Collectors.toList());
    }

    public Collection<GamePlayer> getWolfs(boolean netabare) {
        if (netabare) {
            return getGamePlayers().stream().filter(gamePlayer -> gamePlayer.isWolf && !gamePlayer.isNoCount).collect(Collectors.toList());
        }
        return getGamePlayers().stream().filter(gamePlayer -> gamePlayer.isWolf && !gamePlayer.isDead && !gamePlayer.isNoCount).collect(Collectors.toList());
    }

    public Collection<GamePlayer> getKyojin(boolean netabare) {
        if (netabare) {
            return getGamePlayers().stream().filter(gamePlayer -> !gamePlayer.isWolf && gamePlayer.isNoCount).collect(Collectors.toList());
        }
        return getGamePlayers().stream().filter(gamePlayer -> !gamePlayer.isWolf && !gamePlayer.isDead && gamePlayer.isNoCount).collect(Collectors.toList());
    }


}
