package dev.jeiarmortrimtab.mixin;

import dev.jeiarmortrimtab.ArmorTrimJeiPlugin;

import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.library.recipes.RecipeManagerInternal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.stream.Stream;

@Mixin(value = RecipeManagerInternal.class, remap = false)
abstract class RecipeCategoryOrderMixin {
    @Inject(method = "getRecipeCategoriesForTypes", at = @At("RETURN"), cancellable = true, require = 0)
    private void jeiArmorTrimTab$groupSmithingTabs(CallbackInfoReturnable<Stream<IRecipeCategory<?>>> callback) {
        var categories = new ArrayList<>(callback.getReturnValue().toList());
        IRecipeCategory<?> trims = null;
        int smithingIndex = -1;
        int trimIndex = -1;
        for (int index = 0; index < categories.size(); index++) {
            var category = categories.get(index);
            var type = category.getRecipeType();
            if (RecipeTypes.SMITHING.equals(type)) {
                smithingIndex = index;
            } else if (ArmorTrimJeiPlugin.TRIMS.equals(type)) {
                trims = category;
                trimIndex = index;
            }
        }
        if (smithingIndex >= 0 && trimIndex >= 0 && trimIndex != smithingIndex + 1) {
            categories.remove(trimIndex);
            categories.add(trimIndex < smithingIndex ? smithingIndex : smithingIndex + 1, trims);
        }
        callback.setReturnValue(categories.stream());
    }
}
