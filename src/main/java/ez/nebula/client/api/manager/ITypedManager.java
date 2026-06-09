package ez.nebula.client.api.manager;

import ez.nebula.client.api.Initiable;

import java.util.Collection;

public interface ITypedManager<T> extends Initiable
{
    Collection<T> getAll();
}
