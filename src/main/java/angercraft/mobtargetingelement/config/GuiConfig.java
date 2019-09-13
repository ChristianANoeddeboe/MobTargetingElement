package angercraft.mobtargetingelement.config;

import angercraft.mobtargetingelement.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Reference.MOD_ID)
public class GuiConfig {

    @Config.Name("Mob health")
    @Config.Comment("Show health in numbers or percentage.")
    public static HealthText healthText = HealthText.ABSOLUTE;

    public enum HealthText {
        ABSOLUTE,
        PERCENTAGE
    }

    @Mod.EventBusSubscriber
    private static class ConfigHandler {

        /**
         * Inject the new values and save to the config file when the
         * config has been changed from the GUI.
         *
         * @param event the event
         */
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(Reference.MOD_ID))
                ConfigManager.load(Reference.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
