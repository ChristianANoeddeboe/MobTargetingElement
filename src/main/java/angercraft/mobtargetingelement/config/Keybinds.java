package angercraft.mobtargetingelement.config;

import angercraft.mobtargetingelement.Reference;
import net.java.games.input.Keyboard;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT, bus = Bus.MOD)
public class Keybinds {

    public static KeyBinding toggleTargetElement = new KeyBinding("key.mobtargetingelement.toggleElement", KeyConflictContext.UNIVERSAL, KeyModifier.SHIFT, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_F, "key.categories.mobtargetingelement");

    @SubscribeEvent
    public static void registerKeyBindings(final FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(toggleTargetElement);
    }
}
