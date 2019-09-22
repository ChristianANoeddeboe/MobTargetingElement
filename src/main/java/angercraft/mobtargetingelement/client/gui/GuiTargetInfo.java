package angercraft.mobtargetingelement.client.gui;

import angercraft.mobtargetingelement.Reference;
import angercraft.mobtargetingelement.config.GuiConfig;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.text.DecimalFormat;

public class GuiTargetInfo extends AbstractGui {

    private static final Minecraft mc = Minecraft.getInstance();

    private static final ResourceLocation GUI_RESOURCES = new ResourceLocation(Reference.MOD_ID, "textures/gui/hud/element.png");

    public void drawTarget(LivingEntity entity) {
        if(entity == null) {
            return;
        }

        int containerCornerX = 20;
        int containerCornerY = 20;

        mc.getTextureManager().bindTexture(GUI_RESOURCES);

        this.blit(containerCornerX, containerCornerY, 0, 0, 75, 72);

        this.blit(containerCornerX+85, containerCornerY+13, 75, 0, 160, 46);

        this.drawHealth(entity, containerCornerX+89, containerCornerY+38);

        String entityName = "";

        if(entity.isChild()) {
            entityName = "Baby ";
        }
        entityName += entity.getName().getString();

        GL11.glPushMatrix();
        GL11.glScalef(1.2f, 1.2f, 1.2f);
        this.drawCenteredString(mc.fontRenderer, entityName, 154, 35, 0xffffffff);
        GL11.glPopMatrix();

        float scale;
        if(entity.getWidth() > entity.getHeight()) {
            scale = 2/entity.getWidth()*25;
        } else {
            scale = 2/entity.getHeight()*25;
        }
        if(scale > 40) {
            scale = 40;
        }
        float addY = 2*entity.getHeight();

        GlStateManager.enableBlend();
        //GlStateManager.enableDepth();
        //GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        this.drawEntityOnScreen(58, (int) (75+addY), (int) scale, entity);

        //GlStateManager.disableBlend();

        mc.getTextureManager().bindTexture(GUI_ICONS_LOCATION);
    }

    private void drawHealth(LivingEntity entity, int x, int y) {
        float healthNum = entity.getHealth();
        float percentHealthLeft = healthNum/entity.getMaxHealth();

        int width = 152;

        int displayWidth = (int) (percentHealthLeft*width);

        blit(x, y, 75, 46, displayWidth, 17);

        String healthText = "";
        if(GuiConfig.CLIENT.healthText.get() == GuiConfig.HealthText.PERCENTAGE) {
            healthText = ((int)(percentHealthLeft*100))+"%";
        }
        if(GuiConfig.CLIENT.healthText.get() == GuiConfig.HealthText.ABSOLUTE) {
            if(healthNum % 1 != 0) {
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                healthText = decimalFormat.format(healthNum)+"/"+((int)entity.getMaxHealth());
            } else {
                healthText = (int)healthNum+"/"+((int)entity.getMaxHealth());
            }
        }

        GL11.glPushMatrix();
        GL11.glScalef(1.2f, 1.2f, 1.2f);
        this.drawCenteredString(mc.fontRenderer, healthText, 154, 52, 0xffffffff);
        GL11.glPopMatrix();
    }

    public void drawEntityOnScreen(int posX, int posY, int scale, LivingEntity ent) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float)posX, (float)posY, 50.0F);
        GlStateManager.scalef((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotatef(-135.0F, 0.0F, 1.0F, 0.0F);

        GlStateManager.rotatef(-((float)Math.atan((double)(-60 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F); //15, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = -45;//(float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = -45;//(float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = 0;//-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = -45;
        ent.prevRotationYawHead = -45;

        GlStateManager.translatef(0.0F, 0.0F, 0.0F);

        EntityRendererManager rendermanager = Minecraft.getInstance().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.activeTexture(GLX.GL_TEXTURE1);
        GlStateManager.disableTexture();
        GlStateManager.activeTexture(GLX.GL_TEXTURE0);
    }
}
