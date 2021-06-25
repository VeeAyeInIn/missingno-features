package me.vee.forge.missingnofeatures.items.tabs;

import me.vee.forge.missingnofeatures.MissingNoFeatures;
import me.vee.forge.missingnofeatures.items.MissingNoItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MissingNoFeaturesTab extends CreativeTabs {

    public MissingNoFeaturesTab(String name) {
        super(MissingNoFeatures.MOD_ID + "." + name);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack createIcon() {
        return new ItemStack(MissingNoItems.ADVANCED_HEALER);
    }
}
