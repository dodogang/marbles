package net.dodogang.marbles.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public record PermafrostIceSpikeFeatureConfig(BlockStateProvider stateProvider, List<BlockState> targets) implements FeatureConfig {
    public static final Codec<PermafrostIceSpikeFeatureConfig> CODEC = RecordCodecBuilder.create(
        (instance) -> instance.group(
            BlockStateProvider.TYPE_CODEC
                .fieldOf("state_provider")
                .forGetter((config) -> config.stateProvider),
            BlockState.CODEC
                .listOf()
                .fieldOf("targets")
                .forGetter((config) -> config.targets)
        ).apply(instance, PermafrostIceSpikeFeatureConfig::new)
    );
}
