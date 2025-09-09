package myk_00l.betternarrator.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.gen.Invoker;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import myk_00l.betternarrator.DelayedNarrator;
import myk_00l.betternarrator.mixin.InventoryAccessor;

@Mixin(Inventory.class)
public class InventoryMixin {
    private static DelayedNarrator narrator = new DelayedNarrator(250);
    // for whatever rison it does not compile unless it's cancellable
    @Inject(method="setSelectedSlot", at=@At("TAIL"), cancellable=true)
    private void setSelectedSlot(int n, CallbackInfo ci) {
        ItemStack itemStack = ((InventoryAccessor)this).getItem(n);
        if (itemStack == null || itemStack.isEmpty()) {
            narrator.cancel();
        } else {
            narrator.say(itemStack.getHoverName());
        }
    }
}