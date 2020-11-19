package cx.rain.mc.bukkit.fastexcavator.event;

import cx.rain.mc.bukkit.fastexcavator.FastExcavator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.reflect.Method;

public class EventPlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        try {
            Class<? extends CommandSender> senderClass = player.getClass();
            Method addChannel = senderClass.getDeclaredMethod("addChannel", String.class);
            addChannel.setAccessible(true);
            for (String channelName : FastExcavator.CHANNELS) {
                addChannel.invoke(player, channelName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
