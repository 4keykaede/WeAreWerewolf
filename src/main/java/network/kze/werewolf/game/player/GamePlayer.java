package network.kze.werewolf.game.player;

import lombok.Getter;
import lombok.NonNull;
import network.kze.werewolf.Werewolf;
import org.bukkit.entity.Player;

public abstract class GamePlayer {

    protected final Werewolf plugin = Werewolf.getInstance();

    @Getter public String yourName;

    @Getter protected final Player player;

    public boolean isDead = false;
    public boolean isWolf;
    public boolean isNoCount = false;

    public GamePlayer(@NonNull final Player player) {
        this.player = player;
    }


    public abstract void active();

    public abstract void sendBar();

}
