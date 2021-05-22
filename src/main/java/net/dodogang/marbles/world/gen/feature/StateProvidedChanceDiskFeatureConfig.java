package net.dodogang.marbles.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class StateProvidedChanceDiskFeatureConfig implements FeatureConfig {
    public static final Codec<StateProvidedChanceDiskFeatureConfig> CODEC = RecordCodecBuilder.create(
        (instance) -> instance.group(
            BlockStateProvider.TYPE_CODEC
                .fieldOf("state_provider").forGetter((config) -> config.stateProvider),
            UniformIntDistribution.createValidatedCodec(0, 4, 3)
                .fieldOf("radius").forGetter((config) -> config.radius),
            Codec.intRange(0, 4)
                .fieldOf("half_height").forGetter((config) -> config.halfHeight),
            Codec.floatRange(0.0f, 1.0f)
                .fieldOf("chance").forGetter((config) -> config.chance),
            BlockState.CODEC.listOf()
                .fieldOf("targets").forGetter((config) -> config.targets)
        ).apply(instance, StateProvidedChanceDiskFeatureConfig::new)
    );

    public final BlockStateProvider stateProvider;
    public final UniformIntDistribution radius;
    public final int halfHeight;
    public final float chance;
    public final List<BlockState> targets;

    public StateProvidedChanceDiskFeatureConfig(BlockStateProvider stateProvider, UniformIntDistribution radius, int halfHeight, float chance, List<BlockState> targets) {
        this.stateProvider = stateProvider;
        this.radius = radius;
        this.halfHeight = halfHeight;
        this.chance = chance;
        this.targets = targets;
    }
}
