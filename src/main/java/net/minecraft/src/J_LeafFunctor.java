package net.minecraft.src;

abstract class J_LeafFunctor<T, U> implements J_Functor<T, U> {
	public final T applyTo(U var1) {
		if(!this.matchesNode(var1)) {
			throw J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException.func_27322_a(this);
		} else {
			return this.func_27063_c((U) var1);
		}
	}

	protected abstract T func_27063_c(U var1);
}
