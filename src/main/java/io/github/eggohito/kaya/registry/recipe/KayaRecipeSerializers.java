package io.github.eggohito.kaya.registry.recipe;

import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.recipe.PowerCraftingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class KayaRecipeSerializers {

	public static final PowerCraftingRecipe.Serializer POWER_CRAFTING = registerInternal("power/crafting", new PowerCraftingRecipe.Serializer());

	public static void registerAll() {

	}

	private static <R extends Recipe<?>, S extends RecipeSerializer<R>> S registerInternal(String path, S serializer) {
		return register(Kaya.id(path), serializer);
	}

	public static <R extends Recipe<?>, S extends RecipeSerializer<R>> S register(ResourceLocation id, S serializer) {
		return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id, serializer);
	}

}
