package net.minecraft.src;

final class J_JsonStringNodeSelector extends J_LeafFunctor<String, J_JsonNode> {
	public boolean matchesNode(J_JsonNode var1) {
		return EnumJsonNodeType.STRING == var1.func_27218_a();
	}

	public String shortForm() {
		return "A short form string";
	}

	public String func_27063_c(J_JsonNode var1) {
		return var1.func_27216_b();
	}

	public String toString() {
		return "a value that is a string";
	}
}
