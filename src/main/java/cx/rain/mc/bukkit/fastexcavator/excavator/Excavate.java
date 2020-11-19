package cx.rain.mc.bukkit.fastexcavator.excavator;

import cx.rain.mc.bukkit.fastexcavator.channel.ChannelDiggusMaximus;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.entity.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class Excavate {
    private final static List<BlockPosition> POSITIONS = new ArrayList<>();

    static {
        POSITIONS.addAll(BlockPosition.a(-1, -1, -1, 1, 1, 1)
                .map(BlockPosition::immutableCopy)
                .collect(Collectors.toList()));
    }

    private final Player bukkitPlayer;
    private final Block startBlock;
    private final BlockPosition startPos;
    private final EntityPlayer player;
    private final WorldServer world;
    private final Deque<BlockPosition> points = new ArrayDeque<>();

    private int mined = 0;

    public Excavate(Player bukkitPlayerIn, BlockPosition blockPos, EntityPlayer playerEntity) {
        bukkitPlayer = bukkitPlayerIn;
        startBlock = getBlockAt(playerEntity.world, blockPos);
        startPos = blockPos;
        player = playerEntity;
        world = playerEntity.getWorld().getMinecraftWorld();
    }

    public void start() {
        forceExcavate(startPos);
        // Todo: Server configurations (is allowed block).
        while (!points.isEmpty()) {
            spread(points.remove());
        }
    }

    private void forceExcavate(BlockPosition pos) {
        if (player.playerInteractManager.breakBlock(pos)) {
            points.add(pos);
            mined++;
            ChannelDiggusMaximus.sendExcavatePacket(bukkitPlayer, pos);
        }
    }

    private void spread(BlockPosition posIn) {
        for (BlockPosition pos : POSITIONS) {
            if (isValidPos(pos)) {
                excavate(posIn.a(pos));
            }
        }
    }

    private void excavate(BlockPosition pos) {
        // Todo: Server configurations (max mine blocks count).
        if (mined >= 60) {
            return;
        }
        if (isSame(getBlockAt(world, pos), startBlock)
                && canMine(pos, startPos)
                && player.playerInteractManager.breakBlock(pos)) {
            points.add(pos);
            mined++;
            ChannelDiggusMaximus.sendExcavatePacket(bukkitPlayer, pos);
        }
    }

    private static boolean isValidPos(BlockPosition pos) {
        return (Math.abs(pos.getX()) + Math.abs(pos.getY()) + Math.abs(pos.getZ())) != 0;
    }

    private static boolean isSame(Block blockStart, Block blockNew) {
        return blockStart.r().equals(blockNew.r());
    }

    private static Block getBlockAt(World world, BlockPosition pos) {
        return world.getType(pos).getBlock();
    }

    private static boolean canMine(BlockPosition pos, BlockPosition startPos) {
        return pos.a(startPos, 11);
    }
}
