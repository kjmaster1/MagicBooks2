package com.kjmaster.magicbooks2.client;

import com.kjmaster.magicbooks2.MagicBooks2;
import com.kjmaster.magicbooks2.common.capabilities.mana.IMana;
import com.kjmaster.magicbooks2.common.capabilities.mana.ManaProvider;
import com.kjmaster.magicbooks2.common.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HUDHandler {
    private static final ResourceLocation airManaBar = new ResourceLocation(MagicBooks2.MODID, "textures/gui/air_bar.png");
    private static final ResourceLocation arcaneManaBar = new ResourceLocation(MagicBooks2.MODID, "textures/gui/arcane_bar.png");
    private static final ResourceLocation earthManaBar = new ResourceLocation(MagicBooks2.MODID, "textures/gui/earth_bar.png");
    private static final ResourceLocation fireManaBar = new ResourceLocation(MagicBooks2.MODID, "textures/gui/fire_bar.png");
    private static final ResourceLocation waterManaBar = new ResourceLocation(MagicBooks2.MODID, "textures/gui/water_bar.png");

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onDraw(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            ScaledResolution resolution = event.getResolution();
            float partialTicks = event.getPartialTicks();
            drawManaBars(resolution, partialTicks);
        }
    }

    @SideOnly(Side.CLIENT)
    private void drawManaBars(ScaledResolution res, float pticks) {

        double ADJUST = 0.0064;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        ItemStack heldItemStack = getItemInHand(player);
        Item item = heldItemStack.getItem();
        if (isValid(item)) {
            IMana manaCap = player.getCapability(ManaProvider.MANA_CAP, null);
            int airMana = manaCap.getMana("Air");
            int arcaneMana = manaCap.getMana("Arcane");
            int earthMana = manaCap.getMana("Earth");
            int fireMana = manaCap.getMana("Fire");
            int waterMana = manaCap.getMana("Water");

            int width = 8;
            int height = 64;
            int x = 1;
            int y = (res.getScaledHeight() / 2 - height / 2) - 20;
            int textureWidth = 16;
            int textureHeight = 64;

            mc.renderEngine.bindTexture(airManaBar);
            Gui.drawModalRectWithCustomSizedTexture(x, y, 8, 0, width, height, textureWidth, textureHeight);
            int adjustedHeight = (int) (airMana * ADJUST);
            Gui.drawModalRectWithCustomSizedTexture(x, y + (height - adjustedHeight), 0, height - adjustedHeight, width, adjustedHeight, textureWidth, textureHeight);

            x+=10;
            mc.renderEngine.bindTexture(arcaneManaBar);
            Gui.drawModalRectWithCustomSizedTexture(x, y, 8, 0, width, height, textureWidth, textureHeight);
            adjustedHeight = (int) (arcaneMana * ADJUST);
            Gui.drawModalRectWithCustomSizedTexture(x, y + (height - adjustedHeight), 0, height - adjustedHeight, width, adjustedHeight, textureWidth, textureHeight);

            x+=10;
            mc.renderEngine.bindTexture(earthManaBar);
            Gui.drawModalRectWithCustomSizedTexture(x, y, 8, 0, width, height, textureWidth, textureHeight);
            adjustedHeight = (int) (earthMana * ADJUST);
            Gui.drawModalRectWithCustomSizedTexture(x, y + (height - adjustedHeight), 0, height - adjustedHeight, width, adjustedHeight, textureWidth, textureHeight);

            x+=10;
            mc.renderEngine.bindTexture(fireManaBar);
            Gui.drawModalRectWithCustomSizedTexture(x, y, 8, 0, width, height, textureWidth, textureHeight);
            adjustedHeight = (int) (fireMana * ADJUST);
            Gui.drawModalRectWithCustomSizedTexture(x, y + (height - adjustedHeight), 0, height - adjustedHeight, width, adjustedHeight, textureWidth, textureHeight);

            x+=10;
            mc.renderEngine.bindTexture(waterManaBar);
            Gui.drawModalRectWithCustomSizedTexture(x, y, 8, 0, width, height, textureWidth, textureHeight);
            adjustedHeight = (int) (waterMana * ADJUST);
            Gui.drawModalRectWithCustomSizedTexture(x, y + (height - adjustedHeight), 0, height - adjustedHeight, width, adjustedHeight, textureWidth, textureHeight);
        }
    }


    private boolean isValid(Item item) {
       return (item == ModItems.shardPickaxeArcane || item == ModItems.shardPickaxeEarth || item == ModItems.shardPickaxeWater
                || item == ModItems.shardPickaxeAir || item == ModItems.shardPickaxeFire || item == ModItems.Wand);
    }

    private static ItemStack getItemInHand(EntityPlayer player) {
        if (player == null)
            return ItemStack.EMPTY;

        else {
            return  player.getHeldItemMainhand();
        }
    }
}
