package ez.nebula.client.impl.module.player;

import ez.nebula.client.api.listener.EventListener;
import ez.nebula.client.api.listener.Subscribe;
import ez.nebula.client.api.listener.event.*;
import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import ez.nebula.client.api.setting.Setting;
import ez.nebula.client.util.minecraft.MoveUtil;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.World;

/**
 * @author xgraza
 * @since 6/9/26
 */
@ModuleManifest(name = "Freecam",
        description = "Allows for an out of body experience",
        category = ModuleCategory.PLAYER)
public final class FreecamModule extends Module
{
    public static final int CAMERA_GUY_ENTITY_ID = 69420;

    private final Setting<Double> speedSetting = numberBuilder("Speed", 0.5)
            .setMin(0.1)
            .setMax(5.0)
            .setScale(0.1)
            .setDescription("The speed to move the fake player horizontally")
            .build();
    private final Setting<Double> ySpeedSetting = numberBuilder("Y-Speed", 0.5)
            .setMin(0.1)
            .setMax(5.0)
            .setScale(0.1)
            .setDescription("The speed to move the fake player vertically")
            .build();

    private CameraGuyEntity cameraGuy;

    @Override
    public void onDisable()
    {
        super.onDisable();
        destroyCameraGuy();
    }

    @Subscribe
    private final EventListener<EventUpdate> updateEventListener = event ->
    {
        if (MC.thePlayer.ticksExisted < 5)
        {
            destroyCameraGuy();
            return;
        }
        if (cameraGuy == null)
        {
            createCameraGuy();
        }
    };

    @Subscribe
    private final EventListener<EventChangeWorld> changeWorldEventListener = event ->
    {
        cameraGuy = null;
        MC.renderViewEntity = MC.thePlayer;
    };

    @Subscribe
    private final EventListener<EventUpdateInput> updateInputEventListener = event ->
    {
        if (cameraGuy != null)
        {
            event.cancel();

            cameraGuy.forward = 0;
            cameraGuy.strafe = 0;
            cameraGuy.jump = false;
            cameraGuy.sneak = false;

            if (MC.gameSettings.keyBindJump.isKeyDown())
            {
                cameraGuy.jump = true;
            }

            if (MC.gameSettings.keyBindSneak.isKeyDown())
            {
                cameraGuy.sneak = true;
            }

            if (MC.gameSettings.keyBindForward.isKeyDown())
            {
                cameraGuy.forward = 1;
            }

            if (MC.gameSettings.keyBindBack.isKeyDown())
            {
                cameraGuy.forward = -1;
            }

            if (MC.gameSettings.keyBindLeft.isKeyDown())
            {
                cameraGuy.strafe = 1;
            }

            if (MC.gameSettings.keyBindRight.isKeyDown())
            {
                cameraGuy.strafe = -1;
            }
        }
    };

    @Subscribe
    private final EventListener<EventPacket> packetEventListener = event ->
    {
        if (event.getPacket() instanceof Packet7UseEntity)
        {
            final Packet7UseEntity packet = (Packet7UseEntity) event.getPacket();
            if (packet.targetEntity == MC.thePlayer.entityId)
            {
                event.cancel();
            }
        }
    };

    private void createCameraGuy()
    {
        if (MC.thePlayer == null || MC.theWorld == null)
        {
            return;
        }
        if (cameraGuy != null)
        {
            destroyCameraGuy();
        }
        cameraGuy = new CameraGuyEntity(MC.theWorld);
        cameraGuy.setPositionAndRotation(MC.thePlayer.posX, MC.thePlayer.posY, MC.thePlayer.posZ, MC.thePlayer.rotationYaw, MC.thePlayer.rotationPitch);
        cameraGuy.rotationYaw = cameraGuy.prevRotationYaw = MC.thePlayer.rotationYaw;
        cameraGuy.rotationPitch = cameraGuy.prevRotationPitch = MC.thePlayer.rotationPitch;
        MC.theWorld.entityJoinedWorld(cameraGuy);
        MC.renderViewEntity = cameraGuy;
    }

    private void destroyCameraGuy()
    {
        if (cameraGuy == null)
        {
            return;
        }
        if (MC.thePlayer != null)
        {
            MC.renderViewEntity = MC.thePlayer;
        }
        if (MC.theWorld != null)
        {
            MC.theWorld.setEntityDead(cameraGuy);
        }
        cameraGuy = null;
    }

    private final class CameraGuyEntity extends EntityOtherPlayerMP
    {
        public boolean jump, sneak;
        public int forward, strafe;

        public CameraGuyEntity(World var1)
        {
            super(var1, "CameraGuy");
            entityId = CAMERA_GUY_ENTITY_ID;
        }

        @Override
        public void onUpdate()
        {
            super.onUpdate();

            noClip = true;
            motionX = motionZ = motionY = 0;

            if (strafe != 0 || forward != 0)
            {
                final double[] movement = MoveUtil.calculateMovement(speedSetting.getValue(),
                        MoveUtil.getDirectionYaw(rotationYaw, forward, strafe));
                motionX = movement[0];
                motionZ = movement[1];
            }

            if (jump)
            {
                motionY = ySpeedSetting.getValue();
            }

            if (sneak)
            {
                motionY = -ySpeedSetting.getValue();
            }

            moveEntity(motionX, motionY, motionZ);
        }
    }
}
