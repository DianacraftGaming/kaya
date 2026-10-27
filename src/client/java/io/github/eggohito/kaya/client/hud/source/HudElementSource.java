package io.github.eggohito.kaya.client.hud.source;

import io.github.eggohito.kaya.hud.element.HudElement;
import net.minecraft.world.entity.player.Player;

import java.util.List;

@FunctionalInterface
public interface HudElementSource {
	List<HudElement.WithContext> get(Player viewer, HudElement.RenderPhase renderPhase);
}
