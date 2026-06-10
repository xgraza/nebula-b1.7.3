package ez.nebula.client.impl.module.render;

import ez.nebula.client.BuildConfig;
import ez.nebula.client.api.listener.EventListener;
import ez.nebula.client.api.listener.Subscribe;
import ez.nebula.client.api.listener.event.EventRender2D;
import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import ez.nebula.client.api.setting.Setting;
import ez.nebula.client.core.ClientConfig;
import ez.nebula.client.core.Nebula;
import ez.nebula.client.impl.module.player.FreecamModule;
import net.minecraft.src.EntityPlayer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xgraza
 * @since 6/8/26
 */
@ModuleManifest(name = "HUD",
        description = "Shows important client information",
        category = ModuleCategory.RENDER)
public final class HUDModule extends Module
{
    private final Setting<Boolean> watermarkSetting = builder("Watermark", true)
            .setDescription("If to show the client watermark")
            .build();
    private final Setting<Boolean> arrayListSetting = builder("Arraylist", true)
            .setDescription("If to show all toggled modules")
            .build();
    private final Setting<Boolean> radarSetting = builder("Radar", false)
            .setDescription("If to show nearby players")
            .build();

    public HUDModule()
    {
        // on by default
        setToggled(true);
        setVisible(false);
    }

    @Subscribe
    private final EventListener<EventRender2D> render2DEventListener = event ->
    {
        if (watermarkSetting.getValue())
        {
            MC.fontRenderer.drawStringWithShadow(BuildConfig.NAME + " " + ClientConfig.VERSION, 2, 2, -1);
        }

        if (arrayListSetting.getValue())
        {
            final List<Module> moduleList = Nebula.INSTANCE.getModuleManager().getAll()
                    .stream()
                    .filter((module) -> module.isToggled() && module.isVisible())
                    .sorted(Comparator.comparingInt(
                            (module) -> -MC.fontRenderer.getStringWidth(module.getManifest().name())))
                    .collect(Collectors.toList());

            int posY = 2;
            for (final Module module : moduleList)
            {
                final String text = module.getManifest().name();
                final int textWidth = MC.fontRenderer.getStringWidth(text);

                MC.fontRenderer.drawStringWithShadow(text, event.getResolution().getScaledWidth() - textWidth - 2, posY, -1);
                posY += 11;
            }
        }

        if (radarSetting.getValue())
        {
            final List<EntityPlayer> playerList = ((List<EntityPlayer>) MC.theWorld.playerEntities)
                    .stream()
                    .filter((player) -> !MC.thePlayer.equals(player)
                            && player.entityId != FreecamModule.CAMERA_GUY_ENTITY_ID)
                    .sorted(Comparator.comparingDouble((player) -> MC.thePlayer.getDistanceToEntity(player)))
                    .collect(Collectors.toList());
            int posY = watermarkSetting.getValue() ? 20 : 4;
            for (final EntityPlayer player : playerList)
            {
                final String text = String.format("> %.2f - %s", MC.thePlayer.getDistanceToEntity(player), player.username);
                MC.fontRenderer.drawStringWithShadow(text, 6, posY, -1);
                posY += 11;
            }
        }
    };
}
