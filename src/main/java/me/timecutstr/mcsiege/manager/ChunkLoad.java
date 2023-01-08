package me.timecutstr.mcsiege.manager;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class ChunkLoad {

    public Set<Chunk> getChunks() {
        return chunks;
    }

    private final Set<Chunk> chunks = new HashSet<>();
    private int loadDistance = 50;

    public void loadChunks(Location targetLocation) {
        World world = targetLocation.getWorld();
        for (int x = targetLocation.getBlockX() - loadDistance; x <= targetLocation.getBlockX() + loadDistance; x += 16) {
            for (int z = targetLocation.getBlockZ() - loadDistance; z <= targetLocation.getBlockZ() + loadDistance; z += 16) {
                Chunk chunk = world.getChunkAt(x, z);
                chunk.load(true);
                chunks.add(chunk);
            }
        }

    }

    public void unloadChunks() {
        for (Chunk chunk : chunks) {
            chunk.unload(true);
        }
        chunks.clear();
    }

}
