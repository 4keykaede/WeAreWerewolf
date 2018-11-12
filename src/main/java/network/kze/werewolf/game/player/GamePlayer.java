package network.kze.werewolf.game.player;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;

public abstract class GamePlayer {

    @Getter protected final Player player;

    public boolean isDead = false;
    public boolean isWolf;
    public boolean isNoCount = false;

    public GamePlayer(@NonNull final Player player) {
        this.player = player;
    }


    public abstract void active();

}
