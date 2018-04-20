package com.prefabware.tec.commons.string;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.util.Assert;

/**
 * to use a type for something that is basically a string
 * - makes the code more expressive
 * - clearer than having Strings for everything
 * - many methods of the JDK, Apache commons and Spring allow to use CharSequence instead of String.
 *
 *
 * implements Comparable using the delegate, so Collections can be sorted using
 * .stream().sorted()
 */
public abstract class CharSequenceBase implements CharSequence,Comparable<CharSequenceBase>,Serializable{
	private static final long serialVersionUID = 1L;

	protected final String value;

	public static boolean isNullOrEmpty(CharSequence sequence){
		return sequence==null||sequence.length()==0||sequence.toString().isEmpty();
	}

	/**
	 * in oposite to {@link Objects#toString(Object)}, which returns a String="null" for null,
	 * we need null for null
	 *
	 * as at least IntelliJ gets confused with 2 methods toString,even with different signatures,
	 * need a different name here also
	 *
	 * @param value
	 * @return
	 */
	public static String toString2(CharSequence value) {
		if(value==null){return null;}
		return value.toString();
	}

	//@JsonCreator
	public CharSequenceBase(CharSequence value) {
		super();
		Assert.notNull(value,"the value must not be null");
		this.value = value.toString();
	}

	@Override
	public int length() {
		return value.length();
	}

	@Override
	public char charAt(int index) {
		return value.charAt(index);
	}


	@Override
	public CharSequence subSequence(int start, int end) {
		return value.subSequence(start, end);
	}

	//@JsonValue
	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		//thats important ! we never want 2 different subclasses of this
		//to be equal
		if (o == null || getClass() != o.getClass()) return false;

		CharSequenceBase that = (CharSequenceBase) o;

		return value.equals(that.value);

	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public int compareTo(CharSequenceBase o) {
		if(o==null){
			return value.compareTo(null);
		}
		return value.compareTo(o.toString());
	}
}