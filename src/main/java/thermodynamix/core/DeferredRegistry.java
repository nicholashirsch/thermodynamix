package thermodynamix.core;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import thermodynamix.Thermodynamix;

public class DeferredRegistry {

    // ----------------------
    //   BLOCK REGISTRATION
    // ----------------------
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Thermodynamix.MODID);


    // -------------------------------
    //   BLOCK ENTITIES REGISTRATION
    // -------------------------------
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Thermodynamix.MODID);


    // ---------------------
    //   ITEM REGISTRATION
    // ---------------------
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Thermodynamix.MODID);


    // --------------------------------
    //   DATA COMPONENTS REGISTRATION
    // --------------------------------
    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
        DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Thermodynamix.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> TEMPERATURE =
        DATA_COMPONENTS.registerComponentType("temperature",
            builder -> builder.persistent(Codec.FLOAT).networkSynchronized((ByteBufCodecs.FLOAT)));

}
