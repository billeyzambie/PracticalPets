package billeyzambie.practicalpets.entity.fish;

import billeyzambie.animationcontrollers.ACData;
import billeyzambie.animationcontrollers.ACEntity;
import billeyzambie.animationcontrollers.BVCData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.level.Level;

import java.util.HashMap;

public abstract class PracticalFish extends TamableSchoolingFish implements ACEntity {
    HashMap<String, ACData> ACData = new HashMap<>();
    @Override
    public HashMap<String, ACData> getACData() {
        return ACData;
    }

    HashMap<String, BVCData> BVCData = new HashMap<>();
    @Override
    public HashMap<String, BVCData> getBVCData() {
        return BVCData;
    }

    public PracticalFish(EntityType<? extends AbstractSchoolingFish> p_27523_, Level p_27524_) {
        super(p_27523_, p_27524_);
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }
}