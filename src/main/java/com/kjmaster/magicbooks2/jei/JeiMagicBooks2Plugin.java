package com.kjmaster.magicbooks2.jei;

import com.kjmaster.magicbooks2.common.init.ModBlocks;
import com.kjmaster.magicbooks2.common.recipe.PedestalHandler;
import com.kjmaster.magicbooks2.common.recipe.PedestalRecipe;
import com.kjmaster.magicbooks2.jei.pedestal.PedestalRecipeCategory;
import com.kjmaster.magicbooks2.jei.pedestal.PedestalRecipeWrapper;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JeiMagicBooks2Plugin implements IModPlugin {

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {

    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers helpers = registry.getJeiHelpers();
        registry.addRecipeCategories(new PedestalRecipeCategory(helpers.getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(PedestalRecipe.class, PedestalRecipeWrapper::new, PedestalRecipeCategory.NAME);
        registry.addRecipes(PedestalHandler.PEDESTAL_RECIPES, PedestalRecipeCategory.NAME);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.pedestal, 1, 4), PedestalRecipeCategory.NAME);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

    }
}
