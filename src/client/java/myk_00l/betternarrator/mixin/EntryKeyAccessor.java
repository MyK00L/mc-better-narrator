package myk_00l.betternarrator.mixin;

import net.minecraft.client.gui.narration.NarratedElementType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.client.gui.narration.ScreenNarrationCollector$EntryKey")
public interface EntryKeyAccessor {
    @Accessor("type")
    NarratedElementType betterNarrator$getType();
}
