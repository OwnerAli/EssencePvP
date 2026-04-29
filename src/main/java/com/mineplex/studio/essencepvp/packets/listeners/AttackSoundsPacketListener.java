package com.mineplex.studio.essencepvp.packets.listeners;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSoundEffect;

public class AttackSoundsPacketListener implements PacketListener {

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.SOUND_EFFECT) return;
        WrapperPlayServerSoundEffect soundPacket = new WrapperPlayServerSoundEffect(event);

        if (!soundPacket.getSound().getName()
                .toString()
                .contains("sweep")) return;
        event.setCancelled(true);
    }

}
