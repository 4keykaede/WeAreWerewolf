package network.kze.werewolf.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import network.kze.werewolf.Werewolf;
import network.kze.werewolf.packetwrapper.WrapperPlayServerPlayerInfo;
import org.bukkit.Bukkit;

import java.util.Collections;

public class PacketListener extends PacketAdapter {

    public PacketListener(Werewolf plugin) {
        super(plugin, PacketType.Play.Server.PLAYER_INFO);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if (event.getPacketType().equals(PacketType.Play.Server.PLAYER_INFO)) {
            if (event.getPacket().getPlayerInfoAction().read(0) != EnumWrappers.PlayerInfoAction.UPDATE_GAME_MODE) {
                return;
            }

            PlayerInfoData pid = event.getPacket().getPlayerInfoDataLists().read(0).get(0);
            PlayerInfoData newPid = new PlayerInfoData(pid.getProfile(), pid.getPing(), EnumWrappers.NativeGameMode.SURVIVAL, pid.getDisplayName());
            event.getPacket().getPlayerInfoDataLists().write(0, Collections.singletonList(newPid));
        }
    }
}

