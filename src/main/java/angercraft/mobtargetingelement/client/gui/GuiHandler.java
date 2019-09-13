package angercraft.mobtargetingelement.client.gui;

import angercraft.mobtargetingelement.Reference;
import angercraft.mobtargetingelement.client.MouseOver;
import angercraft.mobtargetingelement.config.Keybinds;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class GuiHandler extends Gui {

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
        EntityLiving pointedEntity = (EntityLiving) mouseOver.getEntityLookingAt(partialticks, 64.0);
        if(pointedEntity != null) {
            guiTargetLock.drawTarget(pointedEntity);
        }
    }

    @SubscribeEvent
    public static void afterRenderGui(RenderGameOverlayEvent.Post event) {
        if(show) {
            renderElement(event.getPartialTicks());
        }
    }
}
