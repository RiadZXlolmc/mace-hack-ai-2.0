package com.example.maceclient;

import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class AnchorAura extends Module {

    public final IntSetting radius = new IntSetting("Radius", 4, 1, 16, 1);

    private int delay = 0;

    public AnchorAura() {
        super("AnchorAura", "Automatically detonates charged respawn anchors");
        settings.add(radius);
    }

    @Override
    public void tick(MinecraftClient c) {
        ClientPlayerEntity player = c.player;

        if (player == null || c.world == null || c.interactionManager == null) {
            return;
        }

        if (delay > 0) {
            delay--;
            return;
        }

        BlockPos base = player.getBlockPos();

        for (int x = -radius.value; x <= radius.value; x++) {
            for (int y = -radius.value; y <= radius.value; y++) {
                for (int z = -radius.value; z <= radius.value; z++) {

                    BlockPos pos = base.add(x, y, z);

                    if (!c.world.getBlockState(pos).isOf(Blocks.RESPAWN_ANCHOR)) {
                        continue;
                    }

                    int charges = c.world.getBlockState(pos)
                            .get(RespawnAnchorBlock.CHARGES);

                    if (charges <= 0) {
                        continue;
                    }

                    BlockHitResult hit = new BlockHitResult(
                            Vec3d.ofCenter(pos),
                            Direction.UP,
                            pos,
                            false
                    );

                    c.interactionManager.interactBlock(
                            player,
                            Hand.MAIN_HAND,
                            hit
                    );

                    delay = 5;
                    return;
                }
            }
        }
    }
}
