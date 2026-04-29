package com.mineplex.studio.essencepvp.packets.listeners;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.particle.type.ParticleTypes;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerParticle;

public class SweepParticlePacketListener implements PacketListener {

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.PARTICLE) return;
        WrapperPlayServerParticle particlePacket = new WrapperPlayServerParticle(event);

        if (!particlePacket.getParticle().getType()
                .equals(ParticleTypes.SWEEP_ATTACK)) return;
        event.setCancelled(true);
    }

}
