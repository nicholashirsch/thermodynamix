package thermodynamix;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import thermodynamix.core.DeferredRegistry;
import thermodynamix.core.Temperature;

/**
 * Main entry point into the mod. This is the first thing pulled by Minecraft during startup and must load all other mod
 * components.
 */
@Mod(Thermodynamix.MODID) // Marks this as a mod for NeoForge
public class Thermodynamix {

    /** Unique identifier for this mod. Must match an entry in the META-INF/neoforge.mods.toml. */
    public static final String MODID = "thermodynamix";

    /** Reference to the slf4j logger. This is the thing that prints to console during startup. */
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * This constructor is called by NeoForge when Minecraft is loading and is what inserts the mod into the game.
     *
     * @param loadingEventBus Injected by NeoForge during startup. All DeferredRegister objects must be registered with
     *                        it because it is through this bus that FML sends out events which do things like "add all
     *                        blocks in this mod to the block list".
     */
    public Thermodynamix(IEventBus loadingEventBus) {
        // Register all of the the mod's features via DeferredRegistry objects into the game.
        DeferredRegistry.BLOCKS.register(loadingEventBus);
        DeferredRegistry.BLOCK_ENTITIES.register(loadingEventBus);
        DeferredRegistry.ITEMS.register(loadingEventBus);
        DeferredRegistry.DATA_COMPONENTS.register(loadingEventBus);

        // Add a listen to the NeoForge event bus. This allows the mod to respond to Vanilla events.
        NeoForge.EVENT_BUS.addListener(ItemTooltipEvent.class,
                event -> event.getToolTip()
                        .add(Component.literal(Temperature.getTemperatureTooltip(event.getItemStack()))));
    }
}
