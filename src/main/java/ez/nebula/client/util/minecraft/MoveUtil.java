package ez.nebula.client.util.minecraft;

import ez.nebula.client.api.listener.event.EventMove;
import net.minecraft.client.Minecraft;

public final class MoveUtil
{
    private static final Minecraft MC = Minecraft.getMinecraft();

    public static final double MATH_GROUND = 1 / 64.0;

    public static double getMathGround(final double posY)
    {
        return posY - (posY % MATH_GROUND);
    }

    public static void setVerticalSpeed(final EventMove event, final double speed)
    {
        if (event != null)
        {
            event.setY(speed);
        }
        MC.thePlayer.motionY = speed;
    }

    public static void setSpeed(EventMove event, double speed)
    {
        final double[] movement = calculateMovement(speed);
        if (event != null)
        {
            event.setX(movement[0]);
            event.setZ(movement[1]);
        }
        MC.thePlayer.motionX = movement[0];
        MC.thePlayer.motionZ = movement[1];
    }

    public static double[] calculateMovement(final double speed)
    {
        return calculateMovement(speed, getDirectionYaw());
    }

    public static double[] calculateMovement(final double speed, final float yaw)
    {
        if (speed <= 0.0)
        {
            return new double[2];
        }

        final float direction = yaw * 0.017453292f;
        return new double[]{ -Math.sin(direction) * speed, Math.cos(direction) * speed };
    }

    public static float getDirectionYaw()
    {
        return getDirectionYaw(MC.thePlayer.rotationYaw,
                MC.thePlayer.movementInput.moveForward,
                MC.thePlayer.movementInput.moveStrafe);
    }

    public static float getDirectionYaw(float yaw, float f, float s)
    {
        // if we're moving backwards, reverse our yaw
        if (f < 0.0f)
        {
            yaw -= 180.0f;
        }

        // this is for handling holding forward & strafing side to side at the same time
        float forward = f * 0.5f;
        if (forward == 0.0f)
        {
            forward = 1.0f;
        }

        float strafe = s;
        if (strafe > 0.0f)
        {
            yaw -= 90.0f * forward;
        } else if (strafe < 0.0f)
        {
            yaw += 90.0f * forward;
        }

        return yaw;
    }

    public static boolean isMoving()
    {
        return MC.thePlayer.movementInput.moveForward != 0.0f
                || MC.thePlayer.movementInput.moveStrafe != 0.0f;
    }
}
