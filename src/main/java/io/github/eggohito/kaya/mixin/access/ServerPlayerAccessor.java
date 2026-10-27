package io.github.eggohito.kaya.mixin.access;

import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ServerPlayer.class)
public interface ServerPlayerAccessor {

    @Invoker
    int callGetCoprime(int spawnArea);

}
