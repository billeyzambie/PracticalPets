package billeyzambie.practicalpets.entity.fish;

import billeyzambie.animationcontrollers.ACData;
import billeyzambie.animationcontrollers.BVCData;
import billeyzambie.animationcontrollers.SwimmingAnimationEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.level.Level;

import java.util.HashMap;

public abstract class PracticalFish extends TamableSchoolingFish implements SwimmingAnimationEntity {
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

    private float prevLimbSwing = 0;

    @Override
    public float getPrevLimbSwing() {
        return prevLimbSwing;
    }

    @Override
    public void setPrevLimbSwing(float value) {
        this.prevLimbSwing = value;
    }

    private float swimSwing = 0;

    @Override
    public void addToSwimSwing(float value) {
        swimSwing += value;
    }

    @Override
    public float getSwimSwing() {
        return swimSwing;
    }

    private float swimSwingAmount = 0;

    @Override
    public float getSwimSwingAmount() {
        return swimSwingAmount;
    }
    @Override
    public void setSwimSwingAmount(float value) {
        this.swimSwingAmount = value;
    }

    public PracticalFish(EntityType<? extends AbstractSchoolingFish> p_27523_, Level p_27524_) {
        super(p_27523_, p_27524_);
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }


}