package cx.rain.mc.bukkit.fastexcavator;

import cx.rain.mc.bukkit.fastexcavator.channel.ChannelDiggusMaximus;
import cx.rain.mc.bukkit.fastexcavator.channel.ChannelRainMiner;
import cx.rain.mc.bukkit.fastexcavator.event.EventPlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class FastExcavator extends JavaPlugin {
    private static FastExcavator INSTANCE;

    public final static List<String> CHANNELS = new ArrayList<>();

    public FastExcavator() {
        INSTANCE = this;
        CHANNELS.add(ChannelRainMiner.CHANNEL_NAME);
        CHANNELS.add(ChannelDiggusMaximus.CHANNEL_NAME);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getMessenger().registerIncomingPluginChannel(this,
                ChannelRainMiner.CHANNEL_NAME, new ChannelRainMiner());

        Bukkit.getPluginManager().registerEvents(new EventPlayerJoin(), this);

        getLogger().info("FastExcavator is working!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye.");
    }

    public static FastExcavator getInstance() {
        return INSTANCE;
    }
}
