package net.diprosalik.the_end_of_the_moon;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class HarvestedChorusFlower extends ChorusFlowerBlock {
    // Form auf 12x12x12, beginnend bei Y=0 für bündigen Abschluss mit dem Stamm
    // Von 2.0 auf 0.0 geändert, damit der Block auf dem Stamm aufliegt
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);

    public HarvestedChorusFlower(Settings settings) {
        // Wir casten Blocks.CHORUS_PLANT, damit die interne Logik weiß, woran wir hängen
        super((ChorusPlantBlock) Blocks.CHORUS_PLANT, settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}