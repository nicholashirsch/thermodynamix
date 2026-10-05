package thermodynamix.machines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import thermodynamix.core.DeferredRegistry;

public class KilnBlockEntity extends BlockEntity {

    public KilnBlockEntity(BlockPos position, BlockState state) {
        super(DeferredRegistry.KILN_BLOCK_ENTITY.get(), position, state);
    }
}
