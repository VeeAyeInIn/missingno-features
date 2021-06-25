package me.vee.forge.missingnofeatures.items;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import com.pixelmonmod.pixelmon.battles.status.StatusType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class PortableHealerItem extends Item {

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World world, @NotNull EntityPlayer player, @NotNull EnumHand hand) {
        if (!world.isRemote) {
            PartyStorage ps = Pixelmon.storageManager.getParty(player.getUniqueID());
            ps.getTeam().forEach(pokemon -> {
                ItemStack itemStack = player.getHeldItem(hand);
                if (pokemon.getHealthPercentage() < 100F || pokemon.getStatus().type.isPrimaryStatus() || !pokemon.getMoveset().hasFullPP()) {
                    itemStack.damageItem(1, player);
                    pokemon.heal();
                }
            });
        }
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    public static class UnbreakableHealer extends PortableHealerItem {

        @Override
        public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World world, @NotNull EntityPlayer player, @NotNull EnumHand hand) {
            if (!world.isRemote) {
                Pixelmon.storageManager.getParty(player.getUniqueID()).heal();
            }
            return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
        }
    }
}
