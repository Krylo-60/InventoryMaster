package com.krishivstudios.inventorymaster.mixin;

import com.krishivstudios.inventorymaster.client.ContainerButtonRenderer;
import com.krishivstudios.inventorymaster.feature.ChestSearchFilter;
import com.krishivstudios.inventorymaster.feature.SlotLockManager;
import com.krishivstudios.inventorymaster.sort.InventorySorter;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin extends Screen {

    @Shadow protected int x;
    @Shadow protected int y;
    @Shadow protected int backgroundWidth;
    @Shadow protected int backgroundHeight;
    @Shadow protected Slot focusedSlot;

    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderTail(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        HandledScreen<?> self = (HandledScreen<?>) (Object) this;
        ContainerButtonRenderer.renderButtons(self, context, mouseX, mouseY, this.x, this.y, this.backgroundWidth, this.backgroundHeight);

        // Render Slot Lock Indicators & Search Dimming
        for (Slot slot : self.getScreenHandler().slots) {
            int slotX = this.x + slot.x;
            int slotY = this.y + slot.y;

            // Search filter dimming
            if (ChestSearchFilter.isActive() && !slot.getStack().isEmpty() && !ChestSearchFilter.matches(slot.getStack())) {
                context.fill(slotX, slotY, slotX + 16, slotY + 16, 0xCC000000);
            }

            // Locked slot badge
            if (SlotLockManager.isLocked(slot.id)) {
                context.drawTextWithShadow(this.textRenderer, "🔒", slotX + 8, slotY + 7, 0xFFA500);
            }
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void onMouseClickedHead(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        HandledScreen<?> self = (HandledScreen<?>) (Object) this;

        // Alt + Left-Click on Slot to Toggle Lock
        if (Screen.hasAltDown() && button == 0 && this.focusedSlot != null) {
            SlotLockManager.toggleLock(this.focusedSlot.id);
            cir.setReturnValue(true);
            return;
        }

        // Custom Buttons Click
        if (ContainerButtonRenderer.mouseClicked(self, mouseX, mouseY, button, this.x, this.y, this.backgroundWidth, this.backgroundHeight)) {
            cir.setReturnValue(true);
            return;
        }

        // Middle-Click (Button 2) anywhere to sort
        if (button == 2) {
            InventorySorter.sortChest(self.getScreenHandler());
            InventorySorter.sortPlayerInventory(self.getScreenHandler());
            cir.setReturnValue(true);
        }
    }
}
