package angercraft.mobtargetingelement.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class GuiConfig {

    public static class Client {

        public final EnumValue<HealthText> healthText;

        Client(final ForgeConfigSpec.Builder builder) {
            builder.comment("Mod settings")
                    .push("client");

            healthText = builder
                    .comment("Test comment")
                    .defineEnum("healthText", HealthText.ABSOLUTE);

            builder.pop();
        }
    }

    public enum HealthText {
        ABSOLUTE,
        PERCENTAGE
    }

    private static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;

    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.CLIENT, clientSpec);
    }

/*
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

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(Reference.MOD_ID))
                ConfigManager.load(Reference.MOD_ID, Config.Type.INSTANCE);
        }
    }
    */
}
