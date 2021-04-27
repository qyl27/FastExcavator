package cx.rain.mc.bukkit.fastexcavator.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.nio.charset.StandardCharsets;

public class ChannelRainMiner implements PluginMessageListener {
    // Todo: Rain miner support.

    public final static int SIG = 3611523;

    public final static String CHANNEL_NAME = "rainminer:excavate";

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        ByteBuf buf = Unpooled.wrappedBuffer(message);
        if (buf.readInt() != SIG) {
            return;
        }
        String msg = buf.toString(StandardCharsets.UTF_8);
    }
}
