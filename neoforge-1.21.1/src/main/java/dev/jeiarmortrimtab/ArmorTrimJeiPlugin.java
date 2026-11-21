package dev.jeiarmortrimtab;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingTrimRecipe;
import net.minecraft.world.level.block.Blocks;

import java.util.Set;
import java.util.stream.Collectors;

@JeiPlugin
public final class ArmorTrimJeiPlugin implements IModPlugin {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("jei_armor_trim_tab", "armor_trim");
    public static final RecipeType<RecipeHolder<SmithingRecipe>> TRIMS = RecipeType.createRecipeHolderType(ID);

    private ArmorTrimCategory category;

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        category = new ArmorTrimCategory(registration.getJeiHelpers().getGuiHelper());
        registration.addRecipeCategories(category);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalysts(TRIMS, Blocks.SMITHING_TABLE);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(SmithingMenu.class, MenuType.SMITHING, TRIMS, 0, 3, 4, 36);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {
        IRecipeManager manager = runtime.getRecipeManager();
        category.setSmithingCategory(manager.getRecipeCategory(RecipeTypes.SMITHING));
        var trims = manager.createRecipeLookup(RecipeTypes.SMITHING).get()
                .filter(holder -> holder.value() instanceof SmithingTrimRecipe)
                .toList();
        if (trims.isEmpty()) {
            return;
        }

        manager.addRecipes(TRIMS, trims);
        Set<RecipeHolder<SmithingRecipe>> moved = manager.createRecipeLookup(TRIMS).get()
                .collect(Collectors.toSet());
        manager.hideRecipes(RecipeTypes.SMITHING, trims.stream().filter(moved::contains).toList());
    }

    @Override
    public void onRuntimeUnavailable() {
        category = null;
    }
}
