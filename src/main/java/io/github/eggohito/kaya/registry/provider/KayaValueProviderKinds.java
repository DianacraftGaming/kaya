package io.github.eggohito.kaya.registry.provider;

import com.mojang.serialization.Codec;
import io.github.eggohito.kaya.Kaya;
import io.github.eggohito.kaya.context.parameter.ContextParameter;
import io.github.eggohito.kaya.provider.ValueProvider;
import io.github.eggohito.kaya.provider.custom.block.BlockProvider;
import io.github.eggohito.kaya.provider.custom.bool.BooleanProvider;
import io.github.eggohito.kaya.provider.custom.box.BoxProvider;
import io.github.eggohito.kaya.provider.custom.command_source.CommandSourceProvider;
import io.github.eggohito.kaya.provider.custom.damage_source.DamageSourceProvider;
import io.github.eggohito.kaya.provider.custom.direction.DirectionProvider;
import io.github.eggohito.kaya.provider.custom.effect.EffectProvider;
import io.github.eggohito.kaya.provider.custom.entity.EntityProvider;
import io.github.eggohito.kaya.provider.custom.item.ItemProvider;
import io.github.eggohito.kaya.provider.custom.nbt.NbtProvider;
import io.github.eggohito.kaya.provider.custom.number.FloatProvider;
import io.github.eggohito.kaya.provider.custom.number.IntProvider;
import io.github.eggohito.kaya.provider.custom.slot.SlotProvider;
import io.github.eggohito.kaya.provider.custom.string.StringProvider;
import io.github.eggohito.kaya.provider.custom.vec3.Vec3Provider;
import io.github.eggohito.kaya.registry.KayaRegistries;
import io.github.eggohito.kaya.registry.context.KayaContextParameterTypes;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public final class KayaValueProviderKinds {

	public static final ValueProvider.Kind<BlockProvider> BLOCK = registerInternal("block", BlockProvider.CODEC, BlockProvider.STREAM_CODEC, KayaContextParameterTypes.BLOCK);
	public static final ValueProvider.Kind<BooleanProvider> BOOLEAN = registerInternal("boolean", BooleanProvider.CODEC, BooleanProvider.STREAM_CODEC, KayaContextParameterTypes.BOOLEAN);
	public static final ValueProvider.Kind<BoxProvider> BOX = registerInternal("box", BoxProvider.CODEC, BoxProvider.STREAM_CODEC, KayaContextParameterTypes.BOX);
	public static final ValueProvider.Kind<CommandSourceProvider> COMMAND_SOURCE = registerInternal("command_source", CommandSourceProvider.CODEC, CommandSourceProvider.STREAM_CODEC, KayaContextParameterTypes.COMMAND_SOURCE);
	public static final ValueProvider.Kind<DamageSourceProvider> DAMAGE_SOURCE = registerInternal("damage_source", DamageSourceProvider.CODEC, DamageSourceProvider.STREAM_CODEC, KayaContextParameterTypes.DAMAGE_SOURCE);
	public static final ValueProvider.Kind<DirectionProvider> DIRECTION = registerInternal("direction", DirectionProvider.CODEC, DirectionProvider.STREAM_CODEC, KayaContextParameterTypes.DIRECTION);
	public static final ValueProvider.Kind<EffectProvider> EFFECT = registerInternal("effect", EffectProvider.CODEC, EffectProvider.STREAM_CODEC, KayaContextParameterTypes.EFFECT);
	public static final ValueProvider.Kind<EntityProvider> ENTITY = registerInternal("entity", EntityProvider.CODEC, EntityProvider.STREAM_CODEC, KayaContextParameterTypes.ENTITY);
	public static final ValueProvider.Kind<FloatProvider> FLOAT = registerInternal("float", FloatProvider.CODEC, FloatProvider.STREAM_CODEC, KayaContextParameterTypes.FLOAT);
	public static final ValueProvider.Kind<IntProvider> INT = registerInternal("int", IntProvider.CODEC, IntProvider.STREAM_CODEC, KayaContextParameterTypes.INT);
	public static final ValueProvider.Kind<ItemProvider> ITEM = registerInternal("item", ItemProvider.CODEC, ItemProvider.STREAM_CODEC, KayaContextParameterTypes.ITEM);
	public static final ValueProvider.Kind<NbtProvider> NBT = registerInternal("nbt", NbtProvider.CODEC, NbtProvider.STREAM_CODEC, KayaContextParameterTypes.NBT);
	public static final ValueProvider.Kind<SlotProvider> SLOT = registerInternal("slot", SlotProvider.CODEC, SlotProvider.STREAM_CODEC, KayaContextParameterTypes.SLOT);
	public static final ValueProvider.Kind<StringProvider> STRING = registerInternal("string", StringProvider.CODEC, StringProvider.STREAM_CODEC, KayaContextParameterTypes.STRING);
	public static final ValueProvider.Kind<Vec3Provider> VEC3 = registerInternal("vec3", Vec3Provider.CODEC, Vec3Provider.STREAM_CODEC, KayaContextParameterTypes.VEC3);

	public static void registerAll() {
		ValueProvider.Kind.ALIASES.addPathAlias("integer", INT);
		ValueProvider.Kind.ALIASES.addPathAlias("vector_3", VEC3);
	}

	public static <V, Provider extends ValueProvider<V>, Parameter extends ContextParameter<V>> ValueProvider.Kind<Provider> register(ResourceLocation id, Codec<Provider> codec, StreamCodec<RegistryFriendlyByteBuf, Provider> streamCodec, ContextParameter.Type<Parameter> parameterType) {
		return Registry.register(KayaRegistries.VALUE_PROVIDER_KIND, id, ValueProvider.Kind.of(codec, streamCodec, parameterType));
	}

	private static <V, Provider extends ValueProvider<V>, Parameter extends ContextParameter<V>> ValueProvider.Kind<Provider> registerInternal(String path, Codec<Provider> codec, StreamCodec<RegistryFriendlyByteBuf, Provider> streamCodec, ContextParameter.Type<Parameter> parameterType) {
		return register(Kaya.id(path), codec, streamCodec, parameterType);
	}

}
