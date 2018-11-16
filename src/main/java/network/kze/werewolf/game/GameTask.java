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
            gameManager.getGame().getGamePlayers().forEach(gamePlayer -> gamePlayer.sendBar());

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

        //ここどうにかしたい
        List<GamePlayer> gamePlayers = new ArrayList<>();
        int phase = 0;
        int count = 0;
        for (int i = 0; i < players.size(); i++) {
            if (phase == 0) {
                if (count == gameManager.getGame().getVillagerNum()) {
                    phase ++;
                    count = 0;
                } else {
                    WWVillager villager = new WWVillager(players.get(i));
                    gamePlayers.add(villager);
                    count ++;
                }
            }
            if (phase == 1) {
                if (count == gameManager.getGame().getYogenNum()) {
                    phase ++;
                    count = 0;
                } else {
                    WWVillager yogen = new WWVillager(players.get(i));
                    yogen.setJob(WWVillager.Job.YOGEN);
                    gamePlayers.add(yogen);
                    count ++;
                }
            }
            if (phase == 2) {
                if (count == gameManager.getGame().getReibaiNum()) {
                    phase ++;
                    count = 0;
                } else {
                    WWVillager reibai = new WWVillager(players.get(i));
                    reibai.setJob(WWVillager.Job.REIBAI);
                    gamePlayers.add(reibai);
                    count ++;
                }
            }
            if (phase == 3) {
                if (count == gameManager.getGame().getKyojinNum()) {
                    phase ++;
                    count = 0;
                } else {
                    GamePlayer kyojin = new WWKyojin(players.get(i));
                    gamePlayers.add(kyojin);
                    count ++;
                }
            }
            if (phase == 4) {
                if (count == gameManager.getGame().getJhinroNum()) {
                    phase ++;
                    count = 0;
                } else {
                    GamePlayer jinro = new WWJinro(players.get(i));
                    gamePlayers.add(jinro);
                    count ++;
                }
            }
        }

        gamePlayers.forEach(player -> {
            player.active();
            gameManager.getGame().addGamePlayer(player);
        });
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
