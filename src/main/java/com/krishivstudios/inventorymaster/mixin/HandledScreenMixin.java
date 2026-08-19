package com.krishivstudios.inventorymaster.mixin;

import com.krishivstudios.inventorymaster.client.ContainerButtonRenderer;
import com.krishivstudios.inventorymaster.sort.InventorySorter;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.Screen;
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

    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRenderTail(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        HandledScreen<?> self = (HandledScreen<?>) (Object) this;
        ContainerButtonRenderer.renderButtons(self, context, mouseX, mouseY, this.x, this.y, this.backgroundWidth, this.backgroundHeight);
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void onMouseClickedHead(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        HandledScreen<?> self = (HandledScreen<?>) (Object) this;

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
