package network.kze.werewolf.util;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import network.kze.werewolf.packetwrapper.AbstractPacket;
import network.kze.werewolf.packetwrapper.WrapperPlayServerChat;

@UtilityClass
public class PacketUtil {

    public AbstractPacket getActionBar(@NonNull String message) {
        WrapperPlayServerChat packet = new WrapperPlayServerChat();
        packet.setChatType(EnumWrappers.ChatType.GAME_INFO);
        packet.setMessage(WrappedChatComponent.fromText(message));
        return packet;
    }
}
