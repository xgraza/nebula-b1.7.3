package ez.nebula.client.api.manager.macro;

import ez.nebula.client.api.listener.EventBus;
import ez.nebula.client.api.manager.ITypedManager;
import ez.nebula.client.core.Nebula;

import java.io.File;
import java.util.*;

/**
 * @author xgraza
 * @since 6/8/26
 */
public final class MacroManager implements ITypedManager<Macro>
{
    private final Map<String, Macro> macroNameMap = new HashMap<>();

    @Override
    public void init()
    {
        EventBus.subscribe(this);
        Nebula.INSTANCE.getConfigManager()
                .addConfiguration(new MacroConfig(this));
    }

    @Override
    public Collection<Macro> getAll()
    {
        return macroNameMap.values();
    }

    public Set<String> getMacroNames()
    {
        return macroNameMap.keySet();
    }

    public void registerMacro(final String name, final Macro macro)
    {
        macroNameMap.put(name, macro);
    }

    public Macro getMacro(final String name)
    {
        return macroNameMap.get(name);
    }

    public void clearMacros()
    {
        macroNameMap.clear();
    }

    public File getSaveFile()
    {
        return new File(Nebula.INSTANCE.getNebulaDirectory(), "macros.json");
    }
}
