package thermodynamix.core;

import java.util.function.Supplier;

import com.mojang.serialization.Codec;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlazedTerracottaBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import thermodynamix.Thermodynamix;
import thermodynamix.machines.kiln.KilnBlock;
import thermodynamix.machines.kiln.KilnBlockEntity;

/**
 * Holds all the deferred registrations for this mod.
 * 
 * A DeferredRegister is NeoForge's mechanism for registering mod objects into
 * the game. All features added by the mod
 * are stored in these registers and then during startup, they are injected into
 * the game.
 */
public class DeferredRegistry {

        // ------------------
        // BLOCK REGISTRATION
        // ------------------
        public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Thermodynamix.MODID);

        public static final DeferredBlock<KilnBlock> KILN = BLOCKS.registerBlock("kiln", KilnBlock::new,
                        props -> props.strength(3.5f).requiresCorrectToolForDrops());
        public static final DeferredBlock<Block> BLAST_BRICKS = BLOCKS.registerBlock("blast_bricks", Block::new,
                        props -> props.strength(3.5f).requiresCorrectToolForDrops());
        public static final DeferredBlock<Block> FURNACE_STACK = BLOCKS.registerBlock("furnace_stack", Block::new,
                        props -> props.strength(3.5f).requiresCorrectToolForDrops());
        // ponytail: vanilla GlazedTerracottaBlock = plain block facing the placer; swap for a real class when the
        // controller gets a block entity.
        public static final DeferredBlock<Block> BLAST_FURNACE_CONTROLLER = BLOCKS.registerBlock(
                        "blast_furnace_controller", GlazedTerracottaBlock::new,
                        props -> props.strength(3.5f).requiresCorrectToolForDrops());

        // ---------------------------
        // BLOCK ENTITIES REGISTRATION
        // ---------------------------
        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
                        .create(Registries.BLOCK_ENTITY_TYPE, Thermodynamix.MODID);

        public static final Supplier<BlockEntityType<KilnBlockEntity>> KILN_BLOCK_ENTITY = BLOCK_ENTITIES.register(
                        "kiln",
                        () -> new BlockEntityType<>(KilnBlockEntity::new, KILN.get()));

        // -----------------
        // ITEM REGISTRATION
        // -----------------
        public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Thermodynamix.MODID);

        public static final DeferredItem<BlockItem> KILN_ITEM = ITEMS.registerSimpleBlockItem(KILN);
        public static final DeferredItem<BlockItem> BLAST_BRICKS_ITEM = ITEMS.registerSimpleBlockItem(BLAST_BRICKS);
        public static final DeferredItem<BlockItem> FURNACE_STACK_ITEM = ITEMS.registerSimpleBlockItem(FURNACE_STACK);
        public static final DeferredItem<BlockItem> BLAST_FURNACE_CONTROLLER_ITEM = ITEMS
                        .registerSimpleBlockItem(BLAST_FURNACE_CONTROLLER);
        public static final DeferredItem<Item> STEEL_INGOT_ITEM = ITEMS.registerSimpleItem("steel_ingot",
                        props -> props.stacksTo(64));
        public static final DeferredItem<Item> BLISTER_STEEL_INGOT_ITEM = ITEMS.registerSimpleItem(
                        "blister_steel_ingot",
                        props -> props.stacksTo(64));
        public static final DeferredItem<Item> CAST_IRON_INGOT_ITEM = ITEMS.registerSimpleItem("cast_iron_ingot",
                        props -> props.stacksTo(64));

        // -------------------------
        // CREATIVE TAB REGISTRATION
        // -------------------------
        public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister
                        .create(Registries.CREATIVE_MODE_TAB, Thermodynamix.MODID);

        // Automatically add all the items from the ITEMS registry to this tab.
        public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = CREATIVE_TABS.register(
                        "thermodynamix",
                        () -> CreativeModeTab.builder()
                                        .title(Component.translatable("itemGroup.thermodynamix"))
                                        .icon(() -> STEEL_INGOT_ITEM.get().getDefaultInstance())
                                        .displayItems((params, output) -> ITEMS.getEntries()
                                                        .forEach(item -> output.accept(item.get())))
                                        .build());

        // ----------------------------
        // DATA COMPONENTS REGISTRATION
        // ----------------------------
        public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister
                        .createDataComponents(Registries.DATA_COMPONENT_TYPE, Thermodynamix.MODID);

        public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> TEMPERATURE = DATA_COMPONENTS
                        .registerComponentType("temperature",
                                        builder -> builder.persistent(Codec.FLOAT)
                                                        .networkSynchronized((ByteBufCodecs.FLOAT)));

}
