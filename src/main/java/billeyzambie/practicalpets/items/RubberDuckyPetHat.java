package billeyzambie.practicalpets.items;

import billeyzambie.practicalpets.entity.base.practicalpet.PetEquipmentWearer;
import billeyzambie.practicalpets.misc.PPSounds;
import billeyzambie.practicalpets.misc.PracticalPets;
import billeyzambie.practicalpets.misc.PPNetworking;
import billeyzambie.practicalpets.network.PetHatSquishAnimPacket;
import billeyzambie.practicalpets.util.DelayedTaskManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RubberDuckyPetHat extends Item implements EntityModelPetCosmetic, DyeableItem {
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
    public ResourceLocation getModelTexture(ItemStack stack, PetEquipmentWearer wearer) {
        return modelTexture;
    }

    @Override
    public Slot slot(ItemStack stack, PetEquipmentWearer wearer) {
        return Slot.HEAD;
    }

    @Override
    public boolean canBePutOn(ItemStack stack, PetEquipmentWearer wearer) {
        return true;
    }

    @Override
    public boolean causesBravery(ItemStack stack, PetEquipmentWearer wearer) {
        return true;
    }

    @Override
    public float reachMultiplier(ItemStack stack, PetEquipmentWearer wearer) {
        return 1.125f;
    }

    private static final Set<Integer> entityIdJustGotRubberDuckied = new HashSet<>();

    public static void applyEffect(PetEquipmentWearer wearer, Entity target) {
        int targetId = target.getId();
        if (entityIdJustGotRubberDuckied.contains(targetId)) {
            return;
        }
        entityIdJustGotRubberDuckied.add(targetId);

        playRubberDuckyPetHatSquishAnimation(wearer);

        Vec3 direction = target.position().subtract(wearer.position()).normalize().scale(0.2);
        target.push(direction.x, 0.1, direction.z);

        DelayedTaskManager.schedule(() -> {
            if (wearer.isAlive() && target.isAlive()) {
                Vec3 direction2 = target.position().subtract(wearer.position()).normalize().scale(0.2);
                target.push(direction2.x, 0.1, direction2.z);
                wearer.petCosmeticDamageEntity(target, 1);
                entityIdJustGotRubberDuckied.remove(targetId);
            }
        }, 10);

    }

    public static void playRubberDuckyPetHatSquishAnimation(PetEquipmentWearer wearer) {
        wearer.playSound(PPSounds.DUCK_AMBIENT.get(), 1f, 1.5f + wearer.getRandom().nextFloat() * 0.2f);

        PPNetworking.CHANNEL.send(
                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(wearer::asMob),
                new PetHatSquishAnimPacket(wearer.asMob().getId())
        );
    }

    @Override
    public boolean onPetHurt(ItemStack stack, PetEquipmentWearer wearer, DamageSource source, float amount) {
        Entity target = source.getEntity();
        if (target != null) {
            applyEffect(wearer, target);
        }
        return false;
    }

    @Override
    public void onPetHit(ItemStack stack, PetEquipmentWearer wearer, Entity target) {
        applyEffect(wearer, target);
    }
}
