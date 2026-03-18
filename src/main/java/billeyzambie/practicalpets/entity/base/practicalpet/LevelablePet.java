package billeyzambie.practicalpets.entity.base.practicalpet;

import billeyzambie.practicalpets.entity.base.MobInterface;
import billeyzambie.practicalpets.items.PetHat;
import billeyzambie.practicalpets.misc.PPSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.UUID;

public interface LevelablePet extends MobInterface, OwnableEntity {
    default double getBaseValueOrElseDefault(Attribute attribute) {
        AttributeInstance instance = this.getAttribute(attribute);
        if (instance == null)
            return attribute.getDefaultValue();
        else
            return instance.getBaseValue();
    }

    default double getLevel1MaxHealth() {
        return this.getBaseValueOrElseDefault(Attributes.MAX_HEALTH);
    }

    default double getLevel1AttackDamage() {
        return this.getBaseValueOrElseDefault(Attributes.ATTACK_DAMAGE);
    }

    double getLevel10MaxHealth();

    double getLevel10AttackDamage();

    default boolean isLevelable() {
        return isTame() && !isBaby();
    }

    boolean isBaby();
    boolean isTame();

    /** Remember to also register the synched entity data and call
     * the {@link LevelablePet#loadPetLevelingData(CompoundTag)}
     * and {@link LevelablePet#savePetLevelingData(CompoundTag)}
     * methods in the compound tag */
    int getPetLevel();
    /** Should also call {@link LevelablePet#refreshPetLevelAttributeMultipliers()} */
    void setPetLevelRaw(int value);

    /** Remember to also register the synched entity data and call
     * the {@link LevelablePet#loadPetLevelingData(CompoundTag)}
     * and {@link LevelablePet#savePetLevelingData(CompoundTag)}
     * methods in the compound tag */
    float getPetXP();
    void setPetXPRaw(float value);

    default void loadPetLevelingData(CompoundTag compoundTag) {
        if (compoundTag.contains("PPetLevel", Tag.TAG_INT)) {
            this.setPetLevelRaw(compoundTag.getInt("PPetLevel"));
            this.setPetXPRaw(compoundTag.getFloat("PPetXP"));
        }
    }

    default void savePetLevelingData(CompoundTag compoundTag) {
        compoundTag.putInt("PPetLevel", this.getPetLevel());
        compoundTag.putFloat("PPetXP", this.getPetXP());
    }

    default void addXpOnHit(Entity target) {
        if (target instanceof Mob mob) {
            float amount = 1;
            if (!mob.isAlive())
                amount += mob.getMaxHealth() / 20;
            addPetXP(amount);
        }
    }

    default void addPetXP(float amount) {
        if (!isLevelable())
            return;

        float multiplier = 1;

        if (this instanceof PetEquipmentWearer wearer) {
            ItemStack headStack = wearer.getPetHeadItem();
            if (headStack.getItem() instanceof PetHat petHat)
                multiplier *= petHat.petXPMultiplier(headStack, wearer);
        }

        setPetXPRaw(getPetXP() + amount * multiplier);

        if (getPetXP() >= getTotalPetXPNeededForLevel(getPetLevel() + 1)) {
            upgradeToLevel(getPetLevel() + 1);
        }
    }

    /**
     * unused
     */
    private static float xpFormula1(int level) {
        if (level == 1)
            return 0;
        return 40 * (level - 1) + 2.5f * level * level;
    }

    private static float xpFormula2(int level) {
        return 40 * (level - 1) + 10 * (level - 1) * (level - 1) * (level - 1);
    }

    default float getTotalPetXPNeededForLevel(int level) {
        return xpFormula2(level);
    }

    default void playLevelUpSound() {
        this.level().playSound(null, this.blockPosition(), PPSounds.PET_LEVEL_UP.get(), SoundSource.NEUTRAL,
                1.0F, (float) Math.pow(2, (this.getPetLevel() - 2) / 12d));
    }

    default void upgradeToLevel(int level) {
        int previousLevel = getPetLevel();
        setPetLevelRaw(level);
        if (level().isClientSide()) {
            return;
        }

        this.playLevelUpSound();
        Component message = Component.translatable(
                "ui.practicalpets.chat.level_up",
                Component.literal(this.getName().getString()).withStyle(ChatFormatting.LIGHT_PURPLE),
                Component.literal(String.valueOf(level)).withStyle(ChatFormatting.BLUE)
        ).withStyle(ChatFormatting.WHITE);
        double radius = 16.0;
        AABB area = new AABB(this.blockPosition()).inflate(radius);
        List<Player> nearbyPlayers = this.level().getEntitiesOfClass(Player.class, area);
        for (Player player : nearbyPlayers) {
            player.sendSystemMessage(message);
        }
        if (this.getOwner() instanceof ServerPlayer owner && !nearbyPlayers.contains(owner)) {
            owner.sendSystemMessage(message);
        }


        setHealth(getMaxHealth());
    }

    default void changePetLevel(int petLevel) {
        this.setPetLevelRaw(petLevel);
        this.setPetXPRaw(getTotalPetXPNeededForLevel(petLevel));
        this.setHealth((float) getBaseValueOrElseDefault(Attributes.MAX_HEALTH));
        this.playLevelUpSound();
    }

    //Probably only one UUID is needed but I'm not sure and I can't bother testing
    UUID MAX_HEALTH_MULTIPLIER = UUID.fromString("f0ba082f-51a6-4f1d-8f25-991e9775d9b1");
    UUID ATTACK_DAMAGE_MULTIPLIER = UUID.fromString("7f999cbc-bb79-4c2b-8cda-0e15166bf50a");

    default void refreshPetLevelAttributeMultipliers() {
        int level = this.getPetLevel();
        double progress1to10 = (level - 1) / 9d;

        AttributeInstance healthAttribute = this.getAttribute(Attributes.MAX_HEALTH);
        AttributeInstance damageAttribute = this.getAttribute(Attributes.ATTACK_DAMAGE);

        if (healthAttribute == null || damageAttribute == null)
            return;

        healthAttribute.removeModifier(MAX_HEALTH_MULTIPLIER);
        damageAttribute.removeModifier(ATTACK_DAMAGE_MULTIPLIER);

        if (level <= 1)
            return;

        double targetHealth = Mth.lerp(progress1to10, getLevel1MaxHealth(), getLevel10MaxHealth());
        //Health not being a multiple of 2 looks confusing on jade
        targetHealth = Math.round(targetHealth / 2d) * 2;

        double targetDamage = Mth.lerp(progress1to10, getLevel1AttackDamage(), getLevel10AttackDamage());

        healthAttribute.addTransientModifier(new AttributeModifier(
                MAX_HEALTH_MULTIPLIER,
                "pplevelmultiplier",
                targetHealth / getLevel1MaxHealth(),
                AttributeModifier.Operation.MULTIPLY_BASE
        ));
        damageAttribute.addTransientModifier(new AttributeModifier(
                ATTACK_DAMAGE_MULTIPLIER,
                "pplevelmultiplier",
                targetDamage / getLevel1AttackDamage(),
                AttributeModifier.Operation.MULTIPLY_BASE
        ));
    }
}
