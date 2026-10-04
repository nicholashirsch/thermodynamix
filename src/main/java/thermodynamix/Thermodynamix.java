package thermodynamix;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;
import thermodynamix.core.Temperature;

/**
 * Main entry point into the mod. This is the first thing pulled by Minecraft during startup and must load all other mod
 * components.
 */
@Mod(Thermodynamix.MODID)  // Marks this as a mod for NeoForge
public class Thermodynamix {

    /** Unique identifier for this mod. Must match an entry in the META-INF/neoforge.mods.toml. */
    public static final String MODID = "thermodynamix";

    /** Reference to the slf4j logger. This is the thing that prints to console during startup. */
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
        DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MODID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> TEMPERATURE =
        DATA_COMPONENTS.registerComponentType("temperature",
            builder -> builder.persistent(Codec.FLOAT).networkSynchronized((ByteBufCodecs.FLOAT)));

    /**
     * This constructor is called by NeoForge when Minecraft is loading and is what inserts the mod into the game.
     *
     * @param loadingEventBus Injected by NeoForge during startup. All DeferredRegister objects must be registered with
     *                        it because it is through this bus that FML sends out events which do things like "add all
     *                        blocks in this mod to the block list".
     */
    public Thermodynamix(IEventBus loadingEventBus) {
        DATA_COMPONENTS.register(loadingEventBus);

        NeoForge.EVENT_BUS.addListener(ItemTooltipEvent.class,
            event -> event.getToolTip()
                .add(Component.literal(Temperature.getTemperatureTooltip(event.getItemStack()))));
    }
}
