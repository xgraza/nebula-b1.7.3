package ez.nebula.client.api;

public interface Toggleable
{
    default void onEnable()
    {

    }

    default void onDisable()
    {

    }

    boolean isToggled();

    void toggle();

    void setToggled(boolean state);
}
