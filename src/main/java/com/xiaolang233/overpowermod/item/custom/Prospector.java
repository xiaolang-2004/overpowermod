package com.xiaolang233.overpowermod.item.custom;

import com.xiaolang233.overpowermod.sounds.ModSounds;
import com.xiaolang233.overpowermod.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Prospector extends Item {
    public Prospector(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            BlockPos blockPos = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            boolean foundBlock = false;
            for (int i = -64;i <= blockPos.getY() +64; i++){
                BlockState state = context.getWorld().getBlockState(blockPos.down(i));
                if (isRightBlock(state)){
                    outputMessage(blockPos.down(i),player,state.getBlock());
                    foundBlock = true;
                    context.getWorld().playSound(null,blockPos, ModSounds.PROSPECTOR_CALL,
                            SoundCategory.BLOCKS,1f,1f);
                    break;
                }
            }
            if (!foundBlock){
                player.sendMessage(Text.of("Block not found"));
            }
        }
        context.getStack().damage(1,context.getPlayer(),
                PlayerEntity -> PlayerEntity.sendToolBreakStatus(PlayerEntity.getActiveHand()));
        return ActionResult.SUCCESS;
    }

    private void outputMessage(BlockPos blockPos, PlayerEntity player, Block block) {
        player.sendMessage(Text.literal("Found" + block.asItem().getName().getString()+ blockPos.getX() + blockPos.getY()  + blockPos.getZ()), false) ;
    }

    private boolean isRightBlock(BlockState state){
        return state.isIn(ModTags.Blocks.PROSPECTOR_LIST);
    }
}
