package net.minecraft.src;

import java.util.Map;

final class J_JsonFieldNodeSelector extends J_LeafFunctor<J_JsonNode, Map> {
	final J_JsonStringNode field_27066_a;

	J_JsonFieldNodeSelector(J_JsonStringNode var1) {
		this.field_27066_a = var1;
	}

	public boolean matchesNode(Map var1) {
		return var1.containsKey(this.field_27066_a);
	}

    public String shortForm() {
		return "\"" + this.field_27066_a.func_27216_b() + "\"";
	}

	public J_JsonNode func_27063_c(Map var1) {
		return (J_JsonNode)var1.get(this.field_27066_a);
	}

	public String toString() {
		return "a field called [\"" + this.field_27066_a.func_27216_b() + "\"]";
	}
}
