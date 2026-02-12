package net.diprosalik.the_end_of_the_moon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class HarvestedChorusFlower extends Block {
    // Die Form des Blocks: 12x12x12 Pixel, mittig platziert
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 2.0, 2.0, 14.0, 14.0, 14.0);

    public HarvestedChorusFlower(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
