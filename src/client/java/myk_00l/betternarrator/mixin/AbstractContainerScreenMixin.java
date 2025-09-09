
package myk_00l.betternarrator.mixin;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jetbrains.annotations.Nullable;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import myk_00l.betternarrator.DelayedNarrator;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin<T extends AbstractContainerMenu> {
    private static DelayedNarrator narrator = new DelayedNarrator(250);
    @Shadow
    @Nullable
    protected Slot hoveredSlot;
    private void onStartHovering(Slot slot) {
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack = slot.getItem();
            if (itemStack.isEmpty()) {
                narrator.cancel();
            } else {
                narrator.say(itemStack.getHoverName());
            }
        } else {
            narrator.cancel();
        }
    }
    private Slot previousHoveredSlot = null;
    @Inject(method = "renderContents", at = @At("TAIL"))
    public void renderHoverStart(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (this.hoveredSlot != this.previousHoveredSlot) {
            onStartHovering(this.hoveredSlot);
            this.previousHoveredSlot = this.hoveredSlot;
        }
    }
}
