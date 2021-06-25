package me.vee.forge.missingnofeatures.handlers;

import me.vee.forge.missingnofeatures.MissingNoFeatures;
import me.vee.forge.missingnofeatures.items.MissingNoItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Objects;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MissingNoFeatures.MOD_ID)
public class ModelRegistrationHandler {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        registerModel(MissingNoItems.ADVANCED_HEALER, 0);
        registerModel(MissingNoItems.PORTABLE_HEALER, 0);
        registerModel(MissingNoItems.PORTABLE_PC, 0);
        registerModel(MissingNoItems.DEEP_SEA_STAR, 0);
    }

    private static void registerModel(Item item, int metadata) {
        ModelLoader.setCustomModelResourceLocation(item, metadata,
                new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
    }
}
