package me.vee.forge.missingnofeatures.triggers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import me.vee.forge.missingnofeatures.MissingNoFeatures;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Used in conjunction with {@link me.vee.forge.missingnofeatures.handlers.TrainerBattleVictoryHandler}, to activate once
 * a battle has been won by a {@link com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant}.
 *
 * @see com.pixelmonmod.pixelmon.api.advancements.triggers.WildBattleVictoryTrigger
 */
@SuppressWarnings("NullableProblems")
public class TrainerBattleVictoryTrigger implements ICriterionTrigger<TrainerBattleVictoryTrigger.Instance> {

    private static final ResourceLocation ID = new ResourceLocation(MissingNoFeatures.MOD_ID + ":trainer_battle_victory_trigger");
    private final Map<PlayerAdvancements, Listeners> listeners = Maps.newHashMap();

    public TrainerBattleVictoryTrigger() {}

    public ResourceLocation getId() {
        return ID;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<TrainerBattleVictoryTrigger.Instance> listener) {
        TrainerBattleVictoryTrigger.Listeners trainerbattlevictorytrigger$Listeners = this.listeners.get(playerAdvancementsIn);
        if (trainerbattlevictorytrigger$Listeners == null) {
            trainerbattlevictorytrigger$Listeners = new TrainerBattleVictoryTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, trainerbattlevictorytrigger$Listeners);
        }
        trainerbattlevictorytrigger$Listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<TrainerBattleVictoryTrigger.Instance> listener) {
        TrainerBattleVictoryTrigger.Listeners BallCaptureTrigger$listeners = this.listeners.get(playerAdvancementsIn);
        if (BallCaptureTrigger$listeners != null) {
            BallCaptureTrigger$listeners.remove(listener);
            if (BallCaptureTrigger$listeners.isEmpty()) {
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    public TrainerBattleVictoryTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        String victoryString = json.has("trainer_kills") ? JsonUtils.getString(json, "trainer_kills") : "";
        int victories = Integer.parseInt(victoryString);
        return new TrainerBattleVictoryTrigger.Instance(this.getId(), victories);
    }

    public void trigger(EntityPlayerMP player, int victories) {
        TrainerBattleVictoryTrigger.Listeners trainerbattlevictorytrigger$Listeners = this.listeners.get(player.getAdvancements());
        if (trainerbattlevictorytrigger$Listeners != null) {
            trainerbattlevictorytrigger$Listeners.trigger(victories);
        }
    }

    static class Listeners {

        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<Instance>> listeners = Sets.newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(Listener<TrainerBattleVictoryTrigger.Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(Listener<TrainerBattleVictoryTrigger.Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(int victories) {
            List<Listener<Instance>> list = null;
            Iterator<Listener<Instance>> var3;
            Listener<Instance> listener;
            for (var3 = this.listeners.iterator(); var3.hasNext(); list.add(listener)) {
                listener = var3.next();
                if (list == null) {
                    list = Lists.newArrayList();
                }
            }
            if (list != null) {
                var3 = list.iterator();
                while (var3.hasNext()) {
                    listener = var3.next();
                    if ((listener.getCriterionInstance()).test(victories)) {
                        listener.grantCriterion(this.playerAdvancements);
                    }
                }
            }
        }
    }

    public static class Instance extends AbstractCriterionInstance {

        private final int victories;

        public Instance(ResourceLocation criterionIn, int victories) {
            super(criterionIn);
            this.victories = victories;
        }

        public boolean test(int victories) {
            return this.victories <= victories;
        }
    }
}
