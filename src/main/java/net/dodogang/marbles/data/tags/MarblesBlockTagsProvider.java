package net.dodogang.marbles.data.tags;

import me.andante.chord.block.helper.WoodBlocks;
import net.dodogang.marbles.init.MarblesBlocks;
import net.dodogang.marbles.tag.MarblesBlockTags;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.AbstractTagProvider;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.nio.file.Path;

public class MarblesBlockTagsProvider extends AbstractTagProvider<Block> {
    public MarblesBlockTagsProvider(DataGenerator gen) {
        super(gen, Registry.BLOCK);
    }

    @Override
    protected void configure() {
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
            .addTag(MarblesBlockTags.ASPEN_LOGS)
            .addTag(MarblesBlockTags.HOOPSI_SPRUCE_LOGS);

        getOrCreateTagBuilder(BlockTags.BAMBOO_PLANTABLE_ON)
            .add(MarblesBlocks.YELLOW_BAMBOO)
            .add(MarblesBlocks.YELLOW_BAMBOO_SAPLING);

        getOrCreateTagBuilder(BlockTags.CLIMBABLE)
            .add(MarblesBlocks.YELLOW_SCAFFOLDING);
    }

    protected void addWoodSet(WoodBlocks blocks) {
        getOrCreateTagBuilder(BlockTags.FENCE_GATES);
    }

    protected Tag.Builder getTagBuilder(Tag.Identified<Block> identified) {
        return super.method_27169(identified);
    }

    @Override
    protected Path getOutput(Identifier id) {
        return root.getOutput().resolve("data/" + id.getNamespace() + "/tags/blocks/" + id.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "MarblesBlockTags";
    }
}
