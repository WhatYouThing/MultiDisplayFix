package multidisplayfix.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.ChatScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.util.InputUtil.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_CAPTURED;

@Mixin(Mouse.class)
public abstract class MouseMixin {
    @Unique
    MinecraftClient client = MinecraftClient.getInstance();

    @Inject(method = "unlockCursor", at = @At(value = "TAIL"))
    private void onMouseMoved(CallbackInfo ci) {
        if (client.currentScreen instanceof ChatScreen) {
            return;
        }
        GLFW.glfwSetInputMode(client.getWindow().getHandle(), GLFW_CURSOR, GLFW_CURSOR_CAPTURED);
    }
}