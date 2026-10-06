package com.example.maceclient.mixin;

import com.example.maceclient.Hitboxes;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "getBoundingBox", at = @At("RETURN"), cancellable = true)
    private void maceclient$expandHitbox(CallbackInfoReturnable<Box> cir) {
        Entity entity = (Entity) (Object) this;

        Box original = cir.getReturnValue();
        Box expanded = Hitboxes.expandBox(entity, original);

        if (expanded != original) {
            cir.setReturnValue(expanded);
        }
    }
}
