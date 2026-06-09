package ez.nebula.client.api.manager.module;

import ez.nebula.client.api.logger.Logger;
import ez.nebula.client.api.manager.ITypedManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModuleManager implements ITypedManager<Module>
{
    private final List<Module> moduleList = new ArrayList<>();

    @Override
    public void init()
    {

        Logger.info("Loaded %s modules", moduleList.size());
    }

    @Override
    public List<Module> getAll()
    {
        return moduleList;
    }
}
