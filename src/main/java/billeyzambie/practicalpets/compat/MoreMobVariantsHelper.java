package billeyzambie.practicalpets.compat;

import billeyzambie.practicalpets.entity.otherpet.GiraffeCat;
import com.github.nyuppo.MoreMobVariants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.animal.Cat;

import java.util.HashMap;
import java.util.Map;

public class MoreMobVariantsHelper {
    private static final Map<String, Integer> giraffeCatVariants = new HashMap<>();

    static {
        int i = 0;
        giraffeCatVariants.put("minecraft:tabby", i++);
        giraffeCatVariants.put("minecraft:black", i++);
        giraffeCatVariants.put("minecraft:red", i++);
        giraffeCatVariants.put("minecraft:siamese", i++);
        giraffeCatVariants.put("minecraft:british_shorthair", i++);
        giraffeCatVariants.put("minecraft:calico", i++);
        giraffeCatVariants.put("minecraft:persian", i++);
        giraffeCatVariants.put("minecraft:ragdoll", i++);
        giraffeCatVariants.put("minecraft:white", i++);
        giraffeCatVariants.put("minecraft:jellie", i++);
        giraffeCatVariants.put("minecraft:all_black", i++);
        giraffeCatVariants.put("practicalpets:perry", i++);
        giraffeCatVariants.put("practicalpets:pizza", i++);
        giraffeCatVariants.put("practicalpets:sugar", i++);
        giraffeCatVariants.put("practicalpets:lily", i++);
        giraffeCatVariants.put("practicalpets:budder", i++);
    }

    public static int getGiraffeCatVariantFromCatVariant(Cat cat) {
        CompoundTag nbt = new CompoundTag();
        cat.saveWithoutId(nbt);
        if (nbt.contains("VariantID")) {
            String variant = nbt.getString("VariantID");
            if (!variant.equals(MoreMobVariants.id("default").toString()) && !variant.isEmpty()) {
                return giraffeCatVariants.getOrDefault(variant, 10);
            } else {
                return 8;
            }
        }
        return GiraffeCat.CAT_VARIANT_TO_INT.getOrDefault(cat.getVariant(), 10);
    }
}
