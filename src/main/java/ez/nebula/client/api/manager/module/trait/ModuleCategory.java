package ez.nebula.client.api.manager.module.trait;

public enum ModuleCategory
{
    COMBAT("Combat"),
    EXPLOIT("Exploit"),
    MOVEMENT("Movement"),
    PLAYER("Player"),
    RENDER("Render"),
    WORLD("World");

    private final String friendlyName;

    ModuleCategory(String friendlyName)
    {
        this.friendlyName = friendlyName;
    }

    @Override
    public String toString()
    {
        return friendlyName;
    }
}
