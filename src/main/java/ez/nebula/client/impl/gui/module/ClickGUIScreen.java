package ez.nebula.client.impl.gui.module;

import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.render.component.IComponent;
import ez.nebula.client.core.Nebula;
import ez.nebula.client.impl.module.render.ClickGUIModule;
import net.minecraft.src.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public final class ClickGUIScreen extends GuiScreen
{
    private static final double PANEL_POS_Y = 20.0;
    private static final double PANEL_HEADER_HEIGHT = 14.0;
    private static final double PANEL_WIDTH = 135.0;

    private final List<CategoryPanel> panelList = new ArrayList<>();
    private final ClickGUIModule module;

    public ClickGUIScreen(final ClickGUIModule module)
    {
        this.module = module;
    }

    @Override
    public void initGui()
    {
        if (panelList.isEmpty())
        {
            double posX = 10.0;

            for (final ModuleCategory moduleCategory : ModuleCategory.values())
            {
                final List<IComponent> moduleComponents = new ArrayList<>();
                for (final Module m : Nebula.INSTANCE.getModuleManager().getAll())
                {
                    if (m.getManifest().category().equals(moduleCategory))
                    {
                        moduleComponents.add(new ModuleComponent(m));
                    }
                }

                final CategoryPanel panel = new CategoryPanel(moduleCategory.toString(), moduleComponents);
                panel.setX(posX);
                panel.setY(PANEL_POS_Y);
                panel.setWidth(PANEL_WIDTH);
                panel.setHeight(PANEL_HEADER_HEIGHT);
                panelList.add(panel);
                posX += PANEL_WIDTH + 4.0;
            }
        }
    }

    @Override
    public void updateScreen()
    {
        handleInput();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float tickDelta)
    {
        drawDefaultBackground();
        for (final CategoryPanel panel : panelList)
        {
            panel.render(mouseX, mouseY);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        for (final CategoryPanel panel : panelList)
        {
            panel.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void onGuiClosed()
    {
        Nebula.INSTANCE.getConfigManager().saveConfigs();
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
