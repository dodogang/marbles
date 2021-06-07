package net.dodogang.marbles.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public record StateProvidedChanceDiskFeatureConfig(BlockStateProvider stateProvider, IntProvider radius, int halfHeight, float chance, List<BlockState> targets) implements FeatureConfig {
    public static final Codec<StateProvidedChanceDiskFeatureConfig> CODEC = RecordCodecBuilder.create(
        (instance) -> instance.group(
            BlockStateProvider.TYPE_CODEC
                .fieldOf("state_provider")
                .forGetter((config) -> config.stateProvider),
            IntProvider
                .createValidatingCodec(0, 8)
                .fieldOf("radius")
                .forGetter((config) -> config.radius),
            Codec.intRange(0, 4)
                 .fieldOf("half_height")
                 .forGetter((config) -> config.halfHeight),
            Codec.floatRange(0.0f, 1.0f)
                 .fieldOf("chance")
                 .forGetter((config) -> config.chance),
            BlockState.CODEC
                .listOf()
                .fieldOf("targets")
                .forGetter((config) -> config.targets)
        ).apply(instance, StateProvidedChanceDiskFeatureConfig::new)
    );
}
