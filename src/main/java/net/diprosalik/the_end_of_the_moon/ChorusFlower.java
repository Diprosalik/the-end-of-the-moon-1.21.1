package net.diprosalik.the_end_of_the_moon;

import net.diprosalik.the_end_of_the_moon.block.ModBlock;
import net.diprosalik.the_end_of_the_moon.item.ModItems;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class ChorusFlower {

    public static void ClickOnFlowerToHarvest(){
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);
            ItemStack stack = player.getStackInHand(hand);

            if (state.isOf(Blocks.CHORUS_FLOWER) && stack.isOf(Items.GLASS_BOTTLE)) {

                int age = state.get(ChorusFlowerBlock.AGE);

                if (age >= 5) {

                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.BLOCKS, 1.0f, 1.0f);

                    if (!player.getAbilities().creativeMode) {
                        stack.decrement(1);
                    }

                    player.getInventory().insertStack(new ItemStack(ModItems.CHORUS_FRUIT_GAS_BOTTLE));

                    world.setBlockState(pos, ModBlock.CHORUS_FLOWER_HARVESTED.getDefaultState());

                    return ActionResult.SUCCESS;
                } else {
                    return ActionResult.CONSUME;
                }
            }
            return ActionResult.PASS;
        });
    }
}
