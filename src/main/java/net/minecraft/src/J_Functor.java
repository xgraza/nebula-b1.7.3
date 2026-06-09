package net.minecraft.src;

interface J_Functor<K, V> {
	boolean matchesNode(V var1);

	K applyTo(V var1);

	String shortForm();
}
