package billeyzambie.practicalpets.advancements;

import billeyzambie.practicalpets.misc.PracticalPets;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;

public class UsedPetAbilityTrigger extends SimpleCriterionTrigger<UsedPetAbilityTrigger.TriggerInstance> {
    public static final ResourceLocation ID = new ResourceLocation(PracticalPets.MODID, "used_pet_ability");

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    @Override
    public @NotNull TriggerInstance createInstance(@NotNull JsonObject json, @NotNull ContextAwarePredicate player, @NotNull DeserializationContext context) {
        ContextAwarePredicate petPredicate = EntityPredicate.fromJson(json, "pet", context);
        Integer ability = json.has("ability") ? json.get("ability").getAsInt() : null;
        return new TriggerInstance(player, petPredicate, ability);
    }

    public void trigger(ServerPlayer player, Entity pet, int ability) {
        LootContext petContext = EntityPredicate.createContext(player, pet);

        this.trigger(player, instance -> instance.matches(petContext, ability));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ContextAwarePredicate pet;
        private final Integer ability;

        public TriggerInstance(ContextAwarePredicate player, ContextAwarePredicate pet, Integer ability) {
            super(ID, player);
            this.pet = pet;
            this.ability = ability;
        }

        public boolean matches(LootContext petContext, int ability) {
            return this.pet.matches(petContext) && (this.ability == null || this.ability.equals(ability));
        }

        @Override
        public @NotNull JsonObject serializeToJson(@NotNull SerializationContext context) {
            JsonObject json = super.serializeToJson(context);
            json.add("pet", this.pet.toJson(context));
            if (this.ability != null)
                json.addProperty("ability", this.ability);
            return json;
        }
    }
}