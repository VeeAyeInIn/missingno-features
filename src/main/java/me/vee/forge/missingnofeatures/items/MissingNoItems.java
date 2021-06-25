package me.vee.forge.missingnofeatures.items;

import me.vee.forge.missingnofeatures.MissingNoFeatures;
import me.vee.forge.missingnofeatures.items.DeepSeaStarItem;
import me.vee.forge.missingnofeatures.items.PortableHealerItem;
import me.vee.forge.missingnofeatures.items.PortablePCItem;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(MissingNoFeatures.MOD_ID)
public class MissingNoItems {
    public static final PortableHealerItem.UnbreakableHealer ADVANCED_HEALER = null;
    public static final PortableHealerItem PORTABLE_HEALER = null;
    public static final PortablePCItem PORTABLE_PC = null;
    public static final DeepSeaStarItem DEEP_SEA_STAR = null;
}
