package ez.nebula.client.impl.gui.command;

import ez.nebula.client.api.manager.command.CommandManager;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public final class ConsoleGUI extends GuiScreen
{
    private final CommandManager manager;

    private final List<String> displayTextList = new ArrayList<>();
    private GuiTextField commandField;

    public ConsoleGUI(final CommandManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void initGui()
    {
        commandField = new GuiTextField(this, fontRenderer, width / 2 - 150, 20, 300, 20, "");
        commandField.setFocused(true);
        Keyboard.enableRepeatEvents(true);

        manager.parseCommand("", displayTextList);
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        commandField.updateCursorCounter();
    }

    @Override
    public void drawScreen(int var1, int var2, float var3)
    {
        commandField.drawTextBox();
        if (displayTextList.isEmpty())
        {
            return;
        }

        int posX = commandField.xPos + 2;
        int posY = commandField.yPos + 22;

        for (final String line : displayTextList)
        {
            fontRenderer.drawStringWithShadow(line, posX, posY, -1);
            posY += 11;
        }
    }

    @Override
    protected void mouseClicked(int var1, int var2, int var3)
    {
        commandField.mouseClicked(var1, var2, var3);
    }

    @Override
    protected void keyTyped(char var1, int var2)
    {
        if (var2 == Keyboard.KEY_RETURN)
        {
            displayTextList.clear();
            manager.parseCommand(commandField.getText(), displayTextList);
            return;
        }
        super.keyTyped(var1, var2);
        commandField.textboxKeyTyped(var1, var2);

        if (commandField.getText().isEmpty())
        {
            displayTextList.clear();
            manager.parseCommand("", displayTextList);
        }
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
