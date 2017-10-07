package com.kjmaster.magicbooks2.jei.pedestal;

import com.kjmaster.magicbooks2.common.recipe.PedestalRecipe;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.Arrays;

public class PedestalRecipeWrapper implements IRecipeWrapper {

    public final PedestalRecipe pedestalRecipe;

    public PedestalRecipeWrapper(PedestalRecipe recipe) {
        this.pedestalRecipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, Arrays.asList(pedestalRecipe.input, pedestalRecipe.airStack,
                pedestalRecipe.earthStack, pedestalRecipe.fireStack, pedestalRecipe.waterStack));
        ingredients.setOutput(ItemStack.class, pedestalRecipe.output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        String earthCost = Integer.toString(pedestalRecipe.earthManaCost);
        String waterCost = Integer.toString(pedestalRecipe.waterManaCost);
        String airCost = Integer.toString(pedestalRecipe.airManaCost);
        String fireCost = Integer.toString(pedestalRecipe.fireManaCost);
        minecraft.fontRenderer.drawString(earthCost, recipeWidth - 65 - getLengthForRender(earthCost, minecraft), recipeHeight - 60, Color.GREEN.getRGB());
        minecraft.fontRenderer.drawString(waterCost, recipeWidth - 125 - getLengthForRender(waterCost, minecraft), recipeHeight - 60, Color.BLUE.getRGB());
        minecraft.fontRenderer.drawString(airCost, recipeWidth - 95 - getLengthForRender(airCost, minecraft), recipeHeight - 90, Color.YELLOW.getRGB());
        minecraft.fontRenderer.drawString(fireCost, recipeWidth - 95 - getLengthForRender(fireCost, minecraft), recipeHeight + 2, Color.ORANGE.getRGB());
    }

    private int getLengthForRender(String msg, Minecraft minecraft) {
        return minecraft.fontRenderer.getStringWidth(msg) / 2;
    }
}