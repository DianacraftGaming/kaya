package io.github.eggohito.kaya.condition.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.eggohito.kaya.condition.Condition;
import io.github.eggohito.kaya.context.Context;
import io.github.eggohito.kaya.context.ContextValidator;
import io.github.eggohito.kaya.provider.custom.vec3.Vec3Provider;
import io.github.eggohito.kaya.registry.KayaConditionTypes;
import io.github.eggohito.kaya.util.CachedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

public record IsExposedToSkyCondition(Vec3Provider position) implements Condition {

	public static final MapCodec<IsExposedToSkyCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
		.group(Vec3Provider.CODEC.fieldOf("position").forGetter(IsExposedToSkyCondition::position))
		.apply(instance, IsExposedToSkyCondition::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, IsExposedToSkyCondition> STREAM_CODEC = StreamCodec.composite(
		Vec3Provider.STREAM_CODEC, IsExposedToSkyCondition::position,
		IsExposedToSkyCondition::new
	);

	@Override
	public Type<?> getType() {
		return KayaConditionTypes.IS_EXPOSED_TO_SKY;
	}

	@Override
	public boolean test(Context context) {
		Level level = context.level();
		return position().getValue(context.forChild(".position"))
			.map(BlockPos::containing)
			.filter(position -> CachedBlock.validate(level, position))
			.filter(position -> level.canSeeSky(position) && level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, position).getY() <= position.getY())
			.isPresent();
	}

	@Override
	public void validate(ContextValidator validator) {
		Condition.super.validate(validator);
		position().validate(validator.forChild(".position"));
	}

}
