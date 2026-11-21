package dev.jeiarmortrimtab;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingTrimRecipe;

import java.util.List;

final class ArmorTrimCategory extends AbstractRecipeCategory<SmithingRecipe> {
    private IRecipeCategory<SmithingRecipe> smithingCategory;

    ArmorTrimCategory(IGuiHelper guiHelper) {
        super(ArmorTrimJeiPlugin.TRIMS, Component.translatable("jei_armor_trim_tab.category.armor_trim"),
                guiHelper.createDrawableItemLike(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE), 108, 28);
    }

    void setSmithingCategory(IRecipeCategory<SmithingRecipe> smithingCategory) {
        this.smithingCategory = smithingCategory;
    }

    @Override
    public boolean isHandled(SmithingRecipe recipe) {
        return recipe instanceof SmithingTrimRecipe;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SmithingRecipe recipe, IFocusGroup focuses) {
        smithingCategory.setRecipe(builder, recipe, focuses);
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, SmithingRecipe recipe, IFocusGroup focuses) {
        smithingCategory.createRecipeExtras(builder, recipe, focuses);
    }

    @Override
    public void draw(SmithingRecipe recipe, IRecipeSlotsView slots, GuiGraphics graphics, double mouseX, double mouseY) {
        smithingCategory.draw(recipe, slots, graphics, mouseX, mouseY);
    }

    @Override
    public void onDisplayedIngredientsUpdate(SmithingRecipe recipe, List<IRecipeSlotDrawable> slots,
                                             IFocusGroup focuses) {
        smithingCategory.onDisplayedIngredientsUpdate(recipe, slots, focuses);
    }

    @Override
    public ResourceLocation getRegistryName(SmithingRecipe recipe) {
        return smithingCategory.getRegistryName(recipe);
    }
}
