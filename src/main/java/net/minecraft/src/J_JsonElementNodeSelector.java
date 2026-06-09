package net.minecraft.src;

import java.util.List;

final class J_JsonElementNodeSelector extends J_LeafFunctor<J_JsonNode, List> {
	final int field_27069_a;

	J_JsonElementNodeSelector(int var1) {
		this.field_27069_a = var1;
	}

    @Override
	public boolean matchesNode(List var1) {
		return var1.size() > this.field_27069_a;
	}

	public String shortForm() {
		return Integer.toString(this.field_27069_a);
	}

	public J_JsonNode func_27063_c(List var1) {
		return (J_JsonNode)var1.get(this.field_27069_a);
	}

	public String toString() {
		return "an element at index [" + this.field_27069_a + "]";
	}
}
