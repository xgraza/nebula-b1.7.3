package ez.nebula.client.impl.gui.account;

import ez.nebula.client.api.manager.account.Account;
import ez.nebula.client.core.Nebula;
import net.minecraft.src.*;

import java.util.List;

public final class AccountSelectorScreen extends GuiScreen
{
    private AccountSlot accountSlot;
    private GuiButton removeAccountButton, loginButton;

    public int selectedAccount = -1;

    @Override
    public void initGui()
    {
        controlList.clear();
        selectedAccount = -1;

        controlList.add(new GuiButton(0, width / 2 - 100, height - 50, 98, 20, "Add"));
        removeAccountButton = new GuiButton(1, width / 2 + 2, height - 50, 98, 20, "Remove");
        removeAccountButton.enabled = false;
        controlList.add(removeAccountButton);
        loginButton = new GuiButton(2, width / 2 - 100, height - 50 + 24, "Login");
        loginButton.enabled = false;
        controlList.add(loginButton);

        accountSlot = new AccountSlot(this);
        accountSlot.registerScrollButtons(this.controlList, 4, 5);
    }

    @Override
    public void updateScreen()
    {
        removeAccountButton.enabled = selectedAccount != -1;
        loginButton.enabled = selectedAccount != -1;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float tickDelta)
    {
        accountSlot.drawScreen(mouseX, mouseY, tickDelta);
        super.drawScreen(mouseX, mouseY, tickDelta);

        drawCenteredString(fontRenderer, "Account Manager [" + Nebula.INSTANCE.getAccountManager().getAll().size() + "]", width / 2, 6, 0xAAAAAA);

        if (mc.session != null)
        {
            int width = fontRenderer.getStringWidth("Logged into: ");
            fontRenderer.drawStringWithShadow("Logged into: ", 2, 2, 0xAAAAAA);
            fontRenderer.drawStringWithShadow(mc.session.username, 2 + width, 2,
                   (mc.session.sessionId == null || mc.session.sessionId.isEmpty()) ? 0xFF2020 : 0x20FF20);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button == null)
        {
            return;
        }
        switch (button.id)
        {
            case 0:
            {
                mc.displayGuiScreen(new AccountAddScreen(this));
                break;
            }
            case 1:
            {
                if (removeAccountButton.enabled && selectedAccount != -1)
                {
                    mc.displayGuiScreen(new GuiYesNo(this, "", "Are you sure you want to delete this account?", "Delete it", "Go back", -1)
                    {
                        @Override
                        protected void actionPerformed(GuiButton var1)
                        {
                            if (var1.id == 0)
                            {
                                Nebula.INSTANCE.getAccountManager().removeAccount(
                                        ((List<Account>) Nebula.INSTANCE.getAccountManager().getAll()).get(selectedAccount));
                                selectedAccount = -1;
                            }
                            mc.displayGuiScreen(parentScreen);
                        }
                    });
                }
                break;
            }
            case 2:
            {
                loginToSelected();
                break;
            }
            default:
            {
                accountSlot.actionPerformed(button);
                break;
            }
        }
    }

    public void loginToSelected()
    {
        if (selectedAccount == -1)
        {
            return;
        }
        final List<Account> accountList = (List<Account>) Nebula.INSTANCE.getAccountManager().getAll();
        if (accountList.isEmpty() || accountList.size() - 1 < selectedAccount)
        {
            return;
        }
        final Account account = accountList.get(selectedAccount);
        Nebula.INSTANCE.getAccountManager().login(account);
        selectedAccount = -1;
    }

    static final class AccountSlot extends GuiSlot
    {
        private final AccountSelectorScreen parent;

        public AccountSlot(final AccountSelectorScreen parent)
        {
            super(parent.mc, parent.width, parent.height, 32, parent.height - 64, 36);
            this.parent = parent;
        }

        @Override
        protected int getSize()
        {
            return Nebula.INSTANCE.getAccountManager().getAll().size();
        }

        @Override
        protected int getContentHeight()
        {
            return getSize() * 36;
        }

        @Override
        protected void elementClicked(int var1, boolean var2)
        {
            parent.selectedAccount = var1;
            if (var2)
            {
                parent.loginToSelected();
            }
        }

        @Override
        protected boolean isSelected(int var1)
        {
            if (parent.selectedAccount == -1)
            {
                return false;
            }
            return parent.selectedAccount == var1;
        }

        @Override
        protected void drawBackground()
        {
            parent.drawDefaultBackground();
        }

        @Override
        protected void drawSlot(int index, int x, int y, int var4, Tessellator tessellator)
        {
            final List<Account> accountList = (List<Account>) Nebula.INSTANCE.getAccountManager().getAll();
            if (accountList.isEmpty() || accountList.size() - 1 < index)
            {
                return;
            }
            final Account account = accountList.get(index);
            if (account == null)
            {
                return;
            }

            final int elementWidth = (this.width / 2 + 110) - x;

            int textWidth = mc.fontRenderer.getStringWidth(account.getDisplayName());
            mc.fontRenderer.drawStringWithShadow(account.getDisplayName(),
                    (int) (x + (elementWidth / 2.0) - (textWidth / 2.0)),
                    (int) (y + (32 / 2.0) - (9 / 2.0) - (!account.isCracked() ? 9 : 0)) + 1,
                    -1);
            if (!account.isCracked())
            {
                textWidth = mc.fontRenderer.getStringWidth(account.getUsername());
                mc.fontRenderer.drawStringWithShadow(account.getUsername(),
                        (int) (x + (elementWidth / 2.0) - (textWidth / 2.0)),
                        (int) (y + (32 / 2.0) - (9 / 2.0) + 9) + 1,
                        0xAAAAAA);
            }
        }
    }
}
