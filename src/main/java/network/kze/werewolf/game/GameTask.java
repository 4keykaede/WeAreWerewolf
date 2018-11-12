package network.kze.werewolf.game;

import network.kze.werewolf.game.player.GamePlayer;
import network.kze.werewolf.game.player.WWJinro;
import network.kze.werewolf.game.player.WWKyojin;
import network.kze.werewolf.game.player.WWVillager;
import network.kze.werewolf.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameTask {

    private final GameManager gameManager;

    private int PRETIME = 10;

    public GameTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void update() {
        //待機中
        if (gameManager.getGame().getPhase() == Game.Phase.WAITING) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                PlayerUtil.sendActionBar(player, GameHelper.getWaitingMessage());
            });
        }

        //準備期間
        if (gameManager.getGame().getPhase() == Game.Phase.PRESTATE) {
            if (PRETIME == 5) {
                castingPlayer();
            }

            if (PRETIME == 0) {
                Bukkit.getOnlinePlayers().forEach(players -> {
                    GameHelper.giveWeapon(players);
                });

                gameManager.getGame().setPhase(Game.Phase.PLAYING);
            }

            PRETIME--;
        }

        //ゲーム中
        if (gameManager.getGame().getPhase() == Game.Phase.PLAYING) {
            GameHelper.checkGameEnd(gameManager.getGame());
            if (gameManager.getGame().getResult() == Game.Result.VILLAGERS_WIN) {
                villagersWin();
                return;
            }

            if (gameManager.getGame().getResult() == Game.Result.WOLFS_WIN) {
                wolfsWin();
                return;
            }

            gameManager.getGame().addTime();
        }
    }

    private void castingPlayer() {
        List<Player> players = new ArrayList<>(gameManager.getGame().getJoinPlayers());
        Collections.shuffle(players, new Random());
        GamePlayer jhinro = new WWJinro(players.get(0));
        GamePlayer kyojin = new WWKyojin(players.get(1));
        WWVillager villager = new WWVillager(players.get(2));

        jhinro.active();
        kyojin.active();
        villager.setJob(WWVillager.Job.REIBAI);
        villager.active();

        gameManager.getGame().addGamePlayer(jhinro);
        gameManager.getGame().addGamePlayer(kyojin);
        gameManager.getGame().addGamePlayer(villager);
    }

    private void villagersWin() {
        gameManager.getGame().setPhase(Game.Phase.ENDING);
        Bukkit.broadcastMessage("village Win");
    }

    private void wolfsWin() {
        gameManager.getGame().setPhase(Game.Phase.ENDING);
        Bukkit.broadcastMessage("wolf win");
    }
}
