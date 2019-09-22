package angercraft.mobtargetingelement.client;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.command.arguments.EntitySelectorParser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraftforge.common.command.IEntitySelectorType;

import javax.annotation.Nullable;
import java.util.List;

public class MouseOver {

    private static final Minecraft mc = Minecraft.getInstance();

    public Entity getEntityMouseOver(float partialTicks, double reachDistance) {
        Entity entity = this.mc.getRenderViewEntity();
        Entity pointedEntity = null;
        if (entity != null) {
            if (mc.world != null) {
                mc.getProfiler().startSection("pick");
                mc.objectMouseOver = entity.func_213324_a(reachDistance, partialTicks, false);
                Vec3d vec3d = entity.getEyePosition(partialTicks);

                double d1 = reachDistance;

                d1 = d1 * d1;
                if (mc.objectMouseOver != null) {
                    d1 = mc.objectMouseOver.getHitVec().squareDistanceTo(vec3d);
                }

                Vec3d vec3d1 = entity.getLook(1.0F);
                Vec3d vec3d2 = vec3d.add(vec3d1.x * reachDistance, vec3d1.y * reachDistance, vec3d1.z * reachDistance);

                AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(vec3d1.scale(reachDistance)).grow(1.0D, 1.0D, 1.0D);
                EntityRayTraceResult entityraytraceresult = ProjectileHelper.func_221273_a(entity, vec3d, vec3d2, axisalignedbb, (p_215312_0_) -> {
                    return !p_215312_0_.isSpectator() && p_215312_0_.canBeCollidedWith();
                }, d1);
                if (entityraytraceresult != null) {
                    Entity entity1 = entityraytraceresult.getEntity();
                    Vec3d vec3d3 = entityraytraceresult.getHitVec();
                    double d2 = vec3d.squareDistanceTo(vec3d3);
                    if (d2 < d1 || mc.objectMouseOver == null) {
                        mc.objectMouseOver = entityraytraceresult;
                        if (entity1 instanceof LivingEntity) {
                            pointedEntity = entity1;
                        }
                    }
                }
                mc.getProfiler().endSection();
            }
        }
        return pointedEntity;
    }

    /*
    public Entity getEntityLookingAt(float partialTicks, double distance)
    {
        Entity pointedEntity = null;
        Entity entity = mc.getRenderViewEntity();

        if (entity != null)
        {
            if (mc.world != null)
            {
                mc.getProfiler().startSection("pick");
                //this.mc.pointedEntity = null;
                RayTraceResult objectMouseOver = entity.func_213324_a(distance, partialTicks, false);
                Vec3d vec3d = entity.getEyePosition(partialTicks);

                double d1 = distance;

                if (objectMouseOver != null)
                {
                    d1 = objectMouseOver.getHitVec().distanceTo(vec3d);
                }

                Vec3d vec3d1 = entity.getLook(1.0F);
                Vec3d vec3d2 = vec3d.add(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance);
                pointedEntity = null;
                Vec3d vec3d3 = null;
                List<Entity> list = mc.world.getEntitiesInAABBexcluding(entity, entity.getBoundingBox().expand(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance).grow(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
                {
                    public boolean apply(@Nullable Entity p_apply_1_)
                    {
                        return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
                    }
                }));
                double d2 = d1;

                for (Entity entity1 : list) {
                    AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow(entity1.getCollisionBorderSize());
                    RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d2);

                    if (axisalignedbb.contains(vec3d)) {
                        if (d2 >= 0.0D) {
                            pointedEntity = entity1;
                            vec3d3 = raytraceresult == null ? vec3d : raytraceresult.hitVec;
                            d2 = 0.0D;
                        }
                    } else if (raytraceresult != null) {
                        double d3 = vec3d.distanceTo(raytraceresult.hitVec);

                        if (d3 < d2 || d2 == 0.0D) {
                            if (entity1.getLowestRidingEntity() == entity.getLowestRidingEntity() && !entity1.canRiderInteract()) {
                                if (d2 == 0.0D) {
                                    pointedEntity = entity1;
                                    vec3d3 = raytraceresult.hitVec;
                                }
                            } else {
                                pointedEntity = entity1;
                                vec3d3 = raytraceresult.hitVec;
                                d2 = d3;
                            }
                        }
                    }
                }

                if(!(pointedEntity instanceof EntityLivingBase) || vec3d.distanceTo(vec3d3) > distance) {
                    pointedEntity = null;
                }

                /*if (pointedEntity != null && flag && vec3d.distanceTo(vec3d3) > distance)
                {
                    pointedEntity = null;
                    objectMouseOver = new RayTraceResult(RayTraceResult.Type.MISS, vec3d3, null, new BlockPos(vec3d3));
                }

                if (pointedEntity != null && (d2 < d1 || this.mc.objectMouseOver == null))
                {
                    objectMouseOver = new RayTraceResult(pointedEntity, vec3d3);

                    if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame)
                    {
                        pointedEntity = pointedEntity;
                    }
                }*



                //this.mc.mcProfiler.endSection();
            }
        }
        return pointedEntity;
    }*/
}
