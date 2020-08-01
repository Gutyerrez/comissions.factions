package io.github.factions.ranking.misc.utils;

import com.boydti.fawe.object.collection.BlockVectorSet;
import com.boydti.fawe.object.visitor.FastIterator;
import com.boydti.fawe.util.EditSessionBuilder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import java.util.Map;
import java.util.Set;

/**
 * @author SrGutyerrez
 */
public class ChunkUtils {

    public static ImmutableMap<EntityType, Integer> getSpawners(Chunk chunk) {
        Map<EntityType, Integer> types = Maps.newHashMap();

        World world = chunk.getWorld();

        Vector vector1 = new Vector(
                chunk.getX(),
                256,
                chunk.getZ()
        ),
        vector2 = new Vector(
                chunk.getX(),
                0,
                chunk.getZ()
        );

        Set<Vector> vectors = new BlockVectorSet();

        vectors.add(vector1);
        vectors.add(vector2);

        EditSession editSession = new EditSessionBuilder(world.getName())
                .fastmode(true)
                .build();

        FastIterator fastIterator = new FastIterator(
                vectors,
                editSession
        );

        for (Vector vector : fastIterator) {
            BaseBlock block = editSession.getBlock(vector);

            Material material = Material.getMaterial(block.getType());

            if (material == Material.MOB_SPAWNER) {
                Block _block = world.getBlockAt(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());

                CreatureSpawner creatureSpawner = (CreatureSpawner) _block.getState();

                types.put(
                        creatureSpawner.getSpawnedType(),
                        types.getOrDefault(
                                creatureSpawner.getSpawnedType(),
                                0
                        ) + 1
                );
            }
        }

        return ImmutableMap.copyOf(types);
    }

}
