package net.diprosalik.the_end_of_the_moon;

import net.diprosalik.the_end_of_the_moon.particle.ModParticles;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class ChorusShroomBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 6.0, 14.0);

    public ChorusShroomBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isOf(Blocks.END_STONE);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING); // Das verhindert den Absturz!
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        // Radius für die Spieler-Erkennung (bleibt bei ca. 2-3 Blöcken sinnvoll)
        double detectionRange = 2.0;
        if (world.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, detectionRange, false) != null) {

            // Wir erhöhen die Anzahl der Versuche etwas, da das Gebiet größer ist
            if (random.nextInt(2) == 0) {
                // 3x3x3 Bereich berechnen:
                // pos.getX() + 0.5 ist die Mitte.
                // (random.nextDouble() - 0.5) * 3.0 ergibt einen Wert zwischen -1.5 und +1.5.
                double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5);
                double y = pos.getY() + 0.5 + (random.nextDouble() - 0.5);
                double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5);

                // Wichtig: 0.0 Geschwindigkeit, damit sie ruhig an dieser Stelle in der Luft erscheinen
                world.addParticle(ModParticles.CHORUS_SHROOM_PARTICLE, x, y, z, 0.0, 0.0, 0.0);
            }
        }
    }
}