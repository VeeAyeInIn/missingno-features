package me.vee.forge.missingnofeatures;

import com.pixelmonmod.pixelmon.Pixelmon;
import me.vee.forge.missingnofeatures.handlers.*;
import me.vee.forge.missingnofeatures.items.DeepSeaStarItem;
import me.vee.forge.missingnofeatures.items.PortableHealerItem;
import me.vee.forge.missingnofeatures.items.PortablePCItem;
import me.vee.forge.missingnofeatures.items.tabs.MissingNoFeaturesTab;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.logging.Logger;

@Mod(
        modid = MissingNoFeatures.MOD_ID,
        name = MissingNoFeatures.MOD_NAME,
        version = MissingNoFeatures.VERSION,
        dependencies = "required-after:pixelmon"
)
public class MissingNoFeatures {

    public static final String MOD_ID = "missingno-features";
    public static final String MOD_NAME = "MissingNo Features";
    public static final String VERSION = "1.0.0";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    public static final MissingNoFeaturesTab CREATIVE_TAB = new MissingNoFeaturesTab("mod_items");

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static MissingNoFeatures INSTANCE;

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {

    }

    /**
     * This is the second initialization event. Register custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    /**
     * This is the final initialization event. Register actions from other mods here
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

        // Check player data on save and on load
        MinecraftForge.EVENT_BUS.register(new DataHandler());

        // On Deep Sea Star usage
        MinecraftForge.EVENT_BUS.register(new DeepSeaStarInteractionHandler());

        // When Ultra Beasts are captured
        Pixelmon.EVENT_BUS.register(new UltraBeastCaptureHandler());

        // When players win a non-wild pixelmon battle
        Pixelmon.EVENT_BUS.register(new TrainerBattleVictoryHandler());
    }

    /**
     * Forge will automatically look up and bind blocks to the fields in this class
     * based on their registry name.
     */
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Blocks {}

    /**
     * Forge will automatically look up and bind items to the fields in this class
     * based on their registry name.
     */


    /**
     * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
     */
    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {

        /**
         * Listen for the register event for creating custom items
         */
        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {

            event.getRegistry().register(new PortableHealerItem()
                    .setRegistryName(MOD_ID, "portable_healer")
                    .setTranslationKey("portable_healer")
                    .setCreativeTab(CREATIVE_TAB)
                    .setMaxStackSize(1)
                    .setMaxDamage(63)
            );

            event.getRegistry().register(new PortablePCItem()
                    .setRegistryName(MOD_ID, "portable_pc")
                    .setTranslationKey("portable_pc")
                    .setCreativeTab(CREATIVE_TAB)
                    .setMaxStackSize(1)
            );

            event.getRegistry().register(new PortableHealerItem.UnbreakableHealer()
                    .setRegistryName(MOD_ID, "advanced_healer")
                    .setTranslationKey("advanced_healer")
                    .setCreativeTab(CREATIVE_TAB)
                    .setMaxStackSize(1)
            );

            event.getRegistry().register(new DeepSeaStarItem()
                    .setRegistryName(MOD_ID, "deep_sea_star")
                    .setTranslationKey("deep_sea_star")
                    .setCreativeTab(CREATIVE_TAB)
                    .setMaxStackSize(64)
            );
        }

        /**
         * Listen for the register event for creating custom blocks
         */
        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {
           /*
             event.getRegistry().register(new MySpecialBlock().setRegistryName(MOD_ID, "mySpecialBlock"));
            */
        }
    }
}
