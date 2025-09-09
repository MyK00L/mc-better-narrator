package myk_00l.betternarrator.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.gen.Invoker;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import myk_00l.betternarrator.DelayedNarrator;

@Mixin(Inventory.class)
public interface InventoryAccessor {
    @Invoker("getItem")
    public ItemStack getItem(int n);
}