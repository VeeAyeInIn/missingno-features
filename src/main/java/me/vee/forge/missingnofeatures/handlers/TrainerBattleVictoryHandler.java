package me.vee.forge.missingnofeatures.handlers;

import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.WildPixelmonParticipant;
import com.pixelmonmod.pixelmon.enums.battle.BattleResults;
import me.vee.forge.missingnofeatures.MissingNoFeatures;
import me.vee.forge.missingnofeatures.triggers.TrainerBattleVictoryTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.IOException;

public class TrainerBattleVictoryHandler {

    public final TrainerBattleVictoryTrigger TRAINER_BATTLE_VICTORY_TRIGGER = CriteriaTriggers.register(new TrainerBattleVictoryTrigger());

    @SubscribeEvent
    public void onVictory(BattleEndEvent e) {

        for (BattleParticipant bp : e.results.keySet()) {
            if (bp instanceof WildPixelmonParticipant) {
                return;
            }
        }

        e.results.forEach((participant, result) -> {
            if (participant instanceof PlayerParticipant && result == BattleResults.VICTORY) {
                EntityPlayerMP player = ((PlayerParticipant) participant).player;
                int wins = DataHandler.PLAYER_TRAINER_WINS.get(player.getUniqueID().toString()) + 1;
                DataHandler.PLAYER_TRAINER_WINS.put(player.getUniqueID().toString(), wins);
                TRAINER_BATTLE_VICTORY_TRIGGER.trigger(player, DataHandler.PLAYER_TRAINER_WINS.get(player.getUniqueID().toString()));
                try {
                    DataHandler.write(((PlayerParticipant) participant).player.getUniqueID().toString(), wins);
                } catch (IOException ioException) {
                    MissingNoFeatures.LOGGER.warning(String.format("Couldn't save data for %s:%s", player.getName(), player.getUniqueID()));
                    ioException.printStackTrace();
                }
                player.sendMessage(new TextComponentString("Found wins at " + wins));
            }
        });
    }
}
