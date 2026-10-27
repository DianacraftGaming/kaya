package io.github.eggohito.kaya.registry.recipe;

import io.github.eggohito.kaya.Kaya;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeBookCategory;

import java.util.Locale;

public class KayaRecipeBookCategories {

	public static final RecipeBookCategory POWER_CRAFTING_RECIPE = registerInternal("power/crafting_recipe");

	public static void registerAll() {

	}

	private static RecipeBookCategory registerInternal(String path) {
		return register(Kaya.id(path), new RecipeBookCategory() {

			@Override
			public String toString() {
				return path.toUpperCase(Locale.ROOT);
			}

		});
	}

	public static <C extends RecipeBookCategory> C register(ResourceLocation id, C category) {
		return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, id, category);
	}

}
