package com.example.maceclient;

import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class AnchorAura extends Module {

    public final IntSetting radius = new IntSetting("Radius", 4, 1, 16);

    private int delay = 0;

    public AnchorAura() {
        super("AnchorAura", "Automatically detonates charged respawn anchors", true);
        settings.add(radius);
    }

    @Override
    public void tick(MinecraftClient c) {
        if (c.player == null || c.world == null || c.interactionManager == null) {
            return;
        }

        if (delay > 0) {
            delay--;
            return;
        }

        BlockPos base = c.player.getBlockPos();

        for (int x = -radius.value; x <= radius.value; x++) {
            for (int y = -radius.value; y <= radius.value; y++) {
                for (int z = -radius.value; z <= radius.value; z++) {

                    BlockPos pos = base.add(x, y, z);

                    if (!c.world.getBlockState(pos).isOf(Blocks.RESPAWN_ANCHOR)) {
                        continue;
                    }

                    int charges = c.world
                            .getBlockState(pos)
                            .get(RespawnAnchorBlock.CHARGES);

                    // Only activate anchors that are already charged.
                    if (charges <= 0) {
                        continue;
                    }

                    // Create a block interaction at the center of the anchor.
                    Vec3d hitPos = Vec3d.ofCenter(pos);

                    BlockHitResult hit = new BlockHitResult(
                            hitPos,
                            Direction.UP,
                            pos,
                            false
                    );

                    // Right-click the charged anchor.
                    c.interactionManager.interactBlock(
                            c.player,
                            Hand.MAIN_HAND,
                            hit
                    );

                    // Small delay so we don't spam the same anchor every tick.
                    delay = 5;

                    return;
                }
            }
        }
    }
}
