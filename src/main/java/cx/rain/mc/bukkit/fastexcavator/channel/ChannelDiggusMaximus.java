package cx.rain.mc.bukkit.fastexcavator.channel;

import cx.rain.mc.bukkit.fastexcavator.FastExcavator;
import cx.rain.mc.bukkit.fastexcavator.excavator.Excavate;
import io.netty.buffer.Unpooled;
import org.bukkit.Location;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketDataSerializer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ChannelDiggusMaximus implements PluginMessageListener {
    public final static String CHANNEL_NAME = "diggusmaximus:start_excavate_packet";

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (channel != CHANNEL_NAME) {
            return;
        }

        PacketDataSerializer buf = new PacketDataSerializer(Unpooled.wrappedBuffer(message));
        BlockPosition blockPos = buf.e();
        EntityPlayer playerEntity = ((CraftPlayer) player).getHandle();

        // Todo: Server configurations (is enabled).
        if (blockPos.a(playerEntity.getPositionVector(), 10)) {
            Excavate excavate = new Excavate(player, blockPos, playerEntity);
            excavate.start();
        }
    }

    public static void sendExcavatePacket(Player player, BlockPosition pos) {
        PacketDataSerializer buf = new PacketDataSerializer(Unpooled.buffer());
        buf.a(pos);
        player.sendPluginMessage(FastExcavator.getInstance(), CHANNEL_NAME, buf.array());
    }
}
