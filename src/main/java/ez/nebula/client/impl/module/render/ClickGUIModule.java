package ez.nebula.client.impl.module.render;

import ez.nebula.client.api.manager.module.Module;
import ez.nebula.client.api.manager.module.trait.ModuleCategory;
import ez.nebula.client.api.manager.module.trait.ModuleManifest;
import ez.nebula.client.impl.gui.module.ClickGUIScreen;
import org.lwjgl.input.Keyboard;

@ModuleManifest(name = "ClickGUI",
        description = "Displays a GUI with all modules and their settings",
        category = ModuleCategory.RENDER)
public final class ClickGUIModule extends Module
{
    private final ClickGUIScreen screen;

    public ClickGUIModule()
    {
        getKey().setKeyCode(Keyboard.KEY_RSHIFT);
        screen = new ClickGUIScreen(this);
    }

    @Override
    public void onEnable()
    {
        super.onEnable();
        if (MC.theWorld != null && MC.thePlayer != null)
        {
            MC.displayGuiScreen(screen);
        }
        setToggled(false);
    }
}
