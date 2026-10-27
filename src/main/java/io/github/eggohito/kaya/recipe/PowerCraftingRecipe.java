package io.github.eggohito.kaya.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.codec.KayaMapCodecs;
import io.github.eggohito.kaya.codec.KayaStreamCodecs;
import io.github.eggohito.kaya.duck.internal.PowerCrafting;
import io.github.eggohito.kaya.power.PowerIdentifier;
import io.github.eggohito.kaya.power.custom.CraftingRecipePower;
import io.github.eggohito.kaya.power.entity.Powers;
import io.github.eggohito.kaya.registry.recipe.KayaRecipeBookCategories;
import io.github.eggohito.kaya.registry.recipe.KayaRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public record PowerCraftingRecipe(PowerIdentifier power, CraftingRecipe delegate) implements CraftingRecipe {

	@Override
	public boolean matches(CraftingInput input, Level world) {

		if (!(input instanceof PowerCrafting powerCrafting)) {
			return false;
		}

		CraftingRecipe powerDefinedRecipe = Powers.getOptional(powerCrafting.kaya$getEntity())
			.flatMap(powers -> powers.getOptionalInstance(this.power()))
			.filter(CraftingRecipePower.Instance.class::isInstance)
			.map(CraftingRecipePower.Instance.class::cast)
			.map(CraftingRecipePower.Instance::recipe)
			.map(RecipeHolder::value)
			.orElse(null);

		return Objects.equals(delegate(), powerDefinedRecipe)
			&& delegate().matches(input, world);

	}

	@Override
	public @NotNull ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
		return delegate().assemble(recipeInput, provider);
	}

	@Override
	public @NotNull RecipeSerializer<? extends CraftingRecipe> getSerializer() {
		return KayaRecipeSerializers.POWER_CRAFTING;
	}

	@Override
	public @NotNull PlacementInfo placementInfo() {
		return delegate().placementInfo();
	}

	@Override
	public @NotNull CraftingBookCategory category() {
		return delegate().category();
	}

	@Override
	public @NotNull RecipeBookCategory recipeBookCategory() {
		return KayaRecipeBookCategories.POWER_CRAFTING_RECIPE;
	}

	@Override
	public boolean isSpecial() {
		return delegate().isSpecial();
	}

	@Override
	public boolean showNotification() {
		return delegate().showNotification();
	}

	@Override
	public @NotNull List<RecipeDisplay> display() {
		return delegate().display();
	}

	public static class Serializer implements RecipeSerializer<PowerCraftingRecipe> {

		public static final MapCodec<PowerCraftingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			PowerIdentifier.CODEC.fieldOf("power").forGetter(PowerCraftingRecipe::power),
			KayaMapCodecs.CRAFTING_RECIPE.codec().fieldOf("recipe").forGetter(PowerCraftingRecipe::delegate)
		).apply(instance, PowerCraftingRecipe::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, PowerCraftingRecipe> STREAM_CODEC = StreamCodec.composite(
			PowerIdentifier.STREAM_CODEC, PowerCraftingRecipe::power,
			KayaStreamCodecs.CRAFTING_RECIPE, PowerCraftingRecipe::delegate,
			PowerCraftingRecipe::new
		);

		@Override
		public @NotNull MapCodec<PowerCraftingRecipe> codec() {
			return CODEC;
		}

		@Override
		public @NotNull StreamCodec<RegistryFriendlyByteBuf, PowerCraftingRecipe> streamCodec() {
			return STREAM_CODEC;
		}

	}

}
