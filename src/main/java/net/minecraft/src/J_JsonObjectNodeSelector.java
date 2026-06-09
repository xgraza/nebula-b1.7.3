package net.minecraft.src;

import java.util.Map;

final class J_JsonObjectNodeSelector extends J_LeafFunctor<Map, J_JsonNode> {
	public boolean matchesNode(J_JsonNode var1) {
		return EnumJsonNodeType.OBJECT == var1.func_27218_a();
	}

	public String shortForm() {
		return "A short form object";
	}

	public Map func_27063_c(J_JsonNode var1) {
		return var1.func_27214_c();
	}

	public String toString() {
		return "an object";
	}
}
