package angercraft.mobtargetingelement;

import angercraft.mobtargetingelement.config.GuiConfig;
import angercraft.mobtargetingelement.config.Keybinds;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.ModLoadingContext;

@Mod("mobtargetingelement")
public class MobTargetingElement
{
    private static final Logger LOGGER = LogManager.getLogger();

    public MobTargetingElement() {
        // Register the setup method for modloading
        GuiConfig.register(ModLoadingContext.get());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void setup(final FMLCommonSetupEvent event)
    {

    }

}