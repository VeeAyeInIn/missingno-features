package me.vee.forge.missingnofeatures.handlers;

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.forms.EnumSpecial;
import me.vee.forge.missingnofeatures.items.DeepSeaStarItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DeepSeaStarInteractionHandler {

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.EntityInteract e) {

        if (!(e.getItemStack().getItem() instanceof DeepSeaStarItem)) {
            return;
        }

        if (!(e.getTarget() instanceof EntityPixelmon)) {
            return;
        }

        ItemStack itemStack = e.getItemStack();
        EntityPixelmon pixelmon = (EntityPixelmon) e.getTarget();

        if (pixelmon.getSpecies().getPossibleForms(false).contains(EnumSpecial.Drowned) && pixelmon.getFormEnum() != EnumSpecial.Drowned) {
            if (!e.getEntityPlayer().isCreative()) {
                itemStack.setCount(itemStack.getCount() - 1);
            }
            pixelmon.setForm(EnumSpecial.Drowned);
        }
    }
}
