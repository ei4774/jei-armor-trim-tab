package dev.jeiarmortrimtab.mixin;

import dev.jeiarmortrimtab.ArmorTrimJeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.library.plugins.vanilla.VanillaPlugin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = VanillaPlugin.class, remap = false)
abstract class SmithingClickAreaMixin {
    @ModifyArg(method = "registerGuiHandlers", at = @At(value = "INVOKE", target =
            "Lmezz/jei/api/registration/IGuiHandlerRegistration;addRecipeClickArea(Ljava/lang/Class;IIII[Lmezz/jei/api/recipe/RecipeType;)V"),
            index = 5, require = 0)
    private RecipeType<?>[] jeiArmorTrimTab$includeTrims(RecipeType<?>[] types) {
        if (types.length == 1 && RecipeTypes.SMITHING.equals(types[0])) {
            return new RecipeType<?>[]{RecipeTypes.SMITHING, ArmorTrimJeiPlugin.TRIMS};
        }
        return types;
    }
}
