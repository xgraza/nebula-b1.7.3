package net.minecraft.src;

import java.util.List;

final class J_JsonArrayNodeSelector extends J_LeafFunctor<List, J_JsonNode> {
	public boolean matchesNode(J_JsonNode var1) {
		return EnumJsonNodeType.ARRAY == var1.func_27218_a();
	}

    public String shortForm() {
		return "A short form array";
	}

	public List func_27063_c(J_JsonNode var1) {
		return var1.func_27215_d();
	}

	public String toString() {
		return "an array";
	}
}
