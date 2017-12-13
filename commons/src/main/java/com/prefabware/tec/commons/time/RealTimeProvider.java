package com.prefabware.tec.commons.time;

import java.time.Instant;

/**
 * to provide the real time
 * Created by stefan on 25.07.16.
 */
public class RealTimeProvider implements TimeProvider{

	@Override
	public Instant now() {
		return Instant.now();
	}
}
