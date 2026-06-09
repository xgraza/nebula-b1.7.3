package ez.nebula.client.impl.gui.account;

import ez.nebula.client.api.manager.account.Account;
import ez.nebula.client.core.Nebula;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import org.lwjgl.input.Keyboard;

public final class AccountAddScreen extends GuiScreen
{
    private final AccountSelectorScreen parent;
    private GuiTextField usernameField, passwordField;
    private GuiButton addAccountButton;

    public AccountAddScreen(final AccountSelectorScreen parent)
    {
        this.parent = parent;
    }

    @Override
    public void initGui()
    {
        controlList.clear();

        usernameField = new GuiTextField(this, fontRenderer, width / 2 - 100, height / 2 - 150, 200, 20, "");
        passwordField = new GuiTextField(this, fontRenderer, width / 2 - 100, height / 2 - 110, 200, 20, "");

        addAccountButton = new GuiButton(0, width / 2 - 100, height / 2 - 50, "Add account");
        addAccountButton.enabled = false;
        controlList.add(addAccountButton);
        controlList.add(new GuiButton(1, width / 2 - 100, height / 2 - 26, "Back"));
    }

    @Override
    public void updateScreen()
    {
        usernameField.updateCursorCounter();
        passwordField.updateCursorCounter();

        addAccountButton.enabled = !usernameField.getText().isEmpty();
    }

    @Override
    public void drawScreen(int var1, int var2, float var3)
    {
        drawDefaultBackground();
        super.drawScreen(var1, var2, var3);
        usernameField.drawTextBox();
        passwordField.drawTextBox();

        fontRenderer.drawStringWithShadow("Username", usernameField.xPos + 2, usernameField.yPos - 10, -1);
        fontRenderer.drawStringWithShadow("Password", passwordField.xPos + 2, passwordField.yPos - 10, 0xAAAAAA);
    }

    @Override
    protected void mouseClicked(int var1, int var2, int var3)
    {
        super.mouseClicked(var1, var2, var3);
        usernameField.mouseClicked(var1, var2, var3);
        passwordField.mouseClicked(var1, var2, var3);
    }

    @Override
    protected void keyTyped(char var1, int var2)
    {
        if (var2 == Keyboard.KEY_ESCAPE)
        {
            mc.displayGuiScreen(parent);
            return;
        }

        usernameField.textboxKeyTyped(var1, var2);
        passwordField.textboxKeyTyped(var1, var2);
    }

    @Override
    protected void actionPerformed(GuiButton var1)
    {
        if (var1 == null)
        {
            return;
        }
        if (var1.id == 0)
        {
            if (!var1.enabled)
            {
                return;
            }
            Nebula.INSTANCE.getAccountManager().registerAccount(new Account(usernameField.getText(), passwordField.getText()));
        }

        mc.displayGuiScreen(parent);
    }
}
