package billeyzambie.practicalpets.mixin.domesticationinnovation;

import billeyzambie.practicalpets.entity.base.SpecialPunchPet;
import com.github.alexthe668.domesticationinnovation.client.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.client.event.InputEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientProxy.class)
public abstract class ClientProxyMixin {
    @Inject(
            method = "onAttackEntityFromClientEvent",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    private void onOnAttackEntityFromClientEvent(InputEvent.InteractionKeyMappingTriggered event, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        if (
                event.isAttack()
                        && minecraft.hitResult instanceof EntityHitResult entityHitResult
                        && entityHitResult.getEntity() instanceof SpecialPunchPet pet
                        && pet.shouldDisableDIPunchThrough(minecraft.player)
        ) {
            ci.cancel();
        }
    }
}
