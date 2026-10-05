package thermodynamix.machines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class KilnBlock extends Block implements EntityBlock {

    public KilnBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos position, BlockState state) {
        return new KilnBlockEntity(position, state);
    }
}
