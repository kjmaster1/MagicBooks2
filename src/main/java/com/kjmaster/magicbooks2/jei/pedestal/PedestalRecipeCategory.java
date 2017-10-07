package com.kjmaster.magicbooks2.jei.pedestal;

import com.kjmaster.magicbooks2.MagicBooks2;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.util.ResourceLocation;

public class PedestalRecipeCategory implements IRecipeCategory<PedestalRecipeWrapper> {

    public static final String NAME = MagicBooks2.MODID + ".pedestal";
    private final IDrawable background;

    public PedestalRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(new ResourceLocation(MagicBooks2.MODID,
                "textures/gui/jei/pedestal.png"), 0, 0, 135, 80);
    }

    @Override
    public String getUid() {
        return NAME;
    }

    @Override
    public String getTitle() {
        return "Pedestal Recipe";
    }

    @Override
    public String getModName() {
        return MagicBooks2.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PedestalRecipeWrapper recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 31, 31);
        recipeLayout.getItemStacks().set(0, recipeWrapper.pedestalRecipe.input);

        recipeLayout.getItemStacks().init(1, true, 1, 31);
        recipeLayout.getItemStacks().set(1, recipeWrapper.pedestalRecipe.waterStack);

        recipeLayout.getItemStacks().init(2, true, 31, 1);
        recipeLayout.getItemStacks().set(2, recipeWrapper.pedestalRecipe.airStack);

        recipeLayout.getItemStacks().init(3, true, 61, 31);
        recipeLayout.getItemStacks().set(3, recipeWrapper.pedestalRecipe.earthStack);

        recipeLayout.getItemStacks().init(4, true, 31, 61);
        recipeLayout.getItemStacks().set(4, recipeWrapper.pedestalRecipe.fireStack);

        recipeLayout.getItemStacks().init(5, false, 112, 31);
        recipeLayout.getItemStacks().set(5, recipeWrapper.pedestalRecipe.output);
    }
}
