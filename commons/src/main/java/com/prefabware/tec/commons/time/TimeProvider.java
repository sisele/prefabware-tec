package com.prefabware.tec.commons.time;

import java.time.Instant;

/**
 * to allow using a fake date for tests, data corrections, simulations
 * using current time in implementations makes the code very difficult to test
 * so code should never directly depend on current time or data
 * - instead implementations always expect date/time as a parameter
 * - in only a few places we acctually have to provide the date/time
 *   use this interface if you need to access the current date/time, so we can easily
 *   stub it when needed, e.g. in tests
 *
 * Created by stefan on 25.07.16.
 */
public interface TimeProvider {
	Instant now();
}
