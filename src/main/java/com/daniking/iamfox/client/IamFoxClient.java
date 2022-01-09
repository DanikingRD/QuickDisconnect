package com.daniking.iamfox.client;

import com.daniking.iamfox.common.IamFox;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.screen.SaveLevelScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;

public class IamFoxClient implements ClientModInitializer {

    public static final KeyBinding DISCONNECT_FROM_SERVER = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + IamFox.MODID + ".disconnect", InputUtil.GLFW_KEY_F10,  "category." + IamFox.MODID));

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (DISCONNECT_FROM_SERVER.wasPressed()) {
                //just to remove the warnings
                if (client == null || client.world == null) {
                    return;
                }
                //Minecraft code, to disconnect players
                boolean bl = client.isInSingleplayer();
                boolean bl2 = client.isConnectedToRealms();
                DISCONNECT_FROM_SERVER.setPressed(true);
                client.world.disconnect();
                if (bl) {
                    client.disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
                } else {
                   client.disconnect();
                }
                 final TitleScreen titleScreen = new TitleScreen();
                if (bl) {
                    client.setScreen(titleScreen);
                } else if (bl2) {
                    client.setScreen(new RealmsMainScreen(titleScreen));
                } else {
                    client.setScreen(new MultiplayerScreen(titleScreen));
                }
            }
        });
    }
}
