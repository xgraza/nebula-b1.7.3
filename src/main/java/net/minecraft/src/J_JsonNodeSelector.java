package net.minecraft.src;

public final class J_JsonNodeSelector {
	final J_Functor field_27359_a;

	J_JsonNodeSelector(J_Functor var1) {
		this.field_27359_a = var1;
	}

	public boolean func_27356_a(Object var1) {
		return this.field_27359_a.matchesNode(var1);
	}

	public Object func_27357_b(Object var1) {
		return this.field_27359_a.applyTo(var1);
	}

	public J_JsonNodeSelector func_27355_a(J_JsonNodeSelector var1) {
		return new J_JsonNodeSelector(new J_ChainedFunctor(this, var1));
	}

	String func_27358_a() {
		return this.field_27359_a.shortForm();
	}

	public String toString() {
		return this.field_27359_a.toString();
	}
}
