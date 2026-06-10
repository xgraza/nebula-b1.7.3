package ez.nebula.client.util.minecraft;

import net.minecraft.client.Minecraft;

public final class ChatUtil
{
    private static final Minecraft MC = Minecraft.getMinecraft();
    public static final char COLOR_CONTROL_CHARACTER = '§';

    public static final String NEBULA_CHAT_PREFIX = formatChatMessage("&a%s&r ", "Nebula");

    public static void sendMessage(final String message, final Object... format)
    {
        MC.ingameGUI.addChatMessage(NEBULA_CHAT_PREFIX + formatChatMessage(message, format));
    }

    public static String formatChatMessage(final String message, final Object... format)
    {
        return String.format(message, format)
                .replaceAll("(?i)&([0-9A-FK-OR])", COLOR_CONTROL_CHARACTER + "$1");
    }
}
