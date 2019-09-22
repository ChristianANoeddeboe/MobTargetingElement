package angercraft.mobtargetingelement.client.gui;

import angercraft.mobtargetingelement.Reference;
import angercraft.mobtargetingelement.client.MouseOver;
import angercraft.mobtargetingelement.config.Keybinds;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class GuiHandler extends AbstractGui {

    private static GuiTargetInfo guiTargetLock = new GuiTargetInfo();
    private static boolean show = true;

    private static void toggleElement() {
        show = !show;
    }

    @SubscribeEvent
    public static void keyPressed(InputEvent.KeyInputEvent event) {
        if(Keybinds.toggleTargetElement.isPressed()) {
            toggleElement();
        }
    }

    private static void renderElement(float partialticks) {
        MouseOver mouseOver = new MouseOver();
        Entity lookingAt = mouseOver.getEntityMouseOver(partialticks, 32.0);
        if(!(lookingAt instanceof LivingEntity)) {
            return;
        }
        guiTargetLock.drawTarget((LivingEntity) lookingAt);
    }

    @SubscribeEvent
    public static void afterRenderGui(RenderGameOverlayEvent.Post event) {
        if(show) {
            renderElement(event.getPartialTicks());
        }
    }
}
