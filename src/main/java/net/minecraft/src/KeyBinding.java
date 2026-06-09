package net.minecraft.src;

import org.lwjgl.input.Keyboard;

public class KeyBinding {
	public String keyDescription;
	public int keyCode;

	public KeyBinding(String var1, int var2) {
		this.keyDescription = var1;
		this.keyCode = var2;
	}

    public boolean isKeyDown()
    {
        return Keyboard.isKeyDown(keyCode);
    }
}
