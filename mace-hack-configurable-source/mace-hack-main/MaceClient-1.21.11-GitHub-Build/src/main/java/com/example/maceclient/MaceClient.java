package com.example.maceclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class MaceClient implements ClientModInitializer {

    public static final ModuleManager MODULES = new ModuleManager();
    private static KeyBinding guiKey;

    @Override
    public void onInitializeClient() {

        TrackerRenderer.init();

        guiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.maceclient.gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                KeyBinding.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (guiKey.wasPressed()) {
                client.setScreen(new ClickGuiScreen());
            }

            MODULES.tick(client);
        });
    }

    public static ModuleManager getModuleManager() {
        return MODULES;
    }

    public static void msg(String s) {
        MinecraftClient c = MinecraftClient.getInstance();

        if (c.player != null) {
            c.player.sendMessage(
                    Text.literal("§7[Mace] §f" + s),
                    true
            );
        }
    }
}
