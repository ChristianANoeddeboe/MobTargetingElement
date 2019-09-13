package angercraft.mobtargetingelement.client.gui;

import angercraft.mobtargetingelement.Reference;
import angercraft.mobtargetingelement.config.GuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.text.DecimalFormat;

public class GuiTargetInfo extends Gui {

    private static final Minecraft mc = Minecraft.getMinecraft();

    private static final ResourceLocation GUI_RESOURCES = new ResourceLocation(Reference.MOD_ID, "textures/gui/hud/element.png");

    public void drawTarget(EntityLiving entity) {
        if(entity == null) {
            return;
        }

        int containerCornerX = 20;
        int containerCornerY = 20;

        mc.getTextureManager().bindTexture(GUI_RESOURCES);

        this.drawTexturedModalRect(containerCornerX, containerCornerY, 0, 0, 75, 72);

        this.drawTexturedModalRect(containerCornerX+85, containerCornerY+13, 75, 0, 160, 46);

        this.drawHealth(entity, containerCornerX+89, containerCornerY+38);

        String entityName = "";

        if(entity.isChild()) {
            entityName = "Baby ";
        }
        entityName += entity.getName();

        GL11.glPushMatrix();
        GL11.glScalef(1.2f, 1.2f, 1.2f);
        this.drawCenteredString(mc.fontRenderer, entityName, 154, 35, 0xffffffff);
        GL11.glPopMatrix();

        float scale;
        if(entity.width > entity.height) {
            scale = 2/entity.width*25;
        } else {
            scale = 2/entity.height*25;
        }
        if(scale > 40) {
            scale = 40;
        }
        float addY = 2*entity.height;

        GlStateManager.enableBlend();
        //GlStateManager.enableDepth();
        //GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        this.drawEntityOnScreen(58, (int) (75+addY), (int) scale, entity);

        //GlStateManager.disableBlend();

        mc.getTextureManager().bindTexture(ICONS);
    }

    private void drawHealth(EntityLiving entity, int x, int y) {
        float healthNum = entity.getHealth();
        float percentHealthLeft = healthNum/entity.getMaxHealth();

        // TODO Find way to display if entity is aggressive or not towards player.
        boolean aggressive = entity.isCreatureType(EnumCreatureType.MONSTER, false) && !EnumCreatureType.MONSTER.getPeacefulCreature();


        int width = 152;

        int displayWidth = (int) (percentHealthLeft*width);

        drawTexturedModalRect(x, y, 75, 46, displayWidth, 17);

        String healthText = "";
        if(GuiConfig.healthText == GuiConfig.HealthText.PERCENTAGE) {
            healthText = ((int)(percentHealthLeft*100))+"%";
        }
        if(GuiConfig.healthText == GuiConfig.HealthText.ABSOLUTE) {
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

    public void drawEntityOnScreen(int posX, int posY, int scale, EntityLivingBase ent) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);

        GlStateManager.rotate(-((float)Math.atan((double)(-60 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F); //15, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = -45;//(float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = -45;//(float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = 0;//-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = -45;
        ent.prevRotationYawHead = -45;

        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
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
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}
