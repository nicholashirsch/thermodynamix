package thermodynamix.machines;

import com.lowdragmc.lowdraglib2.gui.factory.BlockUIMenuType;
import com.lowdragmc.lowdraglib2.gui.ui.ModularUI;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Kiln which can be used for pyrolysis and other thermal processes.
 * 
 * NOTE: This class is overly commented as a template for making other blocks in the future.
 */
public class KilnBlock extends Block implements EntityBlock, BlockUIMenuType.BlockUI {
    /** The horizontal facing direction of the kiln, property is copied from Vanilla furnaces. */
    public static final EnumProperty<Direction> HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

    /**
     * Standard block constructor. This is called when the registry creates this block
     * 
     * @param properties The block's properties, such as mining speed and minimum mining level. This is set in the
     *                   block's registry entry.
     */
    public KilnBlock(BlockBehaviour.Properties properties) {
        // Passes the properties from the registry to the parent class.
        super(properties);
        // registerDefaultState(stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    /**
     * Creates the block state definition for this block.
     * 
     * @param builder Takes in a block state definition, such as HORIZONTAL_FACING, and automatically builds and
     *                registers all available states and store's them in the block's stateDefinition.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    /**
     * Whenever a block is placed in the world Minecraft assigns it a corresponding entity if the block has one (and the
     * kiln does). If so, it calls this function.
     * 
     * @param position The position of the block in the world, required param.
     * @param state    The current BlockState of the block, required param.
     * @return A new instance of the block entity associated with this block.
     */
    @Override
    public BlockEntity newBlockEntity(BlockPos position, BlockState state) {
        return new KilnBlockEntity(position, state);
    }

    /**
     * Called whenver the player places this block and used to update the BlockState.
     * 
     * @param context A BlockPaceContext class holding a bunch of information regarding the block and player's state
     *                when the block was placed.
     * @return The BlockState that should be set when placing the block.
     */
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // Set the block to face the player. Needed for blocks that have an asymmetric texture.
        return defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    /**
     * Called whenver the player right clicks this block. For our purposes used to open the kiln's GUI.
     * 
     * @param state  The current BlockState of the block.
     * @param level  The Level in which the block exists.
     * @param pos    The position of the block in the world. Needed because in Minecraft blocks don't store data,
     *               BlockEntities do. Since those are indexed based on position this method needs the position to
     *               access the correct BlockEntity.
     * @param player The player who is interacting with the block.
     * @param hit    A BlockHitResult indicating where the player clicked on the block.
     * @return An InteractionResult indicating whether the interaction was successful.
     */
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player,
            BlockHitResult hit) {

        // When the player right clicks a block, this method is called by both the client version of the player and the
        // server version of the player. However, since the kiln has an internal server state we need to ensure on the
        // server version of the player's click goes through to prevent desyncs.
        //
        // The client still sees the menu because the server generates it and then sends it a view to the client.
        if (player instanceof ServerPlayer serverPlayer) {
            BlockUIMenuType.openUI(serverPlayer, pos); // Open the LDLib GUI for this block.
        }

        return InteractionResult.SUCCESS;
    }

    /**
     * Builds the LDLib GUI for this block.
     * 
     * @param holder Passed in to get BlockEntity corresponding to the block (via position) as well as to pass
     *               information to the GUI for display.
     * @return The ModularUI instance representing the GUI for this block.
     */
    @Override
    public ModularUI createUI(BlockUIMenuType.BlockUIHolder holder) {
        return ((KilnBlockEntity) holder.player.level().getBlockEntity(holder.pos)).createUI(holder);
    }

    // @Override
    // public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
    // BlockEntityType<T> type) {
    // return level.isClientSide() ? null : (l, pos, s, blockEntity) -> ((KilnBlockEntity) blockEntity).serverTick();
    // }
}
