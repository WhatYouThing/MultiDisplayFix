package multidisplayfix.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.ChatScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_CAPTURED;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {

    @Unique
    Minecraft client = Minecraft.getInstance();

    @Inject(method = "releaseMouse", at = @At(value = "TAIL"))
    private void onMouseMoved(CallbackInfo ci) {
        if (client.screen instanceof ChatScreen) {
            return;
        }
        GLFW.glfwSetInputMode(client.getWindow().handle(), GLFW_CURSOR, GLFW_CURSOR_CAPTURED);
    }
}