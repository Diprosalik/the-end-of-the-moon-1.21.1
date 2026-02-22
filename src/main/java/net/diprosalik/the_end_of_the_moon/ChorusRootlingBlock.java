package net.diprosalik.the_end_of_the_moon;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class ChorusRootlingBlock extends Block {

    private static final VoxelShape SHAPE = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 6.0, 10.0);

    public ChorusRootlingBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }


    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        // Kann nur auf Endstein platziert werden
        return world.getBlockState(pos.down()).isOf(Blocks.END_STONE);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // Verwandelt sich mit einer Chance von ca. 20% pro Random Tick in eine Chorus Blume
        if (random.nextInt(5) == 0) {
            world.setBlockState(pos, Blocks.CHORUS_FLOWER.getDefaultState());
        }
    }
}
