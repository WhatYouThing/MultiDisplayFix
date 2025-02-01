package multidisplayfix.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_CAPTURED;

@Mixin(Mouse.class)
public abstract class MouseMixin {
    @Unique
    MinecraftClient client = MinecraftClient.getInstance();
    @Shadow
    private double x;
    @Shadow
    private double y;

    @Inject(method = "unlockCursor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/InputUtil;setCursorParameters(JIDD)V"), cancellable = true)
    private void onMouseMoved(CallbackInfo ci) {
        if (client.currentScreen instanceof ChatScreen) {
            return;
        }
        InputUtil.setCursorParameters(client.getWindow().getHandle(), GLFW_CURSOR_CAPTURED, this.x, this.y);
        ci.cancel();
    }
}