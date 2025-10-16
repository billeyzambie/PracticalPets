package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.misc.PPSounds;
import billeyzambie.practicalpets.misc.PracticalPets;
import billeyzambie.practicalpets.entity.PracticalPet;
import billeyzambie.practicalpets.network.ModNetworking;
import billeyzambie.practicalpets.network.PetHatSquishAnimPacket;
import billeyzambie.practicalpets.util.DelayedTaskManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

public class RubberDuckyPetHat extends Item implements AttachablePetCosmetic, DyeableItem {
    public RubberDuckyPetHat() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    ResourceLocation modelTexture = new ResourceLocation(
            PracticalPets.MODID,
            "textures/entity/pet_equipment/rubber_ducky.png"
    );

    @Override
    public int getDefaultColor() {
        return 16774175;
    }

    @Override
    public ResourceLocation getModelTexture() {
        return modelTexture;
    }

    @Override
    public AttachBone getAttachBone() {
        return AttachBone.HAT;
    }

    @Override
    public Slot slot() {
        return Slot.HEAD;
    }

    @Override
    public boolean canBePutOn(PracticalPet pet) {
        return true;
    }

    @Override
    public boolean causesBravery() {
        return true;
    }

    public static void applyEffect(PracticalPet wearer, Entity target) {
        playRubberDuckyPetHatSquishAnimation(wearer);

        Vec3 direction = target.position().subtract(wearer.position()).normalize().scale(0.2);
        target.push(direction.x, 0.1, direction.z);

        DelayedTaskManager.schedule(() -> {
            if (wearer.isAlive() && target.isAlive()) {
                Vec3 direction2 = target.position().subtract(wearer.position()).normalize().scale(0.2);
                target.push(direction2.x, 0.1, direction2.z);
                target.hurt(wearer.damageSources().mobAttack(wearer), 3);
            }
        }, 10);

    }


    public static void playRubberDuckyPetHatSquishAnimation(PracticalPet wearer) {
        wearer.playSound(PPSounds.DUCK_AMBIENT.get(), 1f, 1.5f + wearer.getRandom().nextFloat() * 0.2f);
        ModNetworking.CHANNEL.send(
                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> wearer),
                new PetHatSquishAnimPacket(wearer.getId())
        );
    }
}
