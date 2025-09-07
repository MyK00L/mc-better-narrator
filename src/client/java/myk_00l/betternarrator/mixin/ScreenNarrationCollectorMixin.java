package myk_00l.betternarrator.mixin;

import net.minecraft.client.gui.narration.ScreenNarrationCollector;
import net.minecraft.client.gui.narration.NarratedElementType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Map;

@Mixin(ScreenNarrationCollector.class)
public class ScreenNarrationCollectorMixin {
    
    @Shadow
    private Map<?, ?> entries;
    
    @Inject(method = "collectNarrationText", at = @At("HEAD"), cancellable = true)
    private void filterNarrationText(boolean bl, CallbackInfoReturnable<String> cir) {
        // Filter entries to only include TITLE type narrations
        this.entries.entrySet().removeIf(entry -> {
            Object key = entry.getKey();
            EntryKeyAccessor keyAccessor = (EntryKeyAccessor) key;
            NarratedElementType type = keyAccessor.betterNarrator$getType();
            return type != NarratedElementType.TITLE;
        });
    }
}
