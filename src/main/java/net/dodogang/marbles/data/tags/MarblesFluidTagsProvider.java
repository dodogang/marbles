package net.dodogang.marbles.data.tags;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.AbstractTagProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.nio.file.Path;

public class MarblesFluidTagsProvider extends AbstractTagProvider<Fluid> {
    public MarblesFluidTagsProvider(DataGenerator gen) {
        super(gen, Registry.FLUID);
    }

    @Override
    protected void configure() {
    }

    @Override
    protected Path getOutput(Identifier id) {
        return root.getOutput().resolve("data/" + id.getNamespace() + "/tags/fluids/" + id.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "MarblesFluidTags";
    }
}
