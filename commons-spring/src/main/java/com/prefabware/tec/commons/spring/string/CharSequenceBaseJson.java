package com.prefabware.tec.commons.spring.string;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.prefabware.tec.commons.string.CharSequenceBase;

/**
 * Created by stefan on 02.01.18.
 */
public class CharSequenceBaseJson extends CharSequenceBase {

	@JsonCreator
	public CharSequenceBaseJson(CharSequence value) {
		super(value);
	}

	@JsonValue
	@Override
	public String toString() {
		return super.toString();
	}
}
