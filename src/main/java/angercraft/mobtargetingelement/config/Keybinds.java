package angercraft.mobtargetingelement.config;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import static org.lwjgl.input.Keyboard.*;

public class Keybinds {

    public static KeyBinding toggleTargetElement;

    public static void register() {
        toggleTargetElement = new KeyBinding("key.toggleElement", KeyConflictContext.UNIVERSAL, KeyModifier.SHIFT, KEY_F, "key.categories.mobtargetingelement");

        ClientRegistry.registerKeyBinding(toggleTargetElement);
    }
}
