
package myk_00l.betternarrator.mixin;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jetbrains.annotations.Nullable;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin<T extends AbstractContainerMenu> {
    
    @Shadow
    @Nullable
    protected Slot hoveredSlot;

    // private static final String MOD_ID = "better-narrator";
    // private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    private Slot previousHoveredSlot = null;

    private void onStartHovering(Slot slot) {
        
        if (slot != null && slot.hasItem()) {
            // LOGGER.info("Started hovering over slot: {}", slot.index);
            ItemStack itemStack = slot.getItem();
            GameNarrator narrator = Minecraft.getInstance().getNarrator();
            if (!itemStack.isEmpty()) {
                narrator.saySystemNow(itemStack.getHoverName());
            }
        }
    }

    @Inject(method = "renderContents", at = @At("TAIL"))
    public void renderHoverStart(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        // Check if we started hovering over a new slot
        if (this.hoveredSlot != this.previousHoveredSlot) {
            if (this.hoveredSlot != null) {
                onStartHovering(this.hoveredSlot);
            }
            this.previousHoveredSlot = this.hoveredSlot;
        }
    }
}