package ez.nebula.client.api.manager;

import java.util.List;

public interface ITypedManager<T> extends IManager
{
    List<T> getAll();
}
