package network.kze.werewolf.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

@UtilityClass
public class PlayerUtil {

    public void sendActionBar(@NonNull Player player, @NonNull String message) {
        PacketUtil.getActionBar(message).sendPacket(player);
    }
}
