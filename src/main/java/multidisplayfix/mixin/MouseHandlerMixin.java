package multidisplayfix.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.PauseScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_CAPTURED;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {

    @Unique
    Minecraft client = Minecraft.getInstance();

    @WrapOperation(method = "releaseMouse", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;grabOrReleaseMouse(Lcom/mojang/blaze3d/platform/Window;IDD)V"))
    private void onReleaseMouse(Window window, int cursorMode, double xpos, double ypos, Operation<Void> original) {
        if (client.gui.screen() instanceof ChatScreen || client.gui.screen() instanceof PauseScreen) {
            original.call(window, cursorMode, xpos, ypos);
        } else {
            original.call(window, GLFW_CURSOR_CAPTURED, xpos, ypos); // constant value 212996, to be removed in 26.3
        }
    }
}