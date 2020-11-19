package cx.rain.mc.bukkit.fastexcavator.channel;

import cx.rain.mc.bukkit.fastexcavator.FastExcavator;
import cx.rain.mc.bukkit.fastexcavator.network.PacketByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ChannelDiggusMaximus implements PluginMessageListener {
    public final static String CHANNEL_NAME = "diggusmaximus:start_excavate_packet";

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (channel != CHANNEL_NAME) {
            return;
        }

        //PacketByteBuf buf = new PacketByteBuf(Unpooled.wrappedBuffer(message));

        player.sendPluginMessage(FastExcavator.getInstance(), CHANNEL_NAME, message);
    }
}
