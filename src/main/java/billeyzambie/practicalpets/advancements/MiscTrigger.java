package billeyzambie.practicalpets.advancements;

import billeyzambie.practicalpets.misc.PracticalPets;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class MiscTrigger extends SimpleCriterionTrigger<MiscTrigger.TriggerInstance> {
    public static final ResourceLocation ID = new ResourceLocation(PracticalPets.MODID, "misc");

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    @Override
    public @NotNull TriggerInstance createInstance(@NotNull JsonObject json, @NotNull ContextAwarePredicate player, @NotNull DeserializationContext context) {
        int index = json.has("index") ? json.get("index").getAsInt() : 0;
        return new TriggerInstance(player, index);
    }

    public void trigger(ServerPlayer player, int index) {
        this.trigger(player, instance -> instance.matches(index));
    }

    public enum Advancement {BANANA_DUCK1}

    public void trigger(ServerPlayer player, Advancement advancement) {
        this.trigger(player, instance -> instance.matches(advancement.ordinal()));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final int index;

        public TriggerInstance(ContextAwarePredicate player, Integer index) {
            super(ID, player);
            this.index = index;
        }

        public boolean matches(int index) {
            return this.index == index;
        }

        @Override
        public @NotNull JsonObject serializeToJson(@NotNull SerializationContext context) {
            JsonObject json = super.serializeToJson(context);
            json.addProperty("index", this.index);
            return json;
        }
    }
}