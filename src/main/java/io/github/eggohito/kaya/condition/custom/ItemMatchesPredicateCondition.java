package io.github.eggohito.kaya.condition.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.codec.KayaCodecs;
import io.github.eggohito.kaya.codec.KayaStreamCodecs;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.item.ItemProvider;
import io.github.eggohito.kaya.registry.KayaConditionTypes;
import io.github.eggohito.kaya.util.ParsedArgument;
import net.minecraft.commands.arguments.item.ItemPredicateArgument;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record ItemMatchesPredicateCondition(ParsedArgument<ItemPredicateArgument.Result> predicate, ItemProvider item) implements Condition {

	public static final MapCodec<ItemMatchesPredicateCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		KayaCodecs.ITEM_PREDICATE.fieldOf("predicate").forGetter(ItemMatchesPredicateCondition::predicate),
		ItemProvider.CODEC.fieldOf("item").forGetter(ItemMatchesPredicateCondition::item)
	).apply(instance, ItemMatchesPredicateCondition::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, ItemMatchesPredicateCondition> STREAM_CODEC = StreamCodec.composite(
		KayaStreamCodecs.ITEM_PREDICATE, ItemMatchesPredicateCondition::predicate,
		ItemProvider.STREAM_CODEC, ItemMatchesPredicateCondition::item,
		ItemMatchesPredicateCondition::new
	);

	@Override
	public Type<?> getType() {
		return KayaConditionTypes.ITEM_MATCHES_PREDICATE;
	}

	@Override
	public boolean test(Context context) {
		return item().getValue(context.forChild(".item"))
			.map(predicate().argument()::test)
			.orElse(false);
	}

	@Override
	public void validate(ContextValidator validator) {
		Condition.super.validate(validator);
		item().validate(validator.forChild(".item"));
	}

}
